package org.genomalysis.clustalw;

public enum ClustalWRuleAmountType {
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
