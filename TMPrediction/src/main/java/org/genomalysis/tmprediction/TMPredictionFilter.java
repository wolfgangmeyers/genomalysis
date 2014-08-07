package org.genomalysis.tmprediction;

import java.io.IOException;
import java.util.List;

import org.genomalysis.plugin.configuration.annotations.PropertyGetter;
import org.genomalysis.plugin.configuration.annotations.PropertySetter;
import org.genomalysis.proteintools.IProteinSequenceFilter;
import org.genomalysis.proteintools.InitializationException;
import org.genomalysis.proteintools.ProteinSequence;

public class TMPredictionFilter implements IProteinSequenceFilter {

    private TMOutputParser outputParser;
    private int minimumSegments;
    private int maximumSegments;

    public TMPredictionFilter() {
        this.outputParser = new TMOutputParser();

        this.minimumSegments = 1;
        this.maximumSegments = 1;
    }

    @Override
    public void initialize() throws InitializationException {

    }

    @Override
    public boolean filterProteinSequence(ProteinSequence sequence) {
        TMAP tmap = new TMAP();
        String outputString;
        try {
            outputString = tmap.run("tmp/output", sequence.getData(), false);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        String header = sequence.getHeader() == null ? ">NO_HEADER" : sequence
                .getHeader();
        List<TMSegment> segments = this.outputParser.getPredictedSegments(
                outputString, header.split(" ")[0].substring(1));
        boolean result = (segments.size() >= this.minimumSegments)
                && (segments.size() <= this.maximumSegments);
        return result;
    }

    @PropertyGetter(PropertyName = "Minimum Segments")
    public int getMinimumSegments() {
        return this.minimumSegments;
    }

    @PropertySetter(PropertyName = "Minimum Segments")
    public void setMinimumSegments(int minimumSegments) {
        this.minimumSegments = minimumSegments;
    }

    @PropertyGetter(PropertyName = "Maximum Segments")
    public int getMaximumSegments() {
        return this.maximumSegments;
    }

    @PropertySetter(PropertyName = "Maximum Segments")
    public void setMaximumSegments(int maximumSegments) {
        this.maximumSegments = maximumSegments;
    }

}
