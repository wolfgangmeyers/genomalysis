package org.genomalysis.clustalw;

public class ClosenessConstraint
{
  private double lowerBound;
  private boolean enabled;

  public ClosenessConstraint()
  {
    this.lowerBound = 0D;
    this.enabled = true; }

  public double getLowerBound() {
    return this.lowerBound;
  }

  public void setLowerBound(double lowerBound) {
    this.lowerBound = lowerBound;
  }

  public boolean isEnabled() {
    return this.enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public String toString()
  {
    return "Lower Bound: " + getLowerBound() + "\tEnabled: " + isEnabled() + "\n";
  }
}