/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.genomalysis.control;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.genomalysis.control.filters.CountingFilter;
import org.genomalysis.control.filters.ICountingFilterCallback;

/**
 *
 * @author ameyers
 */
public class FilterProgressControl {

    private int totalCount;
    private int numRead;
    private int numWritten;
  private Time startTime;
    private List<IObserver> observers = new ArrayList<IObserver>();

  public FilterProgressControl()
  {
    this.observers = new ArrayList(); }

  public String getProgressReport() {
    StringBuffer buffer = new StringBuffer();
    buffer.append("Total sequences in input file: " + getTotalCount() + "\n");
    buffer.append("Total sequences read from input file: " + getNumRead() + "\n");
    buffer.append("Total sequences written to output file: " + getNumWritten() + "\n");
    buffer.append("Filter percent complete: " + formatPercentage(getPercentageComplete()) + "\n");
    buffer.append("Filter Pass Ratio: " + formatPercentage(getPassRatio()) + "\n");
    buffer.append("Time elapsed: " + getElapsedTime());
    return buffer.toString();
  }

  private String formatPercentage(double ratio) {
    return String.format("%.2f", new Object[] { Double.valueOf(ratio * 100.0D) }) + "%";
  }

  private int percentageFromRatio(double ratio) {
    return (int)(ratio * 100.0D);
  }

  public void reset() {
    this.numRead = 0;
    this.numWritten = 0;
    this.startTime = new Time();
  }

  public String getStartTime() {
    return this.startTime.toString();
  }

  public String getElapsedTime() {
    return new Time().subtract(this.startTime).toString();
  }

  public String getRemainingTime() {
    Time elapsed = new Time().subtract(this.startTime);
    int elapsedSeconds = elapsed.getTotalSeconds();
    double percentComplete = getPercentageComplete();
    int totalSeconds = (int)(elapsedSeconds / percentComplete);
    int remainingSeconds = totalSeconds - elapsedSeconds;
    return Time.fromSeconds(remainingSeconds).toString();
  }

  public void addObserver(IObserver observer) {
    synchronized (this.observers) {
      if (!(this.observers.contains(observer)))
        this.observers.add(observer);
    }
  }

    public void removeObserver(IObserver observer) {
        synchronized (observers) {
            observers.remove(observer);
        }
    }

    public void setReadFilter(CountingFilter readFilter) {
        readFilter.addCallback(new ICountingFilterCallback() {

            public void countReached(int newCount) {
                setNumRead(newCount);
            }
        });
    }

    public void setWriteFilter(CountingFilter writeFilter) {
        writeFilter.addCallback(new ICountingFilterCallback() {

            public void countReached(int newCount) {
                setNumWritten(newCount);
            }
        });
    }

    private void setNumRead(int newCount) {
        this.numRead = newCount;
        notifyObservers();
    }

    private void setNumWritten(int newCount) {
        this.numWritten = newCount;
        notifyObservers();
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

  public int getNumRead() {
    return this.numRead;
  }

    public int getNumWritten() {
        return numWritten;
    }
    
    public double getPassRatio(){
        double ratio = (double)numWritten / (double)numRead;
        return ratio;
    }
    
    public double getPercentageComplete(){
        double ratio = (double)numRead / (double)totalCount;
        return ratio;
    }
    
    private void notifyObservers(){
        synchronized(observers){
            for(IObserver observer : observers){
                observer.update();
            }
        }
    }
}
