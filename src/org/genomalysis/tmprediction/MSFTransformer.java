package org.genomalysis.tmprediction;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Iterator;

import org.genomalysis.proteintools.ISequenceIO;
import org.genomalysis.proteintools.ProteinSequence;
import org.genomalysis.proteintools.SequenceIOImpl;

public class MSFTransformer
{
  public void writeMSF(ProteinSequence sequence, OutputStream out)
  {
    String data = sequence.getData().replace("*", "");
    String name = sequence.getHeader().split(" ")[0].replace(">", "");
    PrintWriter writer = new PrintWriter(out);
    writer.println("Created by Transmembrane Prediction Filter");
    writer.println();
    writer.println("MSF: " + data.length());
    writer.println();
    writer.println("Name: " + name);
    writer.println();
    writer.println("//");
    writer.println(" ");
    writer.println(formatSequenceData(sequence));
    writer.flush();
  }

  public String formatSequenceData(ProteinSequence seq) {
    String tmp = seq.toString().replace("*", "");
    String name = seq.getHeader().split(" ")[0].replace(">", "");
    String[] parts = tmp.split("\n");
    StringBuffer buffer = new StringBuffer();
    for (int i = 1; i < parts.length; ++i) {
      buffer.append(name + " ");
      for (int j = 0; j < parts[i].length(); j += 10) {
        int endIndex = Math.min(j + 10, parts[i].length());
        String part = parts[i].substring(j, endIndex);
        buffer.append(part + ((endIndex == parts[i].length()) ? "" : " "));
      }
      if (i < parts.length - 1)
        buffer.append("\n\n\n");
    }
    return buffer.toString();
  }

  public static void main(String[] args) throws Exception {
    MSFTransformer xformer = new MSFTransformer();

    File file = new File(args[0]);
    FileInputStream in = new FileInputStream(file);
    ISequenceIO io = SequenceIOImpl.getDefaultIO();

    Iterator sequenceIter = io.readSequences(in);
    ProteinSequence sequence = (ProteinSequence)sequenceIter.next();
    in.close();

    File newFile = new File("output.msf");
    newFile.createNewFile();
    FileOutputStream out = new FileOutputStream(newFile);
    xformer.writeMSF(sequence, out);
    out.close();
  }
}