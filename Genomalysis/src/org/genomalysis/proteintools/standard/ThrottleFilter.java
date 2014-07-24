/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.genomalysis.proteintools.standard;

import org.genomalysis.plugin.configuration.annotations.Author;
import org.genomalysis.plugin.configuration.annotations.Configurator;
import org.genomalysis.plugin.configuration.annotations.Documentation;
import org.genomalysis.plugin.configuration.annotations.PropertyGetter;
import org.genomalysis.plugin.configuration.annotations.PropertySetter;
import org.genomalysis.plugin.configuration.dialogs.GenericConfigurator;
import org.genomalysis.proteintools.IProteinSequenceFilter;
import org.genomalysis.proteintools.InitializationException;
import org.genomalysis.proteintools.ProteinSequence;

/**
 *
 * @author ameyers
 */
@Documentation("This filter was meant to be a debugging tool. It will pause"
 + "for the specified number of milliseconds, once configured. The default"
 + "behavior does not cause the filter to pause, and will have no effect"
 + "on normal execution. Maximum pause interval is one minute (60,000 milliseconds).")
@Author(Name="Wolfgang Meyers", EmailAddress="wolfgangmeyers@gmail.com")
@Configurator(GenericConfigurator.class)
public class ThrottleFilter implements IProteinSequenceFilter{

    private int pauseInterval = 0;
    private static int maximumPauseInterval = 60000;
    
    public void initialize() throws InitializationException {
        //do nothing
    }

    public boolean filterProteinSequence(ProteinSequence sequence) {
        if(pauseInterval > 0){
            try {
                Thread.sleep(pauseInterval);
            } catch (InterruptedException ex) { }
        }
        return true;
    }

    @PropertyGetter(PropertyName="Pause Interval")
    public int getPauseInterval() {
        return pauseInterval;
    }

    @PropertySetter(PropertyName="Pause Interval")
    public void setPauseInterval(int pauseInterval) {
        if(pauseInterval >= 0 && pauseInterval <= maximumPauseInterval)
            this.pauseInterval = pauseInterval;
    }

}
