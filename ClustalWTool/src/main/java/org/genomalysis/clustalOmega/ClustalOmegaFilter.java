package org.genomalysis.clustalOmega;

import java.io.IOException;
import java.io.Serializable;

import org.genomalysis.plugin.configuration.annotations.Author;
import org.genomalysis.plugin.configuration.annotations.Configurator;
import org.genomalysis.plugin.configuration.annotations.Documentation;
import org.genomalysis.proteintools.IProteinSequenceFilter;
import org.genomalysis.proteintools.InitializationException;
import org.genomalysis.proteintools.ProteinSequence;

@Documentation("The ClustalOmega filter uses the ClustalOmega program to determine how alike proteins are. You can specify how alike proteins must be (to your target protein) by editing the filter configuration.")
@Author(EmailAddress = "wolfgangmeyers@gmail.com", Name = "Wolfgang Meyers")
@Configurator(ClustalOmegaFilterConfigurator.class)
public class ClustalOmegaFilter implements IProteinSequenceFilter, Serializable {

	
	
	
    private static final long serialVersionUID = 1L;
    private ClustalOmegaInterface parser;
    private ClustalOmegaFilterConfiguration config;

    public ClustalOmegaFilter() {
        this.parser = new ClustalOmegaInterface();
        this.config = new ClustalOmegaFilterConfiguration();
    }

    public void initialize() throws InitializationException {
        try {
            ProcessBuilder pb = new ProcessBuilder(new String[] { "clustalo" });
            pb.start();
            ProteinSequence sequence = ProteinSequence.parse(config
                    .getSequenceData());
            if ((sequence.getHeader() == null) || (sequence.getData() == null)
                    || sequence.getData().isEmpty())
                throw new InitializationException(
                        new String[] { "ClustalOmegaFilter: Invalid protein sequence" });
        } catch (InitializationException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new InitializationException(
                    new String[] { "ClustalOmegaFilter: Could not find ClustalOmega" });
        }
    }

    public boolean filterProteinSequence(ProteinSequence sequence) {
        boolean result;
        try {
            result = config.getConjunction() == ClustalOmegaRuleConjunction.OR ? false
                    : true;
            ProteinSequence criteriaSequence = ProteinSequence.parse(config
                    .getSequenceData());
            if (sequence.getName().equals(criteriaSequence.getName()))
                criteriaSequence.setHeader(">copy_of_"
                        + criteriaSequence.getHeader().replaceAll(">", ""));

            ClustalOmegaOutput output = this.parser.runClustal(criteriaSequence,
                    sequence);

            for (ClustalOmegaRule rule : config.getRules()) {
                result = config.getConjunction() == ClustalOmegaRuleConjunction.OR ? result
                        || rule.testOutput(output)
                        : result && rule.testOutput(output);
            }

            return result;
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException("ClustalOmega failed to run:\n"
                    + ex.getMessage());
        }
    }

    public ClustalOmegaFilterConfiguration getConfig() {
        return this.config;
    }

    public void setConfig(ClustalOmegaFilterConfiguration config) {
        this.config = config;
    }
}