/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.genomalysis.plugin;

/**
 *
 * @author ameyers
 */
public interface IPluginFoundCallback extends IErrorListener{
    void pluginFound(Class<?> pluginInterface, Class<?> pluginClass);
}
