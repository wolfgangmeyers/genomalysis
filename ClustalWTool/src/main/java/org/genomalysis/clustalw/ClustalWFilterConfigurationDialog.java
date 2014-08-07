package org.genomalysis.clustalw;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class ClustalWFilterConfigurationDialog extends JDialog {

    private static final long serialVersionUID = 1L;
    private ClustalWFilterConfiguration myConfig;
    private JButton btnCancel;
    private JButton btnOK;
    private JPanel jPanel4;
    private JPanel jPanel5;
    private JPanel rulesPanel;
    private JPanel panel;
    private JButton btnAddNewRule;

    private List<ClustalWRuleFragmentUI> ruleFragments = new LinkedList<ClustalWRuleFragmentUI>();
    private JPanel panel_1;
    private JPanel panel_2;
    private JLabel lblSequenceData;
    private JTextArea txtSequenceData;
    private JRadioButton rdbtnAllMustBe;
    private JRadioButton rdbtnAtLeastOne;

    public ClustalWFilterConfigurationDialog(Frame parent) {
        super(parent, true);
        initComponents();
    }

    private void initComponents() {
        this.jPanel4 = new JPanel();
        this.jPanel5 = new JPanel();
        this.btnOK = new JButton();
        this.btnCancel = new JButton();

        setDefaultCloseOperation(2);
        setTitle("Configure ClustalW Filter");
        GridLayout gridLayout = new GridLayout(0, 1);
        gridLayout.setVgap(5);
        gridLayout.setHgap(5);
        getContentPane().setLayout(gridLayout);

        this.btnOK.setText("OK");
        this.btnOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnOKActionPerformed(evt);
            }

        });
        this.jPanel5.add(this.btnOK);

        this.btnCancel.setText("Cancel");
        this.btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }

        });
        jPanel4.setLayout(new BorderLayout(0, 0));

        panel = new JPanel();
        jPanel4.add(panel, BorderLayout.NORTH);

        btnAddNewRule = new JButton("Add new rule");
        btnAddNewRule.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                ClustalWRule newRule = ClustalWRule.defaultRule();
                addNewRule(newRule);
                pack();
            }
        });

        ButtonGroup conjunctionGroup = new ButtonGroup();
        rdbtnAllMustBe = new JRadioButton("All must be true");
        panel.add(rdbtnAllMustBe);

        rdbtnAtLeastOne = new JRadioButton("At least one must be true");
        panel.add(rdbtnAtLeastOne);
        conjunctionGroup.add(rdbtnAllMustBe);
        conjunctionGroup.add(rdbtnAtLeastOne);
        panel.add(btnAddNewRule);

        panel_1 = new JPanel();
        jPanel4.add(panel_1, BorderLayout.CENTER);
        panel_1.setLayout(new BorderLayout(0, 0));

        rulesPanel = new JPanel();
        panel_1.add(rulesPanel, BorderLayout.NORTH);
        rulesPanel.setLayout(new GridLayout(0, 1, 0, 0));

        ClustalWRuleFragmentUI initialRuleUI = new ClustalWRuleFragmentUI();
        rulesPanel.add(initialRuleUI);

        panel_2 = new JPanel();
        panel_2.setBorder(new EmptyBorder(5, 5, 5, 5));
        panel_1.add(panel_2, BorderLayout.CENTER);
        panel_2.setLayout(new BorderLayout(0, 5));

        lblSequenceData = new JLabel("Sequence Data");
        lblSequenceData.setHorizontalAlignment(SwingConstants.CENTER);
        panel_2.add(lblSequenceData, BorderLayout.NORTH);

        txtSequenceData = new JTextArea();
        txtSequenceData.setRows(8);
        panel_2.add(txtSequenceData, BorderLayout.CENTER);
        this.jPanel5.add(this.btnCancel);

        this.jPanel4.add(this.jPanel5, BorderLayout.SOUTH);

        getContentPane().add(this.jPanel4);

        pack();
    }

    private void addNewRule(ClustalWRule newRule) {
        final ClustalWRuleFragmentUI newUI = new ClustalWRuleFragmentUI();
        rulesPanel.add(newUI);
        ruleFragments.add(newUI);
        newUI.setRule(newRule);
        newUI.getBtnDelete().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                rulesPanel.remove(newUI);
                ruleFragments.remove(newUI);
                pack();
            }
        });
    }

    private void btnOKActionPerformed(ActionEvent evt) {
        this.myConfig.getRules().clear();
        for (ClustalWRuleFragmentUI fragment : ruleFragments) {
            this.myConfig.getRules().add(fragment.getRule());
        }
        myConfig.setSequenceData(txtSequenceData.getText());
        if (rdbtnAtLeastOne.isSelected()) {
            myConfig.setConjunction(ClustalWRuleConjunction.OR);
        } else {
            myConfig.setConjunction(ClustalWRuleConjunction.AND);
        }
        setVisible(false);
    }

    private void btnCancelActionPerformed(ActionEvent evt) {
        setVisible(false);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame();
                ClustalWFilterConfiguration config = new ClustalWFilterConfiguration();
                ClustalWFilterConfiguration newConfig = new ClustalWFilterConfigurationDialog(
                        frame).showDialog(config);
                System.out.println("Old config:\n\n" + config.toString()
                        + "\n\n");
                System.out.println("New config\n\n" + newConfig);
            }
        });
    }

    public ClustalWFilterConfiguration showDialog(
            ClustalWFilterConfiguration initialConfig) {
        this.myConfig = initialConfig;
        if (this.myConfig == null) {
            this.myConfig = new ClustalWFilterConfiguration(
                    new ArrayList<ClustalWRule>(), "",
                    ClustalWRuleConjunction.AND);
        }
        if (myConfig.getRules().isEmpty()) {
            this.myConfig.getRules().add(ClustalWRule.defaultRule());
        }
        rulesPanel.removeAll();
        ruleFragments.clear();
        for (ClustalWRule rule : myConfig.getRules()) {
            addNewRule(rule);
        }
        txtSequenceData.setText(myConfig.getSequenceData());
        if (myConfig.getConjunction() == null) {
            myConfig.setConjunction(ClustalWRuleConjunction.AND);
        }
        switch (myConfig.getConjunction()) {
        case OR:
            rdbtnAtLeastOne.setSelected(true);
            break;
        default:
            rdbtnAllMustBe.setSelected(true);
            break;
        }
        pack();
        setVisible(true);
        return this.myConfig;
    }
}