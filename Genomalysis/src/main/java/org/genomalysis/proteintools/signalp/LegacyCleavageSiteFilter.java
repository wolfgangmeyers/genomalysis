package org.genomalysis.proteintools.signalp;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Logger;

import org.genomalysis.plugin.configuration.annotations.Author;
import org.genomalysis.plugin.configuration.annotations.Documentation;
import org.genomalysis.proteintools.IProteinSequenceFilter;
import org.genomalysis.proteintools.ISequenceIO;
import org.genomalysis.proteintools.InitializationException;
import org.genomalysis.proteintools.ProteinSequence;
import org.genomalysis.proteintools.SequenceIOImpl;

@Documentation("CleavageSiteFilter:   This filter uses the SignalP program to predict cleavage sites and secretion signals. Sequences without cleavage sites and secretion signals will not pass this filter. Make sure you have SignalP installed and on your PATH variable before using this filter. SignalP can be downloaded for academic and research use at: http://www.cbs.dtu.dk/cgi-bin/nph-sw_request?signalp")
@Author(Name="Wolfgang Meyers", EmailAddress="wolfgangmeyers@gmail.com")
public class LegacyCleavageSiteFilter
  implements IProteinSequenceFilter
{
  private static String newline;
  private SignalPOutputParser parser;
  private ISequenceIO io;

  public LegacyCleavageSiteFilter()
  {
    this.parser = new SignalPOutputParser();
    this.io = SequenceIOImpl.getDefaultIO(); }

  public boolean filterProteinSequence(ProteinSequence sequence) {
    boolean result = false;
    try {
      ArrayList list = new ArrayList();
      list.add(sequence);

      File file = new File("tmp.seq");
      if (file.exists())
        file.delete();

      file.createNewFile();

      FileOutputStream fos = new FileOutputStream(file);
      this.io.writeSequences(fos, list.iterator());
      fos.flush();
      fos.close();

      ByteArrayOutputStream bout = new ByteArrayOutputStream();
      PrintWriter pw = new PrintWriter(bout);

      ProcessBuilder builder = new ProcessBuilder(new String[] { "signalp", "-t", "euk", "tmp.seq" });
      Process process = builder.start();
      InputStream processOutput = process.getInputStream();
      InputStreamReader reader1 = new InputStreamReader(processOutput);
      BufferedReader reader2 = new BufferedReader(reader1);

      String line = null;
      while ((line = reader2.readLine()) != null)
        pw.write(line + "\n");

      pw.flush();

      ByteArrayInputStream bin = new ByteArrayInputStream(bout.toByteArray());
      SignalPOutputParserResult reslt = this.parser.getResults(bin);
      bin.close();
      result = (reslt.getMaxC().isAtOrAboveCutoff()) && (reslt.getMaxS().isAtOrAboveCutoff()) && (reslt.getMaxY().isAtOrAboveCutoff());

      file.delete();
    }
    catch (Exception ex) {
      Logger.getLogger("CleavageSiteFilter").warning(ex.getMessage());
    }

    return result;
  }

  public void initialize()
    throws InitializationException
  {
      //check for signalp
    try
    {
      ProcessBuilder builder = new ProcessBuilder(new String[] { "signalp" });
      builder.start();
    }
    catch (IOException ex)
    {
      InitializationException ex2 = new InitializationException(new String[] { "SignalP executable could not be found. \nMake sure that SignalP is installed and included in your \nPATH variable (Consult documentation on how to obtain SignalP" });
      throw ex2;
    }

    //check for gawk (required by signalp)
    try
    {
      ProcessBuilder builder = new ProcessBuilder(new String[] { "gawk" });
      builder.start();
    }
    catch (IOException ex)
    {
      InitializationException ex2 = new InitializationException(new String[] { "gawk executable could not be found. \nMake sure that gawk is installed and included in your \nPATH variable"});
      throw ex2;
    }
  }
}