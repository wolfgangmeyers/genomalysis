package org.genomalysis.clustalOmega;

public enum ClustalOmegaRuleAmountType {
    PERCENTAGE {
        @Override
        public String toString() {
            return "percent";
        }
    },
    TOTAL_COUNT {
        @Override
        public String toString() {
            return "total count";
        }
    };

    public abstract String toString();
}
