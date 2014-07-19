package org.genomalysis.plugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.genomalysis.plugin.configuration.ConfigurationTables;

public class PluginLoader {

    private static PluginLoader instance = new PluginLoader();

    public static PluginLoader getInstance() {
        return instance;
    }

    public void loadPlugins(URL jarfile, Class[] pluginInterfaces, IPluginFoundCallback callback)
            throws IOException {
        System.out.println("PluginLoader::loadPlugins");

        List<Class<?>> allClasses = loadAllClasses(jarfile);
        for (Class<?> c : allClasses) {
            for(int i = 0; i < pluginInterfaces.length; i++){
                Class pluginInterface = pluginInterfaces[i];
                if(implementsInterface(c, pluginInterface) && hasDefaultConstructor(c)){
                    callback.pluginFound(pluginInterface, c);
                }
            }
        }
    }

    public void loadPlugins(File jarfile, Class[] pluginInterfaces, IPluginFoundCallback callback)
            throws IOException {
        System.out.print("PluginLoader::loadPlugins");
        URL url = jarfile.toURI().toURL();
        loadPlugins(url, pluginInterfaces, callback);
    }

    public List<Class<?>> loadAllInterfaces(URL jarfile) throws IOException {
        List interfaces = new ArrayList();
        List allTypes = loadAllTypes(jarfile);
        for (Iterator i$ = allTypes.iterator(); i$.hasNext();) {
            Class clazz = (Class) i$.next();
            if (clazz.isInterface()) {
                interfaces.add(clazz);
            }
        }
        return interfaces;
    }

    public List<Class<?>> loadAllClasses(URL jarfile) throws IOException {
        List classes = new ArrayList();
        List allTypes = loadAllTypes(jarfile);
        for (Iterator i$ = allTypes.iterator(); i$.hasNext();) {
            Class clazz = (Class) i$.next();
            if (!(clazz.isInterface())) {
                classes.add(clazz);
            }
        }
        return classes;
    }

    public List<Class<?>> loadAllTypes(URL jarfile)
            throws IOException {
        List types = new ArrayList();

        URLConnection cnn = jarfile.openConnection();
        cnn.connect();
        InputStream pluginSource = cnn.getInputStream();
        try {
            ZipInputStream zin = new ZipInputStream(pluginSource);
            ZipEntry entry = null;
            List classNames = new ArrayList();//
            do{
                entry = zin.getNextEntry();
                if(entry != null && !entry.isDirectory() && entry.getName().endsWith(".class")){
                    String className = entry.getName().substring(0, entry.getName().indexOf(".class"));
                    className = className.replace("/", ".");
                    classNames.add(className);
                }
            }while(entry != null);

            pluginSource.close();
            pluginSource = null;

            PluginClassLoader loader = PluginClassLoader.getInstance();

            loader.addPluginFile(jarfile);

            for (Iterator i$ = classNames.iterator(); i$.hasNext();) {
                String str = (String) i$.next();
                try {
                    Class c = loader.findPlugin(str);
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

    private boolean hasDefaultConstructor(Class clazz) {
        boolean result = false;
        try {
            Constructor constructor = clazz.getConstructor(new Class[0]);
            result = true;
        } catch (Exception ex) {
        }
        return result;
    }

    private boolean implementsInterface(Class subject, Class iface) {
        boolean result = false;
        try {
            subject.asSubclass(iface);
            result = true;
        } catch (ClassCastException ex) {
        }
        return result;
    }
}