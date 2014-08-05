package org.genomalysis.plugin;

import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.genomalysis.control.EventSupport;
import org.genomalysis.control.IObserver;
import org.genomalysis.plugin.configuration.ConfigurationTables;
import org.genomalysis.plugin.configuration.IPropertyConfigurator;
import org.genomalysis.plugin.configuration.PropertyConfiguratorTable;
import org.genomalysis.plugin.configuration.annotations.Configurator;
import org.genomalysis.plugin.configuration.dialogs.GenericConfigurator;

public class PluginManager implements IPluginFoundCallback, Runnable {

	/**
	 * Map from interface name to list of implementing classes
	 */
	private Map<String, List<PluginInstanceFactory<?>>> pluginTypes;
	protected List<Class<?>> pluginInterfaces;
	private EventSupport eventSupport = new EventSupport();
	private boolean running;
    private boolean daemon;
    private FileFilter jarFilter;

	public PluginManager() {
		this.pluginTypes = new HashMap<>();
		this.pluginInterfaces = new ArrayList<>();
		this.running = false;
        this.daemon = true;
        this.jarFilter = new JarFilter();
	}

	/**
	 * Gets all known implementing classes of the given interface
	 * 
	 * @param pluginInterface
	 * @return
	 */
	public List<PluginInstanceFactory<?>> getPluginTypes(Class<?> pluginInterface) {
		String classname = pluginInterface.getName();
		List<PluginInstanceFactory<?>> result = null;
		result = this.pluginTypes.get(classname);
		if (result == null) {
			result = new ArrayList<>();
			this.pluginTypes.put(classname, result);
		}
		return result;
	}

	/**
	 * Clear everything
	 */
	public void clearAllPlugins() {
		this.pluginInterfaces.clear();
		this.pluginTypes.clear();
		notifyObservers();
	}

	/**
	 * Registers the plugin interface with this plugin manager. I have yet to
	 * figure out why we would want to do this...
	 * 
	 * @param iface
	 */
	public void addPluginInterface(Class<?> iface) {
		if (!(this.pluginInterfaces.contains(iface))) {
			this.pluginInterfaces.add(iface);
		}
	}

	/**
	 * This method appears to relay a message to any observers of this object.
	 */
	public void showError(String message) {
		notifyObserversOfError(message);
	}

	/**
	 * Gets the appropriate property configurator for the given object.
	 * Configurators spawn user interface dialogs that are used to set
	 * properties on the object. Once instantiated, property configurators are
	 * cached in a PropertyConfigurationTable, and reused on subsequent calls
	 * for objects of the same class.
	 * 
	 * Objects that don't specify a configurator, or whose configurator throws
	 * an exception during instantiation, are assigned a generic configurator
	 * that displays editable properties found by type introspection.
	 * 
	 * @param toBeConfigured
	 * @return
	 */
	public IPropertyConfigurator getConfigurator(Object toBeConfigured) {
		IPropertyConfigurator configurator = null;
		PropertyConfiguratorTable propertyConfigurationTable = ConfigurationTables
				.getPropertyConfiguratorTable();

		// first, see if there is a configurator cached
		if (propertyConfigurationTable
				.IsClassRegisteredForConfiguration(toBeConfigured.getClass())) {
			// if so, use it.
			configurator = propertyConfigurationTable
					.getConfigurationDialog(toBeConfigured.getClass());

		} else if (toBeConfigured.getClass().getAnnotation(Configurator.class) != null) {
			// if not, create it and cache it.
			Configurator ann = (Configurator) toBeConfigured.getClass()
					.getAnnotation(Configurator.class);
			Class<?> cfgClass = ann.value();
			try {
				Constructor<?> ctor = cfgClass.getConstructor(new Class[0]);

				configurator = (IPropertyConfigurator) ctor
						.newInstance(new Object[0]);
			} catch (Exception ex) {
				// TODO: log an error here?
				configurator = new GenericConfigurator();
			}
			// cache it
			propertyConfigurationTable.RegisterPropertyConfigurationDialog(
					toBeConfigured.getClass(), configurator);
		} else {
			// in the case that a configurator hasn't been specified, this type
			// of object gets a generic configurator.
			configurator = new GenericConfigurator();
			propertyConfigurationTable.RegisterPropertyConfigurationDialog(
					toBeConfigured.getClass(), configurator);
		}
		return configurator;
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
	/**
	 * Registers an observer that can listen for state changes in the plugin
	 * manager.
	 * 
	 * @param observer
	 */
	public void addObserver(IObserver observer) {
		eventSupport.addObserver(observer);
	}

	/**
	 * Unregisters an observer
	 * 
	 * @param observer
	 */
	public void removeObserver(IObserver observer) {
		eventSupport.removeObserver(observer);
	}

	/**
	 * Retrieves documentation for this type, specified in the @Documentation
	 * annotation.
	 * 
	 * @param clazz
	 * @return
	 */
	public String getDocumentation(Class<?> clazz) {
		return ConfigurationTables.getDocumentationTable().getDocumentation(
				clazz);
	}

	/**
	 * Implementation of IPluginFoundCallback - called by PluginManager
	 * subclasses to register implementations of the plugin interface.
	 */
	@SuppressWarnings("unchecked")
    public void pluginFound(Class<?> pluginInterface, Class<?> pluginClass) {
		System.out.println("AbstractPluginManager::pluginFound");

		String classname = pluginInterface.getName();

		try {
			ConfigurationTables.getReplacementTable().registerReplacement(
					pluginInterface, pluginClass);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		List<PluginInstanceFactory<?>> plugins = this.pluginTypes.get(classname);
		if (plugins == null) {
			plugins = new ArrayList<>();
			this.pluginTypes.put(classname, plugins);
		}
		if (!(plugins.contains(pluginClass))) {
			plugins.add(new ClassPluginInstanceFactory<Object>((Class<Object>)pluginClass));
		}
	}

	/**
	 * Notifies observers that the state of this plugin manager has changed.
	 */
	protected void notifyObservers() {
		eventSupport.notifyObservers();
	}

	/**
	 * Notifies observers that an error has occurred, and that the end user may
	 * need to be notified that something has broken.
	 * 
	 * @param errorMsg
	 */
	protected void notifyObserversOfError(String errorMsg) {
		eventSupport.notifyObserversOfError(errorMsg);
	}
}