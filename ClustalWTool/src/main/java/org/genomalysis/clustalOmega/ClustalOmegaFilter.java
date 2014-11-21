package org.genomalysis.clustalOmega;

import java.io.IOException;
import java.io.Serializable;

import org.genomalysis.plugin.configuration.annotations.Author;
import org.genomalysis.plugin.configuration.annotations.Configurator;
import org.genomalysis.plugin.configuration.annotations.Documentation;
import org.genomalysis.proteintools.IProteinSequenceFilter;
import org.genomalysis.proteintools.InitializationException;
import org.genomalysis.proteintools.ProteinSequence;

@Documentation("ClustalOmegaFilter: This filter uses Clustal Omega to do sequential pairwise alignments of a user-entered protein or DNA sequence to sequences in a fasta file. Yes, you read correctly, Clustal for pairwise alignments; turns out you can do that. Each sequence in the fasta file either passes or fails the filter based on rules, designated by the user, that specify how similar to the user-entered sequence the fasta file sequences have to be. The user can choose combinations of strong groups, weak groups, identities and designate specific numbers of matches or percentages. Additionally, the user can designate multiple rules in order to further restrict results. \n \nClustal Omega is typically used for multiple sequence alignments and employs multiple algorithms and options to create high quality alignments of large numbers of protein or DNA sequences. The usage here is atypical, but works well. \n \nClustal home page: http://www.clustal.org/")
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