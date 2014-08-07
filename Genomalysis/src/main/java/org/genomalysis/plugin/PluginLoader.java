package org.genomalysis.plugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.genomalysis.plugin.configuration.ConfigurationTables;

public class PluginLoader {

    private static PluginLoader instance = new PluginLoader();

    public static PluginLoader getInstance() {
        return instance;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void loadPlugins(URL jarfile, Class[] pluginInterfaces,
            IPluginFoundCallback callback) throws IOException {
        System.out.println("PluginLoader::loadPlugins");

        List<Class<?>> allClasses = loadAllClasses(jarfile);
        for (Class<?> c : allClasses) {
            for (int i = 0; i < pluginInterfaces.length; i++) {
                Class pluginInterface = pluginInterfaces[i];
                if (PluginUtil.implementsInterface(c, pluginInterface)
                        && hasDefaultConstructor(c)) {
                    callback.pluginFound(pluginInterface,
                            new ClassPluginInstanceFactory(c));
                }
            }
        }
    }

    public void loadPlugins(File jarfile, Class<?>[] pluginInterfaces,
            IPluginFoundCallback callback) throws IOException {
        System.out.print("PluginLoader::loadPlugins");
        URL url = jarfile.toURI().toURL();
        loadPlugins(url, pluginInterfaces, callback);
    }

    public List<Class<?>> loadAllInterfaces(URL jarfile) throws IOException {
        List<Class<?>> interfaces = new ArrayList<>();
        List<Class<?>> allTypes = loadAllTypes(jarfile);
        for (Class<?> clazz : allTypes) {
            if (clazz.isInterface()) {
                interfaces.add(clazz);
            }
        }
        return interfaces;
    }

    public List<Class<?>> loadAllClasses(URL jarfile) throws IOException {
        List<Class<?>> classes = new ArrayList<>();
        List<Class<?>> allTypes = loadAllTypes(jarfile);
        for (Class<?> clazz : allTypes) {
            if (!(clazz.isInterface())) {
                classes.add(clazz);
            }
        }
        return classes;
    }

    public List<Class<?>> loadAllTypes(URL jarfile) throws IOException {
        List<Class<?>> types = new ArrayList<>();

        URLConnection cnn = jarfile.openConnection();
        cnn.connect();
        InputStream pluginSource = cnn.getInputStream();
        try {
            ZipInputStream zin = new ZipInputStream(pluginSource);
            ZipEntry entry = null;
            List<String> classNames = new ArrayList<>();//
            do {
                entry = zin.getNextEntry();
                if (entry != null && !entry.isDirectory()
                        && entry.getName().endsWith(".class")) {
                    String className = entry.getName().substring(0,
                            entry.getName().indexOf(".class"));
                    className = className.replace("/", ".");
                    classNames.add(className);
                }
            } while (entry != null);

            pluginSource.close();
            pluginSource = null;

            PluginClassLoader loader = PluginClassLoader.getInstance();

            loader.addPluginFile(jarfile);

            for (String className : classNames) {
                try {
                    Class<?> c = loader.findPlugin(className);
                    if (c != null) {
                        types.add(c);

                        ConfigurationTables.introspectClass(c);
                    }
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }

        } finally {
            if (pluginSource != null) {
                pluginSource.close();
            }
        }

        return types;
    }

    private boolean hasDefaultConstructor(Class<?> clazz) {
        boolean result = false;
        try {
            clazz.getConstructor(new Class[0]);
            result = true;
        } catch (Exception ex) {
        }
        return result;
    }
}