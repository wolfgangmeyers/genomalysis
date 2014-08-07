/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.genomalysis.plugin;

import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ameyers
 */
// TODO: replace singleton with spring context...
public class PluginClassLoader {

    // singleton instance
    private static PluginClassLoader instance;
    static {
        instance = new PluginClassLoader();
    }

    public static PluginClassLoader getInstance() {
        return instance;
    }

    public Class<?> findPlugin(String className) throws ClassNotFoundException {
        Class<?> c = null;
        try {
            c = Class.forName(className);
        } catch (IllegalAccessError ex) {
        } catch (NoClassDefFoundError ex) {
        }
        return c;
    }

    public void addPluginFile(URL pluginFile) {
        try {
            URLClassLoader systemLoader = (URLClassLoader) ClassLoader
                    .getSystemClassLoader();
            Method method = URLClassLoader.class.getDeclaredMethod("addURL",
                    new Class[] { URL.class });
            method.setAccessible(true);
            method.invoke(systemLoader, new Object[] { pluginFile });
        } catch (Exception ex) {
            Logger.getLogger(PluginClassLoader.class.getName()).log(
                    Level.SEVERE, null, ex);
        }
    }

    public void addPluginFile(File pluginFile) {
        try {
            addPluginFile(pluginFile.toURI().toURL());
        } catch (MalformedURLException ex) {
            Logger.getLogger(PluginClassLoader.class.getName()).log(
                    Level.SEVERE, null, ex);
        }
    }
}
