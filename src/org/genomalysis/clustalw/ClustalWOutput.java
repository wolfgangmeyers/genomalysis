package org.genomalysis.clustalw;

public class ClustalWOutput
{
  private double identity_percentage;
  private double strong_percentage;
  private double weak_percentage;
  private double nomatch_percentage;
  private String raw_output = "";

  public ClustalWOutput()
  {
  }

  public ClustalWOutput(String raw_output, double identity_percentage, double strong_percentage, double weak_percentage, double nomatch_percentage)
  {
    this.raw_output = raw_output;
    this.identity_percentage = identity_percentage;
    this.strong_percentage = strong_percentage;
    this.weak_percentage = weak_percentage;
    this.nomatch_percentage = nomatch_percentage;
  }

  public double getIdentity_percentage() {
    return this.identity_percentage;
  }

  public void setIdentity_percentage(double identity_percentage) {
    this.identity_percentage = identity_percentage;
  }

  public double getStrong_percentage() {
    return this.strong_percentage;
  }

  public void setStrong_percentage(double strong_percentage) {
    this.strong_percentage = strong_percentage;
  }

  public double getWeak_percentage() {
    return this.weak_percentage;
  }

  public void setWeak_percentage(double weak_percentage) {
    this.weak_percentage = weak_percentage;
  }

  public double getNomatch_percentage() {
    return this.nomatch_percentage;
  }

  public void setNomatch_percentage(double nomatch_percentage) {
    this.nomatch_percentage = nomatch_percentage;
  }

  public String getRaw_output() {
    return this.raw_output;
  }

  public void setRaw_output(String raw_output) {
    this.raw_output = raw_output;
  }
}