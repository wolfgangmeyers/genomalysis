package org.genomalysis.plugin;

/**
 *
 * @author ameyers
 */
public interface IPluginFoundCallback extends IErrorListener {
    void pluginFound(Class<?> pluginInterface, PluginInstanceFactory<?> factory);
}
