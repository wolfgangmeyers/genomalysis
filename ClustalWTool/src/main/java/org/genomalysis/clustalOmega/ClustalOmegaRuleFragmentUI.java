package org.genomalysis.clustalOmega;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.border.LineBorder;

public class ClustalOmegaRuleFragmentUI extends JPanel {

    private static final long serialVersionUID = 1L;

    
    
    public ClustalOmegaRule getRule() {
        ClustalOmegaRule rule = new ClustalOmegaRule();
        rule.setWeak(chckbxWeak.isSelected());
        rule.setStrong(chckbxStrong.isSelected());
        rule.setIdentity(chckbxIdentity.isSelected());
        rule.setComparisonType((ClustalOmegaRuleComparisonType) cbComparisonType
                .getSelectedItem());
        rule.setAmountType((ClustalOmegaRuleAmountType) cbAmountType
                .getSelectedItem());
        rule.setAmount((Integer) spinnerAmount.getValue());
        return rule;
    }

    public void setRule(ClustalOmegaRule rule) {
        chckbxWeak.setSelected(rule.isWeak());
        chckbxStrong.setSelected(rule.isStrong());
        chckbxIdentity.setSelected(rule.isIdentity());
        if (rule.getComparisonType() == null) {
            rule.setComparisonType(ClustalOmegaRuleComparisonType.GT);
        }
        cbComparisonType.setSelectedItem(rule.getComparisonType());
        if (rule.getAmountType() == null) {
            rule.setAmountType(ClustalOmegaRuleAmountType.PERCENTAGE);
        }
        cbAmountType.setSelectedItem(rule.getAmountType());
        spinnerAmount.setValue(rule.getAmount());
    }

    private JButton btnDeleteThisRule;
    private JCheckBox chckbxWeak;
    private JCheckBox chckbxStrong;
    private JCheckBox chckbxIdentity;
    private JSpinner spinnerAmount;
    private JComboBox<ClustalOmegaRuleComparisonType> cbComparisonType;
    private JComboBox<ClustalOmegaRuleAmountType> cbAmountType;

    /**
     * Create the panel.
     */
    public ClustalOmegaRuleFragmentUI() {
        setBorder(new LineBorder(new Color(0, 0, 0)));
        setLayout(new BorderLayout(0, 0));

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

        JLabel lblMustBe = new JLabel("Must be:");
        panel.add(lblMustBe);

        cbComparisonType = new JComboBox<ClustalOmegaRuleComparisonType>();
        panel.add(cbComparisonType);
        cbComparisonType
                .setModel(new DefaultComboBoxModel<ClustalOmegaRuleComparisonType>(
                        ClustalOmegaRuleComparisonType.values()));

        spinnerAmount = new JSpinner();
        panel.add(spinnerAmount);

        cbAmountType = new JComboBox<ClustalOmegaRuleAmountType>();
        panel.add(cbAmountType);
        cbAmountType.setModel(new DefaultComboBoxModel<ClustalOmegaRuleAmountType>(
                ClustalOmegaRuleAmountType.values()));

        JPanel panel_1 = new JPanel();
        add(panel_1, BorderLayout.SOUTH);

        btnDeleteThisRule = new JButton("Delete this rule");
        panel_1.add(btnDeleteThisRule);

    }

    public JButton getBtnDelete() {
        return btnDeleteThisRule;
    }
}
