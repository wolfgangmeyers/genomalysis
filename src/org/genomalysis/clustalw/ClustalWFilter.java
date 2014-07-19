package org.genomalysis.clustalw;

import java.io.IOException;

import org.genomalysis.plugin.configuration.annotations.Author;
import org.genomalysis.plugin.configuration.annotations.Documentation;
import org.genomalysis.plugin.configuration.annotations.PropertyGetter;
import org.genomalysis.plugin.configuration.annotations.PropertySetter;
import org.genomalysis.proteintools.IProteinSequenceFilter;
import org.genomalysis.proteintools.InitializationException;
import org.genomalysis.proteintools.ProteinSequence;

@Documentation("The ClustalW filter uses the ClustalW program to determine how alike proteins are. You can specify how alike proteins must be (to your target protein) by editing the filter configuration.")
@Author(EmailAddress="wolfgangmeyers@gmail.com", Name="Wolfgang Meyers")
public class ClustalWFilter
  implements IProteinSequenceFilter
{
  private ClustalWInterface parser;
  private String sequenceData;
  private ClustalWFilterConfiguration config;

  public ClustalWFilter()
  {
    this.parser = new ClustalWInterface();
    this.sequenceData = "";

    this.config = new ClustalWFilterConfiguration(); }

  public void initialize() throws InitializationException {
    try {
      ProcessBuilder pb = new ProcessBuilder(new String[] { "clustalw" });
      pb.start();
      ProteinSequence sequence = ProteinSequence.parse(getSequenceData());
      if ((sequence.getHeader() == null) || (sequence.getData() == null))
        throw new InitializationException(new String[] { "ClustalWFilter: Invalid protein sequence" });
    }
    catch (Exception ex) {
      throw new InitializationException(new String[] { "ClustalWFilter: Could not find ClustalW" }); }
  }

  public boolean filterProteinSequence(ProteinSequence sequence) {
    boolean result;
    try {
      result = true;
      ProteinSequence criteriaSequence = ProteinSequence.parse(getSequenceData());
      if (sequence.getName().equals(criteriaSequence.getName()))
        criteriaSequence.setHeader(">copy_of_" + criteriaSequence.getHeader().replaceAll(">", ""));

      ClustalWOutput output = this.parser.runClustal(criteriaSequence, sequence);

      double spillover = 0D;
      if (this.config.getIdentityConstraint().isEnabled()) {
        spillover = output.getIdentity_percentage() - this.config.getIdentityConstraint().getLowerBound();
        result = (result) && (spillover >= 0D);
      }
      if ((this.config.getStrongConstraint().isEnabled()) && (result)) {
        spillover = output.getStrong_percentage() + spillover - this.config.getStrongConstraint().getLowerBound();
        result = (result) && (spillover >= 0D);
      }
      if ((this.config.getWeakConstraint().isEnabled()) && (result)) {
        spillover = output.getWeak_percentage() + spillover - this.config.getWeakConstraint().getLowerBound();
        result = (result) && (spillover >= 0D);
      }
      return result;
    } catch (IOException ex) {
      ex.printStackTrace();
      throw new RuntimeException("ClustalW failed to run:\n" + ex.getMessage());
    }
  }

  @PropertyGetter(PropertyName="Settings")
  public ClustalWFilterConfiguration getConfig() {
    return this.config;
  }

  @PropertySetter(PropertyName="Settings", ConfiguratorType=ClustalWFilterConfigurator.class)
  public void setConfig(ClustalWFilterConfiguration config) {
    this.config = config;
  }

  @PropertyGetter(PropertyName="Sequence Data")
  public String getSequenceData() {
    return this.sequenceData;
  }

  @PropertySetter(PropertyName="Sequence Data")
  public void setSequenceData(String sequenceData) {
    this.sequenceData = sequenceData;
  }
}