package org.genomalysis.tmprediction;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.genomalysis.plugin.configuration.annotations.PropertyGetter;
import org.genomalysis.plugin.configuration.annotations.PropertySetter;
import org.genomalysis.proteintools.IProteinSequenceFilter;
import org.genomalysis.proteintools.InitializationException;
import org.genomalysis.proteintools.ProteinSequence;

public class TMPredictionFilter
  implements IProteinSequenceFilter
{
  private MSFTransformer xformer;
  private TMOutputParser outputParser;
  private int minimumSegments;
  private int maximumSegments;

  public TMPredictionFilter()
  {
    this.xformer = new MSFTransformer();
    this.outputParser = new TMOutputParser();

    this.minimumSegments = 1;
    this.maximumSegments = 1; }

  public void initialize() throws InitializationException {
    try {
      ProcessBuilder pb = new ProcessBuilder(new String[] { "tmap" });
      pb.start();
    } catch (IOException ex) {
      throw new InitializationException(new String[] { "Could not find the tmap executable.\nMake sure tmap is in a directory included in\nyour PATH variable." });
    }
  }

  public boolean filterProteinSequence(ProteinSequence sequence)
  {
    boolean result = false;
    try
    {
      File sequenceFile = new File("/tmp/sequence.fasta");
      sequenceFile.createNewFile();
      FileOutputStream out = new FileOutputStream(sequenceFile);
      this.xformer.writeMSF(sequence, out);
      out.close();

      ProcessBuilder pb = new ProcessBuilder(new String[] { "tmap", "/tmp/sequence.fasta", "/tmp/output.res" });
      Process p = pb.start();
      p.waitFor();
      File outputFile = new File("/tmp/output.res");
      if (!(outputFile.exists()))
        throw new FileNotFoundException("Could not load result file from tmap");

      FileInputStream in = new FileInputStream(outputFile);
      byte[] buffer = new byte[(int)outputFile.length()];
      in.read(buffer);
      in.close();
      String outputString = new String(buffer);

      List segments = this.outputParser.getPredictedSegments(outputString, sequence.getHeader().split(" ")[0].substring(1));
      result = (segments.size() >= this.minimumSegments) && (segments.size() <= this.maximumSegments);
    }
    catch (InterruptedException ex) {
      Logger.getLogger(TMPredictionFilter.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IOException ex) {
      throw new RuntimeException(ex.getMessage(), ex);
    }
    return result;
  }

  @PropertyGetter(PropertyName="Minimum Segments")
  public int getMinimumSegments() {
    return this.minimumSegments;
  }

  @PropertySetter(PropertyName="Minimum Segments")
  public void setMinimumSegments(int minimumSegments) {
    this.minimumSegments = minimumSegments;
  }

  @PropertyGetter(PropertyName="Maximum Segments")
  public int getMaximumSegments() {
    return this.maximumSegments;
  }

  @PropertySetter(PropertyName="Maximum Segments")
  public void setMaximumSegments(int maximumSegments) {
    this.maximumSegments = maximumSegments;
  }
}