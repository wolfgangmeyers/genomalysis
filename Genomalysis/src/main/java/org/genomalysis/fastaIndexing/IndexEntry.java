/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.genomalysis.fastaIndexing;

import java.io.Serializable;

/**
 *
 * @author ameyers
 */
public class IndexEntry implements Serializable {

    private static final long serialVersionUID = 1L;

    private int startIndex;

    private int endIndex;

    private String sequenceName;

    public IndexEntry() {
    }

    public IndexEntry(int startIndex, int endIndex, String sequenceName) {
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.sequenceName = sequenceName;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }

    public String getSequenceName() {
        return sequenceName;
    }

    public void setSequenceName(String sequenceName) {
        this.sequenceName = sequenceName;
    }
}
