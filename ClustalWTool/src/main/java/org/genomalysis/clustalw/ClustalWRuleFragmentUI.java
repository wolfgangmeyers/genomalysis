package org.genomalysis.clustalw;

import javax.swing.JPanel;

import java.awt.GridLayout;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JButton;

public class ClustalWRuleFragmentUI extends JPanel {

    public ClustalWRule getRule() {
        ClustalWRule rule = new ClustalWRule();
        rule.setWeak(chckbxWeak.isSelected());
        rule.setStrong(chckbxStrong.isSelected());
        rule.setIdentity(chckbxIdentity.isSelected());
        if (rdbtnGreaterThan.isSelected()) {
            rule.setComparisonType(ClustalWRuleComparisonType.GT);
        } else if (rdbtnEqualTo.isSelected()) {
            rule.setComparisonType(ClustalWRuleComparisonType.LT);
        } else {
            rule.setComparisonType(ClustalWRuleComparisonType.EQ);
        }
        if (rdbtnPercent.isSelected()) {
            rule.setAmountType(ClustalWRuleAmountType.PERCENTAGE);
        } else {
            rule.setAmountType(ClustalWRuleAmountType.TOTAL_COUNT);
        }
        rule.setAmount((Integer) spinnerAmount.getValue());
        return rule;
    }

    public void setRule(ClustalWRule rule) {
        chckbxWeak.setSelected(rule.isWeak());
        chckbxStrong.setSelected(rule.isStrong());
        chckbxIdentity.setSelected(rule.isIdentity());
        switch (rule.getComparisonType()) {
        case GT:
            rdbtnGreaterThan.setSelected(true);
            break;
        case LT:
            rdbtnLessThan.setSelected(true);
            break;
        default:
            rdbtnEqualTo.setSelected(true);
            break;
        }
        switch (rule.getAmountType()) {
        case PERCENTAGE:
            rdbtnPercent.setSelected(true);
            break;
        default:
            rdbtnTotalCount.setSelected(true);
            break;
        }
        spinnerAmount.setValue(rule.getAmount());
    }

    private JButton btnDeleteThisRule;
    private JCheckBox chckbxWeak;
    private JCheckBox chckbxStrong;
    private JCheckBox chckbxIdentity;
    private JRadioButton rdbtnLessThan;
    private JRadioButton rdbtnEqualTo;
    private JRadioButton rdbtnGreaterThan;
    private JRadioButton rdbtnPercent;
    private JRadioButton rdbtnTotalCount;
    private JSpinner spinnerAmount;

    /**
     * Create the panel.
     */
    public ClustalWRuleFragmentUI() {
        setLayout(new GridLayout(4, 1, 0, 0));

        JPanel panel = new JPanel();
        add(panel);

        JLabel lblCombinationOf = new JLabel("Combination of:");
        panel.add(lblCombinationOf);

        chckbxWeak = new JCheckBox("Weak");
        panel.add(chckbxWeak);

        chckbxStrong = new JCheckBox("Strong");
        panel.add(chckbxStrong);

        chckbxIdentity = new JCheckBox("Identity");
        panel.add(chckbxIdentity);

        JPanel panel_1 = new JPanel();
        add(panel_1);

        JLabel lblMustBe = new JLabel("Must be:");
        panel_1.add(lblMustBe);

        ButtonGroup comparisonGroup = new ButtonGroup();

        rdbtnLessThan = new JRadioButton("Less than");
        panel_1.add(rdbtnLessThan);

        rdbtnEqualTo = new JRadioButton("Equal to");
        panel_1.add(rdbtnEqualTo);

        rdbtnGreaterThan = new JRadioButton("Greater than");
        panel_1.add(rdbtnGreaterThan);

        comparisonGroup.add(rdbtnLessThan);
        comparisonGroup.add(rdbtnGreaterThan);
        comparisonGroup.add(rdbtnEqualTo);

        JPanel panel_2 = new JPanel();
        add(panel_2);

        JLabel lblThisAmount = new JLabel("This amount:");
        panel_2.add(lblThisAmount);

        spinnerAmount = new JSpinner();
        panel_2.add(spinnerAmount);

        ButtonGroup amountTypeGroup = new ButtonGroup();
        rdbtnPercent = new JRadioButton("Percent");
        panel_2.add(rdbtnPercent);

        rdbtnTotalCount = new JRadioButton("Total Count");
        panel_2.add(rdbtnTotalCount);

        amountTypeGroup.add(rdbtnPercent);
        amountTypeGroup.add(rdbtnTotalCount);

        JPanel panel_3 = new JPanel();
        add(panel_3);

        btnDeleteThisRule = new JButton("Delete this rule");
        panel_3.add(btnDeleteThisRule);

    }

    public JButton getBtnDelete() {
        return btnDeleteThisRule;
    }
}
