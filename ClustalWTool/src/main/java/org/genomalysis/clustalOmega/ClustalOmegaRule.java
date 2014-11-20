package org.genomalysis.clustalOmega;



import java.io.Serializable;

public class ClustalOmegaRule implements Serializable {

    private static final long serialVersionUID = 1L;
    private boolean weak;
    private boolean strong;
    private boolean identity;
    private ClustalOmegaRuleComparisonType comparisonType;
    private int amount;
    private ClustalOmegaRuleAmountType amountType;

    public boolean isWeak() {
        return weak;
    }

    public void setWeak(boolean weak) {
        this.weak = weak;
    }

    public boolean isStrong() {
        return strong;
    }

    public void setStrong(boolean strong) {
        this.strong = strong;
    }

    public boolean isIdentity() {
        return identity;
    }

    public void setIdentity(boolean identity) {
        this.identity = identity;
    }

    public ClustalOmegaRuleComparisonType getComparisonType() {
        return comparisonType;
    }

    public void setComparisonType(ClustalOmegaRuleComparisonType comparisonType) {
        this.comparisonType = comparisonType;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public ClustalOmegaRuleAmountType getAmountType() {
        return amountType;
    }

    public void setAmountType(ClustalOmegaRuleAmountType amountType) {
        this.amountType = amountType;
    }

    public ClustalOmegaRule(boolean weak, boolean strong, boolean identity,
            ClustalOmegaRuleComparisonType comparisonType, int amount,
            ClustalOmegaRuleAmountType amountType) {
        super();
        this.weak = weak;
        this.strong = strong;
        this.identity = identity;
        this.comparisonType = comparisonType;
        this.amount = amount;
        this.amountType = amountType;
    }

    public ClustalOmegaRule() {

    }

    public static ClustalOmegaRule defaultRule() {
        return new ClustalOmegaRule(true, true, true,
                ClustalOmegaRuleComparisonType.EQ, 50,
                ClustalOmegaRuleAmountType.PERCENTAGE);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + amount;
        result = prime * result
                + ((amountType == null) ? 0 : amountType.hashCode());
        result = prime * result
                + ((comparisonType == null) ? 0 : comparisonType.hashCode());
        result = prime * result + (identity ? 1231 : 1237);
        result = prime * result + (strong ? 1231 : 1237);
        result = prime * result + (weak ? 1231 : 1237);
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
        ClustalOmegaRule other = (ClustalOmegaRule) obj;
        if (amount != other.amount)
            return false;
        if (amountType != other.amountType)
            return false;
        if (comparisonType != other.comparisonType)
            return false;
        if (identity != other.identity)
            return false;
        if (strong != other.strong)
            return false;
        if (weak != other.weak)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "ClustalOmegaRuleFragment [weak=" + weak + ", strong=" + strong
                + ", identity=" + identity + ", comparisonType="
                + comparisonType + ", amount=" + amount + ", amountType="
                + amountType + "]";
    }

    public boolean testOutput(ClustalOmegaOutput output) {
        int amount = getAmount(output);
        switch (getComparisonType()) {
        case GT:
            return amount > getAmount();
        case LT:
            return amount < getAmount();
        default:
            return amount == getAmount();
        }
    }

    private int getAmount(ClustalOmegaOutput output) {
        switch (getAmountType()) {
        case PERCENTAGE:
            return getAmountPercentage(output);
        default:
            return getAmountTotalCount(output);
        }
    }

    private int getAmountTotalCount(ClustalOmegaOutput output) {
        int result = 0;
        if (weak) {
            result += output.getWeakCount();
        }
        if (identity) {
            result += output.getIdentityCount();
        }
        if (strong) {
            result += output.getStrongCount();
        }
        return result;
    }

    private int getAmountPercentage(ClustalOmegaOutput output) {
        int result = 0;
        if (weak) {
            result += (int) (output.getWeakPercentage() * 100);
        }
        if (identity) {
            result += (int) (output.getIdentityPercentage() * 100);
        }
        if (strong) {
            result += (int) (output.getStrongPercentage() * 100);
        }
        return result;
    }

}
