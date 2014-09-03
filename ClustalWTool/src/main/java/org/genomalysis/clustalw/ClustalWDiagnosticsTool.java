package org.genomalysis.clustalw;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.List;

import org.genomalysis.plugin.configuration.annotations.PropertyGetter;
import org.genomalysis.plugin.configuration.annotations.PropertySetter;
import org.genomalysis.proteintools.IProteinDiagnosticsTool;
import org.genomalysis.proteintools.InitializationException;
import org.genomalysis.proteintools.ProteinDiagnosticResult;
import org.genomalysis.proteintools.ProteinDiagnosticTextElement;
import org.genomalysis.proteintools.ProteinSequence;



/**
 * Runs Clustal and provides strong, weak, identity count and percentage as
 * text, as well as the non-matched percentage.
 * 
 * @author wolfgang
 *
 */
public class ClustalWDiagnosticsTool implements IProteinDiagnosticsTool {
    private ClustalWInterface parser;
    private String sequenceData;

    public ClustalWDiagnosticsTool() {
        this.parser = new ClustalWInterface();
        this.sequenceData = "";
    }

    public void initialize() throws InitializationException {
        try {
            ProcessBuilder builder = new ProcessBuilder(
                    new String[] { "clustalo" });
            builder.start();
        } catch (IOException ex) {
            throw new InitializationException(
                    new String[] { "ClustalWDiagnosticsTool: ClustalW is not installed" });
        }
        try {
            ProteinSequence sequence = ProteinSequence.parse(this.sequenceData);
            if ((sequence.getData() == null) || (sequence.getHeader() == null))
                throw new Exception("Invalid Sequence");
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new InitializationException(new String[] { ex.getMessage() });
        }
    }

    public ProteinDiagnosticResult runDiagnostics(
            List<ProteinSequence> sequences) {
        ProteinDiagnosticResult result = new ProteinDiagnosticResult();
        NumberFormat formatter = NumberFormat.getPercentInstance();
        ProteinSequence sequence = ProteinSequence.parse(this.sequenceData);
        for (ProteinSequence testSequence : sequences) {
            try {
                if (testSequence.getName().equals(sequence.getName()))
                    testSequence.setHeader(">copy_of_"
                            + testSequence.getHeader().replaceAll(">", ""));

                ClustalWOutput sequenceResult = this.parser.runClustal(
                        sequence, testSequence);
                StringBuffer buffer = new StringBuffer();
                buffer.append("ClustalW Output\n===============================\n\n");
                buffer.append(sequenceResult.getRawOutput());
                buffer.append("\n\nRatios\n====================================\n\n");
                buffer.append("Identity: "
                        + sequenceResult.getIdentityCount()
                        + " - "
                        + formatter.format(sequenceResult
                                .getIdentityPercentage()) + "\n");
                buffer.append("Strong: "
                        + sequenceResult.getStrongCount()
                        + " - "
                        + formatter.format(sequenceResult.getStrongPercentage())
                        + "\n");
                buffer.append("Weak: " + sequenceResult.getWeakCount() + " - "
                        + formatter.format(sequenceResult.getWeakPercentage())
                        + "\n");
                buffer.append("No Match: "
                        + formatter.format(sequenceResult
                                .getNomatchPercentage()) + "\n");

                result.getTextResults().add(
                        new ProteinDiagnosticTextElement(sequence.getName(),
                                buffer.toString()));
            } catch (Exception ex) {
                throw new RuntimeException(
                        "Failed to run diagnostics on sequence:\n"
                                + ex.getMessage());
            }
        }
        return result;
    }

    @PropertyGetter(PropertyName = "Sequence Data")
    public String getSequenceData() {
        return this.sequenceData;
    }

    @PropertySetter(PropertyName = "Sequence Data")
    public void setSequenceData(String sequenceData) {
        this.sequenceData = sequenceData;
    }
}