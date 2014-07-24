package org.genomalysis.plugin;

import java.io.File;
import java.io.FileFilter;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class FilePluginManager extends AbstractPluginManager implements
		Runnable {

	private boolean running;
	private boolean daemon;
	private FileFilter jarFilter;

	public FilePluginManager() {
		this.running = false;
		this.daemon = true;
		this.jarFilter = new JarFilter();
	}

	public void stop() {
		this.running = false;
	}

	/**
	 * Searches the plugins directory for jar files and auto-discovers
	 * implementations of Genomalysis plugins.
	 */
	public void findPlugins() {
		File pluginDir;
		System.out.println("FilePluginManager::findPlugins");
		// if the plugin manager is in daemon mode, it will listen
		// for jar files to be added in the background. This might be overkill...
		if (this.daemon) {
			if (!(this.running)) {
				this.running = true;
				pluginDir = new File("plugins");
				if (!(pluginDir.exists())) {
					pluginDir.mkdir();
				}

				Thread daemon = new Thread(this);
				daemon.setDaemon(true);
				daemon.start();
			}
		} else {
			pluginDir = new File("plugins");
			List<File> plugins = new ArrayList<>();
			Class<?>[] pluginInterfaceArray = new Class<?>[this.pluginInterfaces
					.size()];
			pluginInterfaceArray = (Class[]) this.pluginInterfaces
					.toArray(pluginInterfaceArray);
			System.out.println("FilePluginManager(findPlugins): I have "
					+ pluginInterfaceArray.length + " plugins to search for.");
			scanPlugins(plugins, pluginDir, pluginInterfaceArray);
		}
	}

	public void run() {
		System.out.println("FilePluginManager::run");

		File pluginDir = new File("plugins");
		List<File> plugins = new ArrayList<>();
		Class<?>[] pluginInterfaceArray = new Class<?>[this.pluginInterfaces
				.size()];
		pluginInterfaceArray = (Class[]) this.pluginInterfaces
				.toArray(pluginInterfaceArray);
		if (this.running) {
			try {
				scanPlugins(plugins, pluginDir, pluginInterfaceArray);
				Thread.sleep(5000L);
			} catch (InterruptedException ex) {
				this.running = false;
			}
		}
	}

	/**
	 * Adds all jar files that are in pluginDir but that have not yet been
	 * scanned to the classpath. Proceeds to use PluginLoader to go through all
	 * of the classes in the jar file, using a callback (this plugin manager) to
	 * register newly found plugin implementations.
	 * 
	 * If any new jar files are loaded, observers of this plugin manager are
	 * notified that its state has changed.
	 * 
	 * @param plugins
	 * @param pluginDir
	 * @param pluginInterfaceArray
	 */
	private void scanPlugins(List<File> plugins, File pluginDir,
			Class<?>[] pluginInterfaceArray) {
		File scanned;
		File[] pluginScan = pluginDir.listFiles(this.jarFilter);
		boolean updateRequired = false;

		// this is the part where we add files to the classpath
		for (int i = 0; i < pluginScan.length; i++) {
			scanned = pluginScan[i];
			if (!(plugins.contains(scanned))) {
				PluginClassLoader.getInstance().addPluginFile(scanned);
			}

		}

		// this is the part where we scan each jar file and load plugin
		// implementation classes from it.
		for (int i = 0; i < pluginScan.length; i++) {
			scanned = pluginScan[i];

			if (!(plugins.contains(scanned))) {
				try {
					PluginLoader.getInstance().loadPlugins(scanned,
							pluginInterfaceArray, this);
					updateRequired = true;
				} catch (Exception ex) {
					ex.printStackTrace();
					notifyObserversOfError("Problem loading plugin file: "
							+ ex.getMessage());
				}
				plugins.add(scanned);
			}
		}

		if (updateRequired) {
			notifyObservers();
		}
	}

	public boolean isDaemon() {
		return this.daemon;
	}

	public void setDaemon(boolean daemon) {
		this.daemon = daemon;
	}

	class JarFilter implements FileFilter {
		public boolean accept(File file) {
			return file.getName().endsWith(".jar");
		}
	}
}