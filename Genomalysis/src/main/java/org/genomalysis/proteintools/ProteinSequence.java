/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.genomalysis.proteintools;

import org.genomalysis.plugin.configuration.annotations.Documentation;

@Documentation("A simple class to hold sequence data. toString() method will format the sequence into FASTA format.")
public class ProteinSequence {
    private String header;
    private String data;

    public static ProteinSequence parse(String fastaSequence) {
        ProteinSequence sequence = new ProteinSequence();
        String[] parts = fastaSequence.split("\n");
        sequence.setHeader(parts[0]);
        StringBuffer buffer = new StringBuffer();
        for (int i = 1; i < parts.length; ++i)
            buffer.append(parts[i]);

        sequence.setData(buffer.toString());
        return sequence;
    }

    public String getName() {
        return getHeader().split(" ")[0].replace(">", "");
    }

    public int getLength() {
        return data.length();
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data.replaceAll("\\*", "");
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(getHeader() + "\n");
        for (int i = 0; i < getLength(); i += 50) {
            int substringLength = getLength() - i;
            substringLength = Math.min(50, substringLength);
            String line = getData().substring(i, substringLength + i);
            if (line.endsWith("*")) {
                line = line.substring(0, line.length() - 1);
            }
            buffer.append(line + "\n");
        }
        return buffer.toString();
    }
}
