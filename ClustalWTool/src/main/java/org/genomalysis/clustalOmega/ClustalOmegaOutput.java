package org.genomalysis.clustalw;

public class ClustalWOutput {

    private int identityCount;
    private int strongCount;
    private int weakCount;
    private int sequenceLength;
    private String rawOutput = "";

    public ClustalWOutput() {
    }

    public ClustalWOutput(String raw_output, int sequenceLength,
            int identityCount, int strongCount, int weakCount) {
        this.rawOutput = raw_output;
        this.sequenceLength = sequenceLength;
        this.identityCount = identityCount;
        this.strongCount = strongCount;
        this.weakCount = weakCount;
    }

    public int getIdentityCount() {
        return identityCount;
    }

    public void setIdentityCount(int identityCount) {
        this.identityCount = identityCount;
    }

    public int getStrongCount() {
        return strongCount;
    }

    public void setStrongCount(int strongCount) {
        this.strongCount = strongCount;
    }

    public int getWeakCount() {
        return weakCount;
    }

    public void setWeakCount(int weakCount) {
        this.weakCount = weakCount;
    }

    public double getIdentityPercentage() {
        return (double) identityCount / sequenceLength;
    }

    public double getStrongPercentage() {
        return (double) strongCount / sequenceLength;
    }

    public double getWeakPercentage() {
        return (double) weakCount / sequenceLength;
    }

    public String getRawOutput() {
        return this.rawOutput;
    }

    public void setRawOutput(String rawOutput) {
        this.rawOutput = rawOutput;
    }

    public Object getNomatchPercentage() {
        return 1.0 - (getWeakPercentage() + getStrongPercentage() + getIdentityPercentage());
    }
}