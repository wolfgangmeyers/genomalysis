package org.genomalysis.tmprediction;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TMOutputParser
{
  public List<TMSegment> getPredictedSegments(String output, String proteinName)
  {
    List detectedSegments = new ArrayList();

    int index = output.indexOf("PREDICTED TRANSMEMBRANE SEGMENTS");
    if (index != -1) {
      output = output.substring(index);
      String[] lines = output.split("(\n)|(\r\n)");
      String[] arr$ = lines; int len$ = arr$.length; for (int i$ = 0; i$ < len$; ++i$) { String line = arr$[i$];
        String trimmedLine = line.trim().replaceAll("\\s+", " ");

        if (trimmedLine.startsWith("TM"))
        {
          String[] tokens = trimmedLine.split(" ");
          int startIndex = Integer.parseInt(tokens[2]);
          int endIndex = Integer.parseInt(tokens[4]);
          TMSegment segment = new TMSegment(startIndex, endIndex);
          detectedSegments.add(segment);
        }
      }
    }
    return detectedSegments;
  }

  public static void main(String[] args) {
    StringBuffer buffer = new StringBuffer();
    TMOutputParser parser = new TMOutputParser();

    buffer.append("RESULTS from program TMAP, edition 53\n\n");
    buffer.append("Numbers give: a) number of transmembrane segment");
    buffer.append("              b) start of TM segment (alignment position / residue number)\n");
    buffer.append("              c) end of TM segment (alignment position / residue number)\n");
    buffer.append("              d) length of TM segment within parentheses\n\n");
    buffer.append("PREDICTED TOPOLOGY:  Nout  (contributions:  2 in  3 out  K in      )\n");
    buffer.append("'in' corresponds to 'cytosolic'; 'out' corresponds to 'non-cytosolic'\n\n");
    buffer.append("PREDICTED TRANSMEMBRANE SEGMENTS FOR SEQUENCE test2.msf\n\n");
    buffer.append("  TM  1:    4 -   20  (17.0)\n\n\n");
    buffer.append("PREDICTED TRANSMEMBRANE SEGMENTS FOR PROTEIN defensin1 \n\n");
    buffer.append("  TM  1:    4 -   20 (17)\n");
    String output = buffer.toString();
    List predictedSegments = parser.getPredictedSegments(output, "defensin1");
    for (Iterator i$ = predictedSegments.iterator(); i$.hasNext(); ) { TMSegment segment = (TMSegment)i$.next();
      System.out.println(segment);
    }
  }
}