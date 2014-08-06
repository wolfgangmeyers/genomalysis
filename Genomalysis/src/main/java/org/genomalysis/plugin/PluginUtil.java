package org.genomalysis.plugin;

public class PluginUtil {
    
    public static boolean implementsInterface(Class<?> subject, Class<?> iface) {
        boolean result = false;
        try {
            subject.asSubclass(iface);
            result = true;
        } catch (ClassCastException ex) {
        }
        return result;
    }
    
}
