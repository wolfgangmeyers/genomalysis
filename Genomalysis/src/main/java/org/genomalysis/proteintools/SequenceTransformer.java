/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.genomalysis.proteintools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.genomalysis.plugin.configuration.annotations.Author;
import org.genomalysis.plugin.configuration.annotations.Documentation;

@Documentation("Sequence Transformer uses the default ISequenceIO to read in an input file and output a filtered set of sequences into an output file. Takes a collection of IProteinSequenceFilter to filter input files.")
@Author(Name = "Wolfgang Meyers", EmailAddress = "wolfgangmeyers@gmail.com")
public class SequenceTransformer {
    private ISequenceIO dao;
    private boolean running = false;

    public void cancelTransform() {
        this.running = false;
    }

    public SequenceTransformer() {
        this.dao = SequenceIOImpl.getDefaultIO();
    }

    public ISequenceIO getDao() {
        return this.dao;
    }

    public void setDao(ISequenceIO dao) {
        this.dao = dao;
    }

    public void transformSequenceFile(InputStream inFile, OutputStream outFile,
            List<IProteinSequenceFilter> filters) throws IOException {
        if (dao == null)
            throw new NullPointerException("Dao cannot be null");
        if (filters.size() == 0)
            throw new RuntimeException(
                    "No filters have been added to the transformer!");
        running = true;
        final List<IProteinSequenceFilter> filterList = filters;
        beforeFileLoad();
        // get full list of sequences
        final Iterator<ProteinSequence> sequences = dao.readSequences(inFile);

        Iterator<ProteinSequence> filtered = new Iterator<ProteinSequence>() {

            private ProteinSequence current;

            private void getNextValidSequence() {
                boolean criteriaMatches = false;
                current = null;
                while (sequences.hasNext() && !criteriaMatches && running) {
                    boolean sequenceMatches = true;
                    ProteinSequence sequence = sequences.next();
                    assert sequence != null;
                    for (IProteinSequenceFilter filter : filterList) {
                        try {
                            sequenceMatches = sequenceMatches
                                    && filter.filterProteinSequence(sequence);
                        } catch (RuntimeException ex) {
                            System.out
                                    .println("Error while processing the following sequence:");
                            System.out.println(sequence.getHeader());
                            System.out.println(sequence.getData());
                            try {
                                FileUtils.write(new File("error-sequence.txt"),
                                        sequence.getData());
                                System.out
                                        .println("Sequence data written to error-sequence.txt");
                            } catch (IOException ignored) {
                            }
                            throw new RuntimeException(filter.getClass()
                                    .getSimpleName() + ": " + ex.getMessage(),
                                    ex);
                        }
                    }
                    if (sequenceMatches) {
                        criteriaMatches = true;
                        current = sequence;
                    }
                }
            }

            public boolean hasNext() {
                if (!running) {
                    return false;
                }
                if (current == null) {
                    getNextValidSequence();
                }
                if (current == null || !running)
                    return false;
                return true;
            }

            public ProteinSequence next() {
                if (current == null)
                    getNextValidSequence();
                if (current == null || !running) {
                    return null;
                }
                // System.out.println("Writing Sequence Iterator: " +
                // writeCount++ + " sequences written so far...");
                ProteinSequence tmp = current;
                current = null;
                return tmp;
            }

            public void remove() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };

        dao.writeSequences(outFile, filtered);
        afterFileWrite();
    }

    public void transformSequenceFile(File inFile, File outFile,
            List<IProteinSequenceFilter> filters) throws IOException {
        FileInputStream istream = null;
        FileOutputStream ostream = null;
        try {
            istream = new FileInputStream(inFile);
            if (!outFile.exists()) {
                if (!outFile.createNewFile()) {
                    throw new IOException("Could not create output file: "
                            + outFile.getName());
                }
            } else if (outFile.isDirectory()) {
                throw new IOException("Specified output file is a directory");
            }
            ostream = new FileOutputStream(outFile);
            transformSequenceFile(istream, ostream, filters);
            ostream.flush();

        } finally {
            if (ostream != null)
                ostream.close();
            if (istream != null)
                istream.close();
        }
    }

    // these methods can be overridden in derived classes
    protected void beforeFileLoad() {
    }

    protected void afterFileWrite() {
    }
}
