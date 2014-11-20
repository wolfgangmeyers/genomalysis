package org.genomalysis.clustalw;

import java.io.IOException;
import java.io.Serializable;

import org.genomalysis.plugin.configuration.annotations.Author;
import org.genomalysis.plugin.configuration.annotations.Configurator;
import org.genomalysis.plugin.configuration.annotations.Documentation;
import org.genomalysis.proteintools.IProteinSequenceFilter;
import org.genomalysis.proteintools.InitializationException;
import org.genomalysis.proteintools.ProteinSequence;

@Documentation("The ClustalW filter uses the ClustalW program to determine how alike proteins are. You can specify how alike proteins must be (to your target protein) by editing the filter configuration.")
@Author(EmailAddress = "wolfgangmeyers@gmail.com", Name = "Wolfgang Meyers")
@Configurator(ClustalWFilterConfigurator.class)
public class ClustalWFilter implements IProteinSequenceFilter, Serializable {

	
	
	
    private static final long serialVersionUID = 1L;
    private ClustalWInterface parser;
    private ClustalWFilterConfiguration config;

    public ClustalWFilter() {
        this.parser = new ClustalWInterface();
        this.config = new ClustalWFilterConfiguration();
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
                        new String[] { "ClustalWFilter: Invalid protein sequence" });
        } catch (InitializationException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new InitializationException(
                    new String[] { "ClustalWFilter: Could not find ClustalW" });
        }
    }

    public boolean filterProteinSequence(ProteinSequence sequence) {
        boolean result;
        try {
            result = config.getConjunction() == ClustalWRuleConjunction.OR ? false
                    : true;
            ProteinSequence criteriaSequence = ProteinSequence.parse(config
                    .getSequenceData());
            if (sequence.getName().equals(criteriaSequence.getName()))
                criteriaSequence.setHeader(">copy_of_"
                        + criteriaSequence.getHeader().replaceAll(">", ""));

            ClustalWOutput output = this.parser.runClustal(criteriaSequence,
                    sequence);

            for (ClustalWRule rule : config.getRules()) {
                result = config.getConjunction() == ClustalWRuleConjunction.OR ? result
                        || rule.testOutput(output)
                        : result && rule.testOutput(output);
            }

            return result;
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException("ClustalW failed to run:\n"
                    + ex.getMessage());
        }
    }

    public ClustalWFilterConfiguration getConfig() {
        return this.config;
    }

    public void setConfig(ClustalWFilterConfiguration config) {
        this.config = config;
    }
}