/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.genomalysis.fastaIndexing;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import org.genomalysis.proteintools.ProteinSequence;

/**
 *
 * @author ameyers
 */
public class IndexPager {
    private int pageSize = 1000;
    private Index index;
    private RandomAccessFile fileIO = null;

    private static final int minimumPageSize = 100;
    private static final int maximumPageSize = 10000;

    private List<ProteinSequence> sequenceCache = new ArrayList<ProteinSequence>();

    public int getNumberOfPages() {
        if (index == null)
            return 0;
        return (int) Math.ceil(index.getIndexEntries().getEntries().size()
                / (double) pageSize);
    }

    public List<IndexEntry> getPage(int pageNr) throws IOException {
        sequenceCache.clear();
        List<IndexEntry> entries = new ArrayList<IndexEntry>();
        if (index != null) {
            if (pageNr * pageSize < index.getIndexEntries().getEntries().size()) {
                int startIndex = pageNr * pageSize;
                int totalEntries = index.getIndexEntries().getEntries().size();
                for (int i = startIndex; i < startIndex + pageSize
                        && i < totalEntries; i++) {
                    IndexEntry entry = index.getIndexEntries().getEntries()
                            .get(i);
                    entries.add(entry);
                    ProteinSequence sequence = fetchSequence(entry);
                    sequenceCache.add(sequence);
                }
            }
        }
        return entries;
    }

    public ProteinSequence fetchCachedSequence(int index) {
        ProteinSequence result = null;
        if (this.index != null) {
            if (index >= 0 && index < sequenceCache.size()) {
                result = sequenceCache.get(index);
            }
        }
        return result;
    }

    public ProteinSequence fetchSequence(IndexEntry entry) throws IOException {
        if (index == null)
            return null;
        byte[] buffer = new byte[entry.getEndIndex() - entry.getStartIndex()];
        fileIO.seek(entry.getStartIndex());
        fileIO.read(buffer);

        ProteinSequence sequence = ProteinSequence.parse(new String(buffer));
        return sequence;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        if (pageSize >= minimumPageSize && pageSize <= maximumPageSize)
            this.pageSize = pageSize;
    }

    public Index getIndex() {
        return index;
    }

    public void setIndex(Index index) throws IOException {
        this.index = index;
        if (fileIO != null)
            fileIO.close();
        if (index != null)
            this.fileIO = new RandomAccessFile(index.getIndexedFileName(), "r");
    }
}
