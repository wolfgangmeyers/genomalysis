package org.genomalysis.proteintools.signalp;

public class SignalPOutputParserResult
{
  private SignalPVariable maxS;
  private SignalPVariable maxY;
  private SignalPVariable maxC;

  public SignalPOutputParserResult()
  {
  }

  public SignalPOutputParserResult(SignalPVariable maxS, SignalPVariable maxY, SignalPVariable maxC)
  {
    this.maxS = maxS;
    this.maxY = maxY;
    this.maxC = maxC;
  }

  public SignalPVariable getMaxS() {
    return this.maxS;
  }

  public void setMaxS(SignalPVariable maxS) {
    this.maxS = maxS;
  }

  public SignalPVariable getMaxY() {
    return this.maxY;
  }

  public void setMaxY(SignalPVariable maxY) {
    this.maxY = maxY;
  }

  public SignalPVariable getMaxC() {
    return this.maxC;
  }

  public void setMaxC(SignalPVariable maxC) {
    this.maxC = maxC;
  }
}