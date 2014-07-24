package org.genomalysis.proteintools;

import java.util.List;

import org.genomalysis.plugin.configuration.annotations.Author;
import org.genomalysis.plugin.configuration.annotations.Documentation;

@Documentation("All implementing classes should assemble a diagnostic result, which can have multiple graphic and text based diagnostic results.")
@Author(Name="Wolfgang Meyers", EmailAddress="wolfgangmeyers@gmail.com")
public abstract interface IProteinDiagnosticsTool
{
  public abstract void initialize()
    throws org.genomalysis.proteintools.InitializationException;

  public abstract ProteinDiagnosticResult runDiagnostics(List<ProteinSequence> paramList);
}