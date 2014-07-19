/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.genomalysis.control.filters;

import org.genomalysis.proteintools.IProteinSequenceFilter;
import org.genomalysis.proteintools.InitializationException;
import org.genomalysis.proteintools.ProteinSequence;

/**
 *
 * @author ameyers
 */
public class PauseFilter implements IProteinSequenceFilter{

    private boolean pause = false;
    private boolean active = false;
    
    public void Pause(){
        pause = true;
    }
    
    public void UnPause(){
        pause = false;
    }
    
    public void initialize() throws InitializationException {
        //do nothing
    }

    public boolean filterProteinSequence(ProteinSequence sequence) {
        active = true;
        if(isPaused())
            System.out.println("PauseFilter: pausing");
        while(isPaused()){
            Thread.yield();
            if(!isPaused())
                System.out.println("PauseFilter: unpausing");
        }        
        active = false;
        return true;
    }

    public boolean isActive() {
        return active;
    }

    public boolean isPaused() {
        return pause;
    }

}
