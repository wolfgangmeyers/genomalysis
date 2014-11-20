package org.genomalysis.clustalOmega;

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
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;


public class ClustalOmegaFilterConfigurationDialog extends JDialog {

    private static final long serialVersionUID = 1L;
    private ClustalOmegaFilterConfiguration myConfig;
    private JButton btnCancel;
    private JButton btnOK;
    private JPanel jPanel4;
    private JPanel jPanel5;
    private JPanel rulesPanel;
    private JPanel panel;
    private JButton btnAddNewRule;

    private List<ClustalOmegaRuleFragmentUI> ruleFragments = new LinkedList<ClustalOmegaRuleFragmentUI>();
    private JPanel panel_1;
    private JPanel panel_2;
    private JLabel lblSequenceData;
    private JTextArea txtSequenceData;
    private JRadioButton rdbtnAllMustBe;
    private JRadioButton rdbtnAtLeastOne;
    private JPopupMenu popupMenu;
    private JMenuItem mntmCut;
    private JMenuItem mntmCopy;
    private JMenuItem mntmPaste;

    public ClustalOmegaFilterConfigurationDialog(Frame parent) {
        super(parent, true);
        initComponents();
    }

    private void initComponents() {
        this.jPanel4 = new JPanel();
        this.jPanel5 = new JPanel();
        this.btnOK = new JButton();
        this.btnCancel = new JButton();

        setDefaultCloseOperation(2);
        setTitle("Configure ClustalOmega Filter");
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
                ClustalOmegaRule newRule = ClustalOmegaRule.defaultRule();
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

        ClustalOmegaRuleFragmentUI initialRuleUI = new ClustalOmegaRuleFragmentUI();
        rulesPanel.add(initialRuleUI);

        panel_2 = new JPanel();
        panel_2.setBorder(new EmptyBorder(5, 5, 5, 5));
        panel_1.add(panel_2, BorderLayout.CENTER);
        panel_2.setLayout(new BorderLayout(0, 5));

        lblSequenceData = new JLabel("Sequence data: a single contiguous sequence (case sensitive)");
        lblSequenceData.setHorizontalAlignment(SwingConstants.CENTER);
        panel_2.add(lblSequenceData, BorderLayout.NORTH);

        txtSequenceData = new JTextArea();
        txtSequenceData.setLineWrap(true);
        txtSequenceData.setRows(8);
        panel_2.add(txtSequenceData, BorderLayout.CENTER);
        
        popupMenu = new JPopupMenu();
        addPopup(txtSequenceData, popupMenu);
        
        mntmCut = new JMenuItem("Cut");
        popupMenu.add(mntmCut);
        this.mntmCut.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                menuViewSequenceCutActionPerformed(evt);
            }
        });
        mntmCopy = new JMenuItem("Copy");
        popupMenu.add(mntmCopy);
        this.mntmCopy.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                menuViewSequenceCopyActionPerformed(evt);
            }
        });
        mntmPaste = new JMenuItem("Paste");
        popupMenu.add(mntmPaste);
        this.mntmPaste.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                menuViewSequencePasteActionPerformed(evt);
            }
        });
        
        this.jPanel5.add(this.btnCancel);

        this.jPanel4.add(this.jPanel5, BorderLayout.SOUTH);

        getContentPane().add(this.jPanel4);

        pack();
    }

    private void addNewRule(ClustalOmegaRule newRule) {
        final ClustalOmegaRuleFragmentUI newUI = new ClustalOmegaRuleFragmentUI();
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
        for (ClustalOmegaRuleFragmentUI fragment : ruleFragments) {
            this.myConfig.getRules().add(fragment.getRule());
        }
        myConfig.setSequenceData(txtSequenceData.getText());
        if (rdbtnAtLeastOne.isSelected()) {
            myConfig.setConjunction(ClustalOmegaRuleConjunction.OR);
        } else {
            myConfig.setConjunction(ClustalOmegaRuleConjunction.AND);
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
                ClustalOmegaFilterConfiguration config = new ClustalOmegaFilterConfiguration();
                ClustalOmegaFilterConfiguration newConfig = new ClustalOmegaFilterConfigurationDialog(
                        frame).showDialog(config);
                System.out.println("Old config:\n\n" + config.toString()
                        + "\n\n");
                System.out.println("New config\n\n" + newConfig);
            }
        });
    }

    public ClustalOmegaFilterConfiguration showDialog(
            ClustalOmegaFilterConfiguration initialConfig) {
        this.myConfig = initialConfig;
        if (this.myConfig == null) {
            this.myConfig = new ClustalOmegaFilterConfiguration(
                    new ArrayList<ClustalOmegaRule>(), "",
                    ClustalOmegaRuleConjunction.AND);
        }
        if (myConfig.getRules().isEmpty()) {
            this.myConfig.getRules().add(ClustalOmegaRule.defaultRule());
        }
        rulesPanel.removeAll();
        ruleFragments.clear();
        for (ClustalOmegaRule rule : myConfig.getRules()) {
            addNewRule(rule);
        }
        txtSequenceData.setText(myConfig.getSequenceData());
        if (myConfig.getConjunction() == null) {
            myConfig.setConjunction(ClustalOmegaRuleConjunction.AND);
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
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
	private void menuViewSequenceCutActionPerformed(ActionEvent evt) {
        this.txtSequenceData.cut();
    }
	private void menuViewSequenceCopyActionPerformed(ActionEvent evt) {
        this.txtSequenceData.copy();
	}
	private void menuViewSequencePasteActionPerformed(ActionEvent evt) {
        this.txtSequenceData.paste();
	}    
}