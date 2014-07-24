/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.genomalysis.fastaIndexing;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ameyers
 */
public class IndexEntryCollection implements Serializable{
    
    
    private List<IndexEntry> indexEntries = new ArrayList<IndexEntry>();
    
    public List<IndexEntry> getEntries() {
        return indexEntries;
    }

    public void setEntries(List<IndexEntry> indexEntries) {
        this.indexEntries = indexEntries;
    }
    
}
