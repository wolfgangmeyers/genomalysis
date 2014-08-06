package org.genomalysis.plugin.script;

import java.util.List;

import org.genomalysis.plugin.PluginInstanceFactory;

/**
 * Provides an integration point for scripting engines to be added to
 * Genomalysis.
 * 
 * @author wolfgang
 *
 */
public interface ScriptPlugin {

    /**
     * Specifies what kind of extension a ScriptFactory supports. For example, a
     * ScriptFactory that integrates a python scripting engine would likely
     * support "py" extensions, where a ruby implementation would support "rb"
     * extensions.
     * 
     * @return Extension of script files supported by this ScriptFactory
     */
    String extension();

    /**
     * Inspects the script for any implemented plugin type, and returns it.
     * 
     * @param script
     *            Contents of the script file to scan
     * @param pluginTypes
     *            Types of plugin that are supported by the system.
     * @return If found, the type of plugin interface that the script implements
     * @throws ScriptException
     *             If any errors occur when executing the script.
     */
    Class<?> discoverPluginType(String script, List<Class<?>> pluginTypes)
            throws ScriptException;

    /**
     * Returns an implementation of PluginInstanceFactory that can create plugin
     * instances by using the contents of a script. Instances that are created
     * by this factory should be of the same type as returned by
     * discoverPluginType.
     * 
     * @param script
     *            Contents of the script file to use
     * @return PluginInstanceFactory instance
     * @throws ScriptException
     *             If any errors occur when executing the script.
     */
    PluginInstanceFactory<?> createInstanceFactory(String script, Class<?> pluginType)
            throws ScriptException;

}
