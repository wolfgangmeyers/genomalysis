/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.genomalysis.plugin.configuration;

/**
 *
 * @author ameyers
 */
public class ConfigurationException extends Exception{
    public ConfigurationException(){
        
    }
    
    public ConfigurationException(String reason){
        super(reason);
    }
    
    public ConfigurationException(String reason, Exception inner){
        super(reason, inner);
    }
}
