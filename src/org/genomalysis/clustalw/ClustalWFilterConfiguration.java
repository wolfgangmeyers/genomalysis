package org.genomalysis.clustalw;

public class ClustalWFilterConfiguration
{
  private ClosenessConstraint identityConstraint;
  private ClosenessConstraint strongConstraint;
  private ClosenessConstraint weakConstraint;

  public ClustalWFilterConfiguration()
  {
    this.identityConstraint = new ClosenessConstraint();
    this.strongConstraint = new ClosenessConstraint();
    this.weakConstraint = new ClosenessConstraint(); }

  public ClosenessConstraint getIdentityConstraint() {
    return this.identityConstraint;
  }

  public void setIdentityConstraint(ClosenessConstraint identityConstraint) {
    this.identityConstraint = identityConstraint;
  }

  public ClosenessConstraint getStrongConstraint() {
    return this.strongConstraint;
  }

  public void setStrongConstraint(ClosenessConstraint strongConstraint) {
    this.strongConstraint = strongConstraint;
  }

  public ClosenessConstraint getWeakConstraint() {
    return this.weakConstraint;
  }

  public void setWeakConstraint(ClosenessConstraint weakConstraint) {
    this.weakConstraint = weakConstraint;
  }

  public String toString()
  {
    return "Identity:\n" + this.identityConstraint.toString() + "Strong:\n" + this.strongConstraint.toString() + "Weak:\n" + this.weakConstraint.toString();
  }
}