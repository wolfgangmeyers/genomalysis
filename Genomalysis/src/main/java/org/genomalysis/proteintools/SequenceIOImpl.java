/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.genomalysis.proteintools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.genomalysis.plugin.configuration.annotations.Author;
import org.genomalysis.plugin.configuration.annotations.Documentation;

@Documentation("Default implementation of the ISequenceIO interface reads and writesfiles of FASTA format.")
@Author(Name = "Wolfgang Meyers", EmailAddress = "wolfgangmeyers@gmail.com")
public class SequenceIOImpl
        implements ISequenceIO {

    private static ISequenceIO defaultIO = new SequenceIOImpl();

    public static ISequenceIO getDefaultIO() {
        return defaultIO;
    }

    public Iterator<ProteinSequence> readSequences(InputStream inFile)
            throws IOException {
        final List result = new ArrayList();
        InputStreamReader reader1 = new InputStreamReader(inFile);
        final BufferedReader reader2 = new BufferedReader(reader1);

        Iterator<ProteinSequence> iterator = new Iterator<ProteinSequence>() {

            private Iterator<ProteinSequence> internalIterator;
            private int readCount = 0;

            public boolean hasNext() {
                if (internalIterator == null) {
                    internalIterator = result.iterator();
                }
                if (!internalIterator.hasNext()) {
                    if (!refillBuffer()) {
                        return false;
                    } else {
                        internalIterator = result.iterator();
                        return true;
                    }
                } else {
                    return true;
                }
            }

            public ProteinSequence next() {
                if (hasNext()) //System.out.println("Reading Sequence Iterator: " + readCount++ + " sequences read so far...");
                {
                    return internalIterator.next();
                }
                return null;
            }

            public void remove() {
                //internalIterator.remove();
            }

            private boolean refillBuffer() {
                String line;
                try {
                    line = "";
                    result.clear();
                    this.internalIterator = null;
                    ProteinSequence current = new ProteinSequence();
                    StringBuffer buffer = new StringBuffer();
                    String lastLine = null;
                    boolean EOF = false;

                    while (result.size() < 300 && !EOF) {
                        do {
                            if ((line = reader2.readLine()) == null) {
                                EOF = true;
                            }
                        } while (line != null && line.equals(""));
                        if (!EOF) {
                            if (line.startsWith(">")) {
                                if (current.getHeader() == null) {
                                    current.setHeader(line);
                                } else {
                                    buffer.append(lastLine);
                                    current.setData(buffer.toString());
                                    if ((current.getData().indexOf("*") != -1) || (current.getData().indexOf("*") == current.getData().lastIndexOf("*"))) {
                                        result.add(current);
                                    }
                                    buffer = new StringBuffer();
                                    current = new ProteinSequence();
                                    current.setHeader(line);
                                }
                            } else if (line.length() < 50) {
                                buffer.append(line);
                                current.setData(buffer.toString());
                                if ((current.getData().indexOf("*") != -1) || (current.getData().indexOf("*") == current.getData().lastIndexOf("*"))) {
                                    result.add(current);
                                }
                                buffer = new StringBuffer();
                                current = new ProteinSequence();
                            } else {
                                buffer.append(line);
                            }
                            lastLine = line;
                        }
                    }

                    label279:
                    return (result.size() > 0);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

            }
        };
        return iterator;
    }

    public Iterator<ProteinSequence> readSequences(File inFile) throws IOException {
        System.out.println("SequenceIOImpl: reading input file " + inFile.getAbsolutePath());
        FileInputStream fs = null;
        Iterator<ProteinSequence> result = null;
        try {
            fs = new FileInputStream(inFile);
            System.out.println("SequenceIOImpl: opened input file");
            result = readSequences(fs);
        } finally {
            if (fs != null) {
                fs.close();
            }
        }
        return result;
    }

    public void writeSequences(OutputStream outFile, Iterator<ProteinSequence> data) throws IOException {
        OutputStreamWriter writer1 = new OutputStreamWriter(outFile);
        while (data.hasNext()) {
            ProteinSequence sequence = data.next();

            //I moved this logic into the sequences

            //System.out.println(sequence.toString());
//            writer1.write(sequence.getHeader() + "\n");
//            for (int i = 0; i < sequence.getLength(); i += 50) {
//                int substringLength = sequence.getLength() - i;
//                substringLength = Math.min(50, substringLength);
//                String line = sequence.getData().substring(i, substringLength + i);
//                writer1.write(line + "\n");
//            }
            writer1.write(sequence.toString());
        }
        writer1.flush();
    }

    public void writeSequences(File outFile, Iterator<ProteinSequence> data) throws IOException {
        FileOutputStream fs = null;
        try {
            fs = new FileOutputStream(outFile);
            writeSequences(fs, data);
            fs.flush();
        } finally {
            if (fs != null) {
                fs.close();
            }
        }
    }
}
