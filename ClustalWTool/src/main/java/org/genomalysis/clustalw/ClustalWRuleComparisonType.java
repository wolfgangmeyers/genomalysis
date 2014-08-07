package org.genomalysis.clustalw;

public enum ClustalWRuleComparisonType {
    GT {
        @Override
        public String toString() {
            return "greater than";
        }
    }, LT {
        @Override
        public String toString() {
            return "less than";
        }
    }, EQ {
        @Override
        public String toString() {
            return "equal to";
        }
    };
    
    public abstract String toString();

}
