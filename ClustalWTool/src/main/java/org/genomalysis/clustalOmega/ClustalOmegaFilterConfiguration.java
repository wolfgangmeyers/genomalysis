package org.genomalysis.clustalOmega;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class ClustalOmegaFilterConfiguration implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<ClustalOmegaRule> rules = new LinkedList<ClustalOmegaRule>();
    private String sequenceData = "";
    private ClustalOmegaRuleConjunction conjunction;

    public List<ClustalOmegaRule> getRules() {
        return rules;
    }

    public void setRules(List<ClustalOmegaRule> rules) {
        this.rules = rules;
    }

    public String getSequenceData() {
        return sequenceData;
    }

    public void setSequenceData(String sequenceData) {
        this.sequenceData = sequenceData;
    }

    public ClustalOmegaRuleConjunction getConjunction() {
        return conjunction;
    }

    public void setConjunction(ClustalOmegaRuleConjunction conjunction) {
        this.conjunction = conjunction;
    }

    public ClustalOmegaFilterConfiguration() {
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((conjunction == null) ? 0 : conjunction.hashCode());
        result = prime * result + ((rules == null) ? 0 : rules.hashCode());
        result = prime * result
                + ((sequenceData == null) ? 0 : sequenceData.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ClustalOmegaFilterConfiguration other = (ClustalOmegaFilterConfiguration) obj;
        if (conjunction != other.conjunction)
            return false;
        if (rules == null) {
            if (other.rules != null)
                return false;
        } else if (!rules.equals(other.rules))
            return false;
        if (sequenceData == null) {
            if (other.sequenceData != null)
                return false;
        } else if (!sequenceData.equals(other.sequenceData))
            return false;
        return true;
    }

    public ClustalOmegaFilterConfiguration(List<ClustalOmegaRule> rules,
            String sequenceData, ClustalOmegaRuleConjunction conjunction) {
        super();
        this.rules = rules;
        this.sequenceData = sequenceData;
        this.conjunction = conjunction;
    }

    private String sequenceDataPreview() {
        if (sequenceData.length() < 8) {
            return sequenceData;
        } else {
            return sequenceData.substring(0, 5) + "...";
        }
    }

    @Override
    public String toString() {
        return "ClustalOmegaFilterConfiguration [rules=" + rules
                + ", sequenceData=" + sequenceDataPreview() + ", conjunction="
                + conjunction + "]";
    }

}