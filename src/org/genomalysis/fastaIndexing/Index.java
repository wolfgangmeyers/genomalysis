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

public class Index implements Serializable{
    
    private IndexEntryCollection indexEntries = new IndexEntryCollection();
    
    private String indexedFileName;
    private long lastModified;
    
    public Index(){}
    public Index(String indexedFileName){
        this.indexedFileName = indexedFileName;
    }

    public IndexEntryCollection getIndexEntries() {
        return indexEntries;
    }

    public void setIndexEntries(IndexEntryCollection indexEntries) {
        this.indexEntries = indexEntries;
    }

    public String getIndexedFileName() {
        return indexedFileName;
    }

    public void setIndexedFileName(String indexedFileName) {
        this.indexedFileName = indexedFileName;
    }
    
    public long getLastModified() {
        return lastModified;
    }

    public void setLastModified(long lastModified) {
        this.lastModified = lastModified;
    }
}
