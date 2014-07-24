/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.genomalysis.control.filters;

import java.util.ArrayList;
import java.util.List;

import org.genomalysis.proteintools.IProteinSequenceFilter;
import org.genomalysis.proteintools.InitializationException;
import org.genomalysis.proteintools.ProteinSequence;

/**
 *
 * @author ameyers
 */
public class CountingFilter implements IProteinSequenceFilter{

    private int count = 0;
    private int countingInterval = 1;
    private List<ICountingFilterCallback> callbacks = new ArrayList<ICountingFilterCallback>();
    
    public void addCallback(ICountingFilterCallback callback){
        synchronized(callbacks){
            if(!this.callbacks.contains(callback)){
                callbacks.add(callback);
            }
        }
    }
    
    public void removeCallback(ICountingFilterCallback callback){
        synchronized(callbacks){
            callbacks.remove(callback);
        }
    }
    
    public void initialize() throws InitializationException {
        count = 0;
    }

    public boolean filterProteinSequence(ProteinSequence sequence) {
        count++;
        if(count % countingInterval == 0)
            notifyCallbacks(count);
        return true;
    }

    public int getCount() {
        return count;
    }

    public int getCountingInterval() {
        return countingInterval;
    }

    public void setCountingInterval(int countingInterval) {
        this.countingInterval = countingInterval;
    }
    
    private void notifyCallbacks(int newCount){
        synchronized(callbacks){
            for(ICountingFilterCallback callback : callbacks){
                callback.countReached(newCount);
            }
        }
    }

}
