package org.genomalysis.proteintools;

import java.util.ArrayList;
import java.util.List;

public class ProteinDiagnosticResult
{
  private List<ProteinDiagnosticImageElement> graphicResults;
  private List<ProteinDiagnosticTextElement> textResults;

  public ProteinDiagnosticResult()
  {
    this.graphicResults = new ArrayList();
    this.textResults = new ArrayList(); }

  public void addDiagnostic(ProteinDiagnosticResult result) {
    this.graphicResults.addAll(result.getGraphicResults());
    this.textResults.addAll(result.getTextResults());
  }

  public List<ProteinDiagnosticImageElement> getGraphicResults() {
    return this.graphicResults;
  }

  public List<ProteinDiagnosticTextElement> getTextResults() {
    return this.textResults;
  }
}