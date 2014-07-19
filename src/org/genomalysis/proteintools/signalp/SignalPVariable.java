package org.genomalysis.proteintools.signalp;

public class SignalPVariable
{
  private double value;
  private double cutoffValue;

  public String toString()
  {
    return "{Value=" + this.value + ", Cutoff=" + this.cutoffValue + "}";
  }

  public double getValue() {
    return this.value;
  }

  public SignalPVariable(double value, double cutoffValue) {
    this.value = value;
    this.cutoffValue = cutoffValue; }

  public SignalPVariable() {
  }

  public void setValue(double value) {
    this.value = value;
  }

  public double getCutoffValue() {
    return this.cutoffValue;
  }

  public void setCutoffValue(double cutoffValue) {
    this.cutoffValue = cutoffValue;
  }

  public boolean isAtOrAboveCutoff() {
    return (this.value >= this.cutoffValue);
  }
}