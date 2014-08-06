package org.genomalysis.clustalw;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class ClustalWFilterConfiguration implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<ClustalWRule> rules = new LinkedList<ClustalWRule>();
    private String sequenceData = "";

    public List<ClustalWRule> getRules() {
        return rules;
    }

    public void setRules(List<ClustalWRule> rules) {
        this.rules = rules;
    }

    public String getSequenceData() {
        return sequenceData;
    }

    public void setSequenceData(String sequenceData) {
        this.sequenceData = sequenceData;
    }

    public ClustalWFilterConfiguration() {
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
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
        ClustalWFilterConfiguration other = (ClustalWFilterConfiguration) obj;
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

    public ClustalWFilterConfiguration(List<ClustalWRule> rules,
            String sequenceData) {
        super();
        this.rules = rules;
        this.sequenceData = sequenceData;
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
        return "ClustalWFilterConfiguration [rules=" + rules
                + ", sequenceData=" + sequenceDataPreview() + "]";
    }
    
    

}