package org.genomalysis.plugin.script;

/**
 * Provides an integration point for scripting engines to be added to
 * Genomalysis.
 * 
 * @author wolfgang
 *
 */
public interface ScriptFactory {

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
     * This method should syntax-check the script.
     * 
     * @param script
     *            Contents of the script file to check
     * @throws ScriptException
     *             If the syntax of the script file is malformed.
     */
    void verify(String script) throws ScriptException;

    /**
     * Creates an instance of the object in the script.
     * 
     * @param script
     * @return
     * @throws ScriptException
     */
    Object createInstance(String script) throws ScriptException;

}
