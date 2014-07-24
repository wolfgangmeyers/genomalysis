package org.genomalysis.tmprediction;

public class TMSegment
{
  private int startIndex;
  private int endIndex;

  public TMSegment()
  {
  }

  public TMSegment(int startIndex, int endIndex)
  {
    this.startIndex = startIndex;
    this.endIndex = endIndex;
  }

  public int getStartIndex() {
    return this.startIndex;
  }

  public void setStartIndex(int startIndex) {
    this.startIndex = startIndex;
  }

  public int getEndIndex() {
    return this.endIndex;
  }

  public void setEndIndex(int endIndex) {
    this.endIndex = endIndex;
  }

  public int getLength() {
    return (this.endIndex - this.startIndex);
  }

  public String toString()
  {
    return "TM Segment: " + this.startIndex + " - " + this.endIndex;
  }
}