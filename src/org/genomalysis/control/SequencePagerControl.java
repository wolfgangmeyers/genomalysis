/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.genomalysis.control;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.genomalysis.fastaIndexing.Index;
import org.genomalysis.fastaIndexing.IndexEntry;
import org.genomalysis.fastaIndexing.IndexPager;
import org.genomalysis.fastaIndexing.io.FileIndexIO;
import org.genomalysis.proteintools.ProteinSequence;

/**
 *
 * @author ameyers
 */
public class SequencePagerControl {
    private IndexPager pager = new IndexPager();
    private int currentPage;
    private FileIndexIO indexIO = new FileIndexIO();
    private List<IndexEntry> cachedEntries = null;

    public boolean nextPage() {
        boolean success = false;
        if(currentPage + 1 < pager.getNumberOfPages()){
            currentPage++;
            success = true;
            cachedEntries = null;
        }
        return success;
    }

    public boolean previousPage() {
        boolean success = false;
        if(currentPage > 0){
            currentPage--;
            success = true;
            cachedEntries = null;
        }
        return success;
    }

    public boolean firstPage() {
         boolean success = false;
        if(currentPage > 0){
            currentPage = 0;
            success = true;
            cachedEntries = null;
        }
        return success;
    }

    public boolean lastPage() {
        boolean success = false;
        if(currentPage < pager.getNumberOfPages() - 1){
            currentPage = pager.getNumberOfPages() - 1;
            success = true;
            cachedEntries = null;
        }
        return success;
    }

    public List<String> getCurrentPageItems() throws IOException {
        List<String> pageItems = new ArrayList<String>();
        
        if(cachedEntries == null){
            cachedEntries = pager.getPage(currentPage);
        }
        
        for(IndexEntry entry : cachedEntries){
            pageItems.add(entry.getSequenceName());
        }
        return pageItems;
    }

    public ProteinSequence getItemDetails(int index) {
        return pager.fetchCachedSequence(index);
    }

    public boolean hasNextPage() {
         return currentPage < pager.getNumberOfPages() - 1;
    }

    public boolean hasPreviousPage() {
        return currentPage > 0;
    }

    public int getCurrentPage() {
        return this.currentPage;
    }

    public int getTotalPages() {
        return pager.getNumberOfPages();
    }

    public void loadFile(File file) throws IOException {
        String filename = file.getName();
        if(!file.exists()){
            throw new FileNotFoundException(filename + " could not be found");
        }
        
        
        
        //the index file will be the same as the original, except the
        //extension (if there is one) will be replaced with ".index"
        int indexOfExtension = filename.lastIndexOf(".");
        String basename = filename;
        if(indexOfExtension != -1){
            basename = filename.substring(0, indexOfExtension);
        }
        String indexName = basename + ".index";
        //if the index file does not exist, creat it
        File indexFile = new File(indexName);
        Index index = null;
        if(!indexFile.exists()){
            indexFile.createNewFile();
            index = indexIO.indexFasta(file);
            indexIO.writeBinaryIndex(index, indexFile);
        }else{
            index = indexIO.readBinaryIndex(indexFile);
            if(index.getLastModified() != indexFile.lastModified()){
                index = indexIO.indexFasta(file);
                indexIO.writeBinaryIndex(index, indexFile);
            }else{
                index = indexIO.readBinaryIndex(indexFile);
            }
        }
        pager.setIndex(index);
        currentPage = 0;
        cachedEntries = null;
    }

    public int getResultsPerPage() {
        return pager.getPageSize();
    }

    public void setResultsPerPage(int results) {
        pager.setPageSize(results);
    }
    
    public void reset(){
        try {
            pager.setIndex(null);
            currentPage = 0;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
    }
}
