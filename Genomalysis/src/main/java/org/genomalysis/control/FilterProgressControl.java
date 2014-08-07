/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.genomalysis.control;

import org.genomalysis.control.filters.CountingFilter;
import org.genomalysis.control.filters.ICountingFilterCallback;

/**
 *
 * @author ameyers
 */
public class FilterProgressControl implements IObservable {

    private int totalCount;
    private int numRead;
    private int numWritten;
    private Time startTime;
    private EventSupport eventSupport = new EventSupport();

    public FilterProgressControl() {
    }

    public String getProgressReport() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("Total sequences in input file: " + getTotalCount()
                + "\n");
        buffer.append("Total sequences read from input file: " + getNumRead()
                + "\n");
        buffer.append("Total sequences written to output file: "
                + getNumWritten() + "\n");
        buffer.append("Filter percent complete: "
                + formatPercentage(getPercentageComplete()) + "\n");
        buffer.append("Filter Pass Ratio: " + formatPercentage(getPassRatio())
                + "\n");
        buffer.append("Time elapsed: " + getElapsedTime());
        return buffer.toString();
    }

    private String formatPercentage(double ratio) {
        return String.format("%.2f",
                new Object[] { Double.valueOf(ratio * 100.0D) })
                + "%";
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
        int totalSeconds = (int) (elapsedSeconds / percentComplete);
        int remainingSeconds = totalSeconds - elapsedSeconds;
        return Time.fromSeconds(remainingSeconds).toString();
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
        eventSupport.notifyObservers();
    }

    private void setNumWritten(int newCount) {
        this.numWritten = newCount;
        eventSupport.notifyObservers();
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

    public double getPassRatio() {
        double ratio = (double) numWritten / (double) numRead;
        return ratio;
    }

    public double getPercentageComplete() {
        double ratio = (double) numRead / (double) totalCount;
        return ratio;
    }

    @Override
    public void addObserver(IObserver observer) {
        eventSupport.addObserver(observer);
    }

    @Override
    public void removeObserver(IObserver observer) {
        eventSupport.addObserver(observer);
    }
}
