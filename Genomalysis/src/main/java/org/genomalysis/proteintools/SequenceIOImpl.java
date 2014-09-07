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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.genomalysis.plugin.configuration.annotations.Author;
import org.genomalysis.plugin.configuration.annotations.Documentation;

@Documentation("Default implementation of the ISequenceIO interface reads and writes files of FASTA format.")
@Author(Name = "Wolfgang Meyers", EmailAddress = "wolfgangmeyers@gmail.com")
public class SequenceIOImpl implements ISequenceIO {

    private static ISequenceIO defaultIO = new SequenceIOImpl();

    public static ISequenceIO getDefaultIO() {
        return defaultIO;
    }

    public Iterator<ProteinSequence> readSequences(InputStream inFile)
            throws IOException {
        InputStreamReader reader1 = new InputStreamReader(inFile);
        final LineIterator lineIterator = IOUtils.lineIterator(reader1);
        Iterator<ProteinSequence> iterator = new Iterator<ProteinSequence>() {
            
            private String header = lineIterator.next();
            
            @Override
            public void remove() {
                throw new RuntimeException("Not Implemented");
            }
            
            @Override
            public ProteinSequence next() {
                if (!lineIterator.hasNext()) {
                    return null;
                }
                StringBuffer buff = new StringBuffer(header);
                header = null;
                while (lineIterator.hasNext()) {
                    String line = lineIterator.next();
                    if (line.isEmpty()) {
                        continue;
                    }
                    if (line.startsWith(">")) {
                        header = line;
                        break;
                    } else {
                        buff.append("\n" + line);
                    }
                }
                ProteinSequence seq = ProteinSequence.parse(buff.toString());
                return seq;
            }
            
            @Override
            public boolean hasNext() {
                return lineIterator.hasNext() && header != null;
            }
        };
        return iterator;
    }

    public Iterator<ProteinSequence> readSequences(File inFile)
            throws IOException {
        System.out.println("SequenceIOImpl: reading input file "
                + inFile.getAbsolutePath());
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

    public void writeSequences(OutputStream outFile,
            Iterator<ProteinSequence> data) throws IOException {
        OutputStreamWriter writer1 = new OutputStreamWriter(outFile);
        while (data.hasNext()) {
            ProteinSequence sequence = data.next();

            // I moved this logic into the sequences

            // System.out.println(sequence.toString());
            // writer1.write(sequence.getHeader() + "\n");
            // for (int i = 0; i < sequence.getLength(); i += 50) {
            // int substringLength = sequence.getLength() - i;
            // substringLength = Math.min(50, substringLength);
            // String line = sequence.getData().substring(i, substringLength +
            // i);
            // writer1.write(line + "\n");
            // }
            writer1.write(sequence.toString());
        }
        writer1.flush();
    }

    public void writeSequences(File outFile, Iterator<ProteinSequence> data)
            throws IOException {
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
