package org.genomalysis.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.AbstractListModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.genomalysis.control.IObserver;
import org.genomalysis.control.SequenceCacheControl;
import org.genomalysis.control.SequencePagerControl;
import org.genomalysis.plugin.FilePluginManager;
import org.genomalysis.plugin.PluginInstance;
import org.genomalysis.plugin.PluginInstanceManager;
import org.genomalysis.plugin.configuration.ConfigurationException;
import org.genomalysis.plugin.configuration.IPropertyConfigurator;
import org.genomalysis.plugin.configuration.ui.InstancePanel;
import org.genomalysis.proteintools.IProteinDiagnosticsTool;
import org.genomalysis.proteintools.IProteinSequenceFilter;
import org.genomalysis.proteintools.InitializationException;
import org.genomalysis.proteintools.ProteinDiagnosticImageElement;
import org.genomalysis.proteintools.ProteinDiagnosticResult;
import org.genomalysis.proteintools.ProteinDiagnosticTextElement;
import org.genomalysis.proteintools.ProteinSequence;

public class FrmMain extends JFrame
        implements org.genomalysis.plugin.IObserver {

    private SequencePagerControl pagerControl = new SequencePagerControl();
    private JFileChooser fileDlg = new JFileChooser();
    private FilePluginManager pluginManager = new FilePluginManager();
    private PluginInstanceManager<IProteinSequenceFilter> filterInstanceManager = null;
    private PluginInstanceManager<IProteinDiagnosticsTool> diagnosticsInstanceManager = null;
    private FilterDialog filterDialog = new FilterDialog(this, true);
    private InstancePanel diagnosticInstancePanel = null;
    private DiagnosticsDialog diagnosticsDlg = null;
    private SequenceCacheControl sequenceCacheControl = new SequenceCacheControl();
    private int selectedFilterInstanceIndex = -1;
    private JPopupMenu availableFiltersPopupMenu;
    private javax.swing.JButton btnAddFilterInstance;
    private javax.swing.JButton btnAddToCache;
    private javax.swing.JButton btnClearCache;
    private javax.swing.JButton btnConfigureFilterInstance;
    private javax.swing.JButton btnCreateNewCache;
    private javax.swing.JButton btnExecuteFilters;
    private javax.swing.JButton btnFilterInfo;
    private javax.swing.JButton btnLoadCache;
    private javax.swing.JButton btnMoveFilterDown;
    private javax.swing.JButton btnMoveFilterUp;
    private javax.swing.JButton btnRemoveCache;
    private javax.swing.JButton btnRemoveFilterInstance;
    private javax.swing.JButton btnRemoveSequenceFromCache;
    private javax.swing.JButton btnRenameCache;
    private javax.swing.JButton btnRenameFilterInstance;
    private javax.swing.JButton btnRenameSequence;
    private javax.swing.JButton btnRunDiagnostics;
    private javax.swing.JButton btnRunDiagnosticsOnCache;
    private javax.swing.JButton btnSaveCache;
    private javax.swing.JButton btnTestInit;
    private javax.swing.JButton btnViewSequenceFirstPage;
    private javax.swing.JButton btnViewSequenceLastPage;
    private javax.swing.JButton btnViewSequenceNextPage;
    private javax.swing.JButton btnViewSequencePrevPage;
    private JComboBox cbActiveCache;
    private JPopupMenu clipboardMenu;
    private JPopupMenu filterInstancesMenu;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JLabel jLabel8;
    private JMenu jMenu1;
    private JMenu jMenu2;
    private JMenuBar jMenuBar1;
    private JPanel jPanel1;
    private JPanel jPanel10;
    private JPanel jPanel11;
    private JPanel jPanel12;
    private JPanel jPanel13;
    private JPanel jPanel14;
    private JPanel jPanel15;
    private JPanel jPanel16;
    private JPanel jPanel17;
    private JPanel jPanel18;
    private JPanel jPanel19;
    private JPanel jPanel2;
    private JPanel jPanel20;
    private JPanel jPanel21;
    private JPanel jPanel22;
    private JPanel jPanel23;
    private JPanel jPanel3;
    private JPanel jPanel4;
    private JPanel jPanel5;
    private JPanel jPanel6;
    private JPanel jPanel7;
    private JPanel jPanel8;
    private JPanel jPanel9;
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane2;
    private JScrollPane jScrollPane3;
    private JScrollPane jScrollPane4;
    private JScrollPane jScrollPane5;
    private JLabel lblPage;
    private JLabel lblTotalPages;
    private JList lstCache;
    private JList lstCacheSequences;
    private JList lstFilterInstances;
    private JList lstSequenceFilters;
    private JList lstViewSequences;
    private JPanel mainPanel;
    private JMenuItem menuAvailableFiltersAddFilter;
    private JMenuItem menuAvailableFiltersInfo;
    private JMenuItem menuFileExit;
    private JMenuItem menuFileOpenFasta;
    private JMenuItem menuFilterInstanceConfigure;
    private JMenuItem menuFilterInstanceRemove;
    private JMenuItem menuFilterInstanceRename;
    private JMenuItem menuViewSequenceCut;
    private JMenuItem menuViewSequencesAddToCache;
    private JMenuItem menuViewSequencesRunDiagnostics;
    private JMenuItem menuViewSequeneCopy;
    private JPanel pagingPanel;
    private JPanel panelDiagnosticResults;
    private JPanel panelFilterInstances;
    private JPanel panelFilterPlugins;
    private JPanel panelFilterSequences;
    private JPanel panelSequencesCache;
    private JPanel panelViewSequence;
    private JPanel panelViewSequences;
    private JScrollPane spSequenceFilters;
    private JTabbedPane tabPageDiagnosticResults;
    private JTabbedPane tabPageMain;
    private JTextArea txtViewSequence;
    private JPopupMenu viewSequencesPopupMenu;

    public FrmMain() {
        initComponents();
        final FrmMain mainform = this;
        File pluginDir = new File("plugins");
        pluginDir.mkdir();
        this.pluginManager.addObserver(this);
        this.filterInstanceManager = new PluginInstanceManager(IProteinSequenceFilter.class, this.pluginManager);
        this.filterInstanceManager.addObserver(this);
        this.diagnosticsInstanceManager = new PluginInstanceManager(IProteinDiagnosticsTool.class, this.pluginManager);

        this.diagnosticInstancePanel = new InstancePanel(this.diagnosticsInstanceManager, this.pluginManager);
        this.tabPageMain.addTab("Diagnostic Tools", this.diagnosticInstancePanel);

        this.pluginManager.addPluginInterface(IProteinSequenceFilter.class);
        this.pluginManager.addPluginInterface(IProteinDiagnosticsTool.class);
        this.pluginManager.findPlugins();
        this.diagnosticsDlg = new DiagnosticsDialog(this);

        this.sequenceCacheControl.addObserver(new IObserver() {

            public void update() {
                mainform.repaint();
            //FrmMain.access$100(this.this$0);
            }

            public void showError(String errorMsg) {
                mainform.showError(errorMsg);
            }
        });
        populateCacheList();

        this.lstFilterInstances.setTransferHandler(new TransferHandler() {

            public boolean canImport(DataFlavor[] arg1) {
                System.out.println(arg1);
                return true;
            }

            public boolean importData(Transferable arg1) {
                //FrmMain.access$200(this.this$0, null);
                mainform.repaint();
                return true;
            }
        });
    }

    private void populateCacheList() {
        String[] cacheNames = this.sequenceCacheControl.getCacheNames();
        DefaultListModel lm = new DefaultListModel();
        DefaultComboBoxModel cbmodel = new DefaultComboBoxModel();
        String[] arr$ = cacheNames;
        int len$ = arr$.length;
        for (int i$ = 0; i$ < len$; ++i$) {
            String cacheName = arr$[i$];
            lm.add(lm.size(), cacheName);
            cbmodel.addElement(cacheName);
        }
        this.lstCache.setModel(lm);
        this.cbActiveCache.setModel(cbmodel);
        this.cbActiveCache.setSelectedItem(this.sequenceCacheControl.getActiveCacheName());
    }

    private void populateCacheSequences() {
        if (this.lstCache.getSelectedIndex() == -1) {
            this.lstCacheSequences.setModel(new DefaultListModel());
        } else {
            DefaultListModel lm = new DefaultListModel();
            List sequences = this.sequenceCacheControl.getSequences(this.lstCache.getSelectedValue().toString());
            for (Iterator i$ = sequences.iterator(); i$.hasNext();) {
                ProteinSequence sequence = (ProteinSequence) i$.next();
                lm.add(lm.size(), sequence.getName());
            }
            this.lstCacheSequences.setModel(lm);
        }
    }

    public void showError(String errorMessage) {
        JOptionPane.showMessageDialog(this, errorMessage, "Error", 0);
    }

    private void loadFastaFile(File file) throws IOException {
        this.pagerControl.loadFile(file);
        updateIndexLists();
    }

    private void updateIndexLists() throws IOException {
        DefaultListModel model = new DefaultListModel();
        model.clear();
        for (Iterator i$ = this.pagerControl.getCurrentPageItems().iterator(); i$.hasNext();) {
            String string = (String) i$.next();
            model.add(model.size(), string);
        }
        this.lstViewSequences.setModel(model);
        this.btnViewSequenceFirstPage.setEnabled(this.pagerControl.hasPreviousPage());
        this.btnViewSequenceLastPage.setEnabled(this.pagerControl.hasNextPage());
        this.btnViewSequenceNextPage.setEnabled(this.pagerControl.hasNextPage());
        this.btnViewSequencePrevPage.setEnabled(this.pagerControl.hasPreviousPage());
        this.lblPage.setText(String.valueOf(this.pagerControl.getCurrentPage() + 1));
        this.lblTotalPages.setText(String.valueOf(this.pagerControl.getTotalPages()));
    }

    private List<ProteinSequence> getSelectedSequences() {
        List result = new ArrayList();
        int[] arr$ = this.lstViewSequences.getSelectedIndices();
        int len$ = arr$.length;
        for (int i$ = 0; i$ < len$; ++i$) {
            int i = arr$[i$];
            ProteinSequence sequence = this.pagerControl.getItemDetails(i);
            if (sequence != null) {
                result.add(sequence);
            }
        }
        return result;
    }

    private void initComponents() {
        this.viewSequencesPopupMenu = new JPopupMenu();
        this.menuViewSequencesRunDiagnostics = new JMenuItem();
        this.menuViewSequencesAddToCache = new JMenuItem();
        this.availableFiltersPopupMenu = new JPopupMenu();
        this.menuAvailableFiltersAddFilter = new JMenuItem();
        this.menuAvailableFiltersInfo = new JMenuItem();
        this.filterInstancesMenu = new JPopupMenu();
        this.menuFilterInstanceConfigure = new JMenuItem();
        this.menuFilterInstanceRename = new JMenuItem();
        this.menuFilterInstanceRemove = new JMenuItem();
        this.clipboardMenu = new JPopupMenu();
        this.menuViewSequenceCut = new JMenuItem();
        this.menuViewSequeneCopy = new JMenuItem();
        this.mainPanel = new JPanel();
        this.tabPageMain = new JTabbedPane();
        this.panelViewSequences = new JPanel();
        this.pagingPanel = new JPanel();
        this.jScrollPane1 = new JScrollPane();
        this.lstViewSequences = new JList();
        this.jPanel1 = new JPanel();
        this.btnViewSequenceFirstPage = new javax.swing.JButton();
        this.btnViewSequencePrevPage = new javax.swing.JButton();
        this.btnViewSequenceNextPage = new javax.swing.JButton();
        this.btnViewSequenceLastPage = new javax.swing.JButton();
        this.jPanel2 = new JPanel();
        this.jPanel4 = new JPanel();
        this.jLabel1 = new JLabel();
        this.lblPage = new JLabel();
        this.jPanel5 = new JPanel();
        this.jLabel2 = new JLabel();
        this.lblTotalPages = new JLabel();
        this.panelViewSequence = new JPanel();
        this.jScrollPane2 = new JScrollPane();
        this.txtViewSequence = new JTextArea();
        this.jPanel10 = new JPanel();
        this.jPanel11 = new JPanel();
        this.jLabel4 = new JLabel();
        this.jPanel12 = new JPanel();
        this.btnRunDiagnostics = new javax.swing.JButton();
        this.btnAddToCache = new javax.swing.JButton();
        this.panelFilterSequences = new JPanel();
        this.panelFilterPlugins = new JPanel();
        this.spSequenceFilters = new JScrollPane();
        this.lstSequenceFilters = new JList();
        this.jPanel7 = new JPanel();
        this.jLabel3 = new JLabel();
        this.panelFilterInstances = new JPanel();
        this.jPanel9 = new JPanel();
        this.jPanel8 = new JPanel();
        this.jLabel5 = new JLabel();
        this.jPanel3 = new JPanel();
        this.btnAddFilterInstance = new javax.swing.JButton();
        this.btnRemoveFilterInstance = new javax.swing.JButton();
        this.btnConfigureFilterInstance = new javax.swing.JButton();
        this.btnMoveFilterUp = new javax.swing.JButton();
        this.btnMoveFilterDown = new javax.swing.JButton();
        this.btnRenameFilterInstance = new javax.swing.JButton();
        this.btnFilterInfo = new javax.swing.JButton();
        this.btnTestInit = new javax.swing.JButton();
        this.btnExecuteFilters = new javax.swing.JButton();
        this.jScrollPane3 = new JScrollPane();
        this.lstFilterInstances = new JList();
        this.panelSequencesCache = new JPanel();
        this.jPanel13 = new JPanel();
        this.jPanel16 = new JPanel();
        this.jPanel17 = new JPanel();
        this.jLabel7 = new JLabel();
        this.jScrollPane5 = new JScrollPane();
        this.lstCache = new JList();
        this.jPanel18 = new JPanel();
        this.jPanel6 = new JPanel();
        this.jPanel19 = new JPanel();
        this.btnLoadCache = new javax.swing.JButton();
        this.btnSaveCache = new javax.swing.JButton();
        this.btnClearCache = new javax.swing.JButton();
        this.jPanel20 = new JPanel();
        this.btnRemoveSequenceFromCache = new javax.swing.JButton();
        this.btnRenameSequence = new javax.swing.JButton();
        this.jPanel21 = new JPanel();
        this.btnRemoveCache = new javax.swing.JButton();
        this.btnRenameCache = new javax.swing.JButton();
        this.jPanel22 = new JPanel();
        this.jLabel8 = new JLabel();
        this.cbActiveCache = new JComboBox();
        this.btnCreateNewCache = new javax.swing.JButton();
        this.jPanel23 = new JPanel();
        this.btnRunDiagnosticsOnCache = new javax.swing.JButton();
        this.jPanel14 = new JPanel();
        this.jPanel15 = new JPanel();
        this.jLabel6 = new JLabel();
        this.jScrollPane4 = new JScrollPane();
        this.lstCacheSequences = new JList();
        this.panelDiagnosticResults = new JPanel();
        this.tabPageDiagnosticResults = new JTabbedPane();
        this.jMenuBar1 = new JMenuBar();
        this.jMenu1 = new JMenu();
        this.menuFileOpenFasta = new JMenuItem();
        this.menuFileExit = new JMenuItem();
        this.jMenu2 = new JMenu();

        this.menuViewSequencesRunDiagnostics.setText("Run Diagnostics");
        this.menuViewSequencesRunDiagnostics.setEnabled(false);
        this.menuViewSequencesRunDiagnostics.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                menuViewSequencesRunDiagnosticsActionPerformed(evt);
            }
        });
        this.viewSequencesPopupMenu.add(this.menuViewSequencesRunDiagnostics);

        this.menuViewSequencesAddToCache.setText("Add to Cache");
        this.menuViewSequencesAddToCache.setEnabled(false);
        this.menuViewSequencesAddToCache.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                menuViewSequencesAddToCacheActionPerformed(evt);
            }
        });
        this.viewSequencesPopupMenu.add(this.menuViewSequencesAddToCache);

        this.menuAvailableFiltersAddFilter.setText("Add Filter");
        this.menuAvailableFiltersAddFilter.setEnabled(false);
        this.menuAvailableFiltersAddFilter.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                menuAvailableFiltersAddFilterActionPerformed(evt);
            }
        });
        this.availableFiltersPopupMenu.add(this.menuAvailableFiltersAddFilter);

        this.menuAvailableFiltersInfo.setText("Filter Info...");
        this.menuAvailableFiltersInfo.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                menuAvailableFiltersInfoActionPerformed(evt);
            }
        });
        this.availableFiltersPopupMenu.add(this.menuAvailableFiltersInfo);

        this.menuFilterInstanceConfigure.setText("Configure");
        this.menuFilterInstanceConfigure.setEnabled(false);
        this.menuFilterInstanceConfigure.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                menuFilterInstanceConfigureActionPerformed(evt);
            }
        });
        this.filterInstancesMenu.add(this.menuFilterInstanceConfigure);

        this.menuFilterInstanceRename.setText("Rename");
        this.menuFilterInstanceRename.setEnabled(false);
        this.menuFilterInstanceRename.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                menuFilterInstanceRenameActionPerformed(evt);
            }
        });
        this.filterInstancesMenu.add(this.menuFilterInstanceRename);

        this.menuFilterInstanceRemove.setText("Remove");
        this.menuFilterInstanceRemove.setEnabled(false);
        this.menuFilterInstanceRemove.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                menuFilterInstanceRemoveActionPerformed(evt);
            }
        });
        ;
        this.filterInstancesMenu.add(this.menuFilterInstanceRemove);

        this.menuViewSequenceCut.setText("Cut");
        this.menuViewSequenceCut.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                menuViewSequenceCutActionPerformed(evt);
            }
        });
        this.clipboardMenu.add(this.menuViewSequenceCut);

        this.menuViewSequeneCopy.setText("Copy");
        this.menuViewSequeneCopy.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                menuViewSequeneCopyActionPerformed(evt);
            }
        });
        this.clipboardMenu.add(this.menuViewSequeneCopy);

        setDefaultCloseOperation(3);
        setTitle("Genomalysis - DNA and Protein Sequence Analysis Software");

        this.mainPanel.setLayout(new BorderLayout());

        this.panelViewSequences.setLayout(new BorderLayout(20, 10));

        this.pagingPanel.setLayout(new BorderLayout(10, 10));

        this.lstViewSequences.setComponentPopupMenu(this.viewSequencesPopupMenu);
        this.lstViewSequences.setModel(new AbstractListModel() {

            String[] strings = new String[]{"Open fasta file to view sequences here"};

            public int getSize() {
                return this.strings.length;
            }

            public Object getElementAt(int i) {
                return this.strings[i];
            }
        });
        this.lstViewSequences.addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent evt) {
                lstViewSequencesValueChanged(evt);
            }
        });
        this.jScrollPane1.setViewportView(this.lstViewSequences);

        this.pagingPanel.add(this.jScrollPane1, "Center");

        this.btnViewSequenceFirstPage.setText("First");
        this.btnViewSequenceFirstPage.setEnabled(false);
        this.btnViewSequenceFirstPage.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                btnViewSequenceFirstPageActionPerformed(evt);
            }
        });
        this.jPanel1.add(this.btnViewSequenceFirstPage);

        this.btnViewSequencePrevPage.setText("Previous");
        this.btnViewSequencePrevPage.setEnabled(false);
        this.btnViewSequencePrevPage.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                btnViewSequencePrevPageActionPerformed(evt);
            }
        });
        this.jPanel1.add(this.btnViewSequencePrevPage);

        this.btnViewSequenceNextPage.setText("Next");
        this.btnViewSequenceNextPage.setEnabled(false);
        this.btnViewSequenceNextPage.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                btnViewSequenceNextPageActionPerformed(evt);
            }
        });
        this.jPanel1.add(this.btnViewSequenceNextPage);

        this.btnViewSequenceLastPage.setText("Last");
        this.btnViewSequenceLastPage.setEnabled(false);
        this.btnViewSequenceLastPage.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                btnViewSequenceLastPageActionPerformed(evt);
            }
        });
        this.jPanel1.add(this.btnViewSequenceLastPage);

        this.pagingPanel.add(this.jPanel1, "North");

        this.jPanel2.setLayout(new BorderLayout());

        this.jLabel1.setText("Page:");
        this.jPanel4.add(this.jLabel1);

        this.lblPage.setText("0");
        this.jPanel4.add(this.lblPage);

        this.jPanel2.add(this.jPanel4, "West");

        this.jLabel2.setText("Total Pages:");
        this.jPanel5.add(this.jLabel2);

        this.lblTotalPages.setText("0");
        this.jPanel5.add(this.lblTotalPages);

        this.jPanel2.add(this.jPanel5, "East");

        this.pagingPanel.add(this.jPanel2, "Last");

        this.panelViewSequences.add(this.pagingPanel, "West");

        this.panelViewSequence.setLayout(new BorderLayout(0, 20));

        this.txtViewSequence.setColumns(20);
        this.txtViewSequence.setComponentPopupMenu(this.clipboardMenu);
        this.txtViewSequence.setFont(new Font("Lucida Console", 0, 12));
        this.txtViewSequence.setRows(5);
        this.jScrollPane2.setViewportView(this.txtViewSequence);

        this.panelViewSequence.add(this.jScrollPane2, "Center");

        this.jPanel10.setLayout(new BorderLayout());

        this.jLabel4.setHorizontalAlignment(0);
        this.jLabel4.setText("Sequence Data");
        this.jPanel11.add(this.jLabel4);

        this.jPanel10.add(this.jPanel11, "North");

        this.jPanel12.setLayout(new GridLayout(1, 0, 10, 10));

        this.btnRunDiagnostics.setText("Run Diagnostics...");
        this.btnRunDiagnostics.setEnabled(false);
        this.btnRunDiagnostics.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                btnRunDiagnosticsActionPerformed(evt);
            }
        });
        this.jPanel12.add(this.btnRunDiagnostics);

        this.btnAddToCache.setText("Add to Cache");
        this.btnAddToCache.setEnabled(false);
        this.btnAddToCache.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                btnAddToCacheActionPerformed(evt);
            }
        });
        this.jPanel12.add(this.btnAddToCache);

        this.jPanel10.add(this.jPanel12, "Center");

        this.panelViewSequence.add(this.jPanel10, "North");

        this.panelViewSequences.add(this.panelViewSequence, "Center");

        this.tabPageMain.addTab("View Sequences", this.panelViewSequences);

        this.panelFilterSequences.setLayout(new BorderLayout(20, 0));

        this.panelFilterPlugins.setMinimumSize(new Dimension(150, 46));
        this.panelFilterPlugins.setPreferredSize(new Dimension(300, 100));
        this.panelFilterPlugins.setLayout(new BorderLayout());

        this.lstSequenceFilters.setComponentPopupMenu(this.availableFiltersPopupMenu);
        this.lstSequenceFilters.setSelectionMode(0);
        this.lstSequenceFilters.setDragEnabled(true);
        this.lstSequenceFilters.addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent evt) {
                lstSequenceFiltersValueChanged(evt);
            }
        });
        this.lstSequenceFilters.addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent evt) {
                lstSequenceFiltersMouseClicked(evt);
            }
        });
        this.spSequenceFilters.setViewportView(this.lstSequenceFilters);

        this.panelFilterPlugins.add(this.spSequenceFilters, "Center");

        this.jLabel3.setText("Available Filters:");
        this.jPanel7.add(this.jLabel3);

        this.panelFilterPlugins.add(this.jPanel7, "North");

        this.panelFilterSequences.add(this.panelFilterPlugins, "West");

        this.panelFilterInstances.setLayout(new BorderLayout(0, 10));

        this.jPanel9.setLayout(new BorderLayout());

        this.jLabel5.setText("Filter Instances:");
        this.jPanel8.add(this.jLabel5);

        this.jPanel9.add(this.jPanel8, "North");

        this.jPanel3.setLayout(new GridLayout(3, 3, 5, 5));

        this.btnAddFilterInstance.setText("Add Filter");
        this.btnAddFilterInstance.setEnabled(false);
        this.btnAddFilterInstance.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                btnAddFilterInstanceActionPerformed(evt);
            }
        });
        this.jPanel3.add(this.btnAddFilterInstance);

        this.btnRemoveFilterInstance.setText("Remove Filter");
        this.btnRemoveFilterInstance.setEnabled(false);
        this.btnRemoveFilterInstance.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                btnRemoveFilterInstanceActionPerformed(evt);
            }
        });
        this.jPanel3.add(this.btnRemoveFilterInstance);

        this.btnConfigureFilterInstance.setText("Configure...");
        this.btnConfigureFilterInstance.setEnabled(false);
        this.btnConfigureFilterInstance.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                btnConfigureFilterInstanceActionPerformed(evt);
            }
        });
        this.jPanel3.add(this.btnConfigureFilterInstance);

        this.btnMoveFilterUp.setText("Move Up /\\");
        this.btnMoveFilterUp.setEnabled(false);
        this.btnMoveFilterUp.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                btnMoveFilterUpActionPerformed(evt);
            }
        });
        this.jPanel3.add(this.btnMoveFilterUp);

        this.btnMoveFilterDown.setText("Move Down \\/");
        this.btnMoveFilterDown.setEnabled(false);
        this.btnMoveFilterDown.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                btnMoveFilterDownActionPerformed(evt);
            }
        });
        this.jPanel3.add(this.btnMoveFilterDown);

        this.btnRenameFilterInstance.setText("Rename");
        this.btnRenameFilterInstance.setEnabled(false);
        this.btnRenameFilterInstance.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                btnRenameFilterInstanceActionPerformed(evt);
            }
        });
        this.jPanel3.add(this.btnRenameFilterInstance);

        this.btnFilterInfo.setText("Filter Info...");
        this.btnFilterInfo.setEnabled(false);
        this.btnFilterInfo.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                btnFilterInfoActionPerformed(evt);
            }
        });
        this.jPanel3.add(this.btnFilterInfo);

        this.btnTestInit.setText("Test Initialize...");
        this.btnTestInit.setEnabled(false);
        this.btnTestInit.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                btnTestInitActionPerformed(evt);
            }
        });
        this.jPanel3.add(this.btnTestInit);

        this.btnExecuteFilters.setText("Execute Filters...");
        this.btnExecuteFilters.setEnabled(false);
        this.btnExecuteFilters.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                btnExecuteFiltersActionPerformed(evt);
            }
        });
        this.jPanel3.add(this.btnExecuteFilters);

        this.jPanel9.add(this.jPanel3, "Center");

        this.panelFilterInstances.add(this.jPanel9, "North");

        this.lstFilterInstances.setSelectionMode(0);
        this.lstFilterInstances.setDragEnabled(true);
        this.lstFilterInstances.addMouseMotionListener(new MouseMotionAdapter() {

            @Override
            public void mouseDragged(MouseEvent evt) {
                lstFilterInstancesMouseDragged(evt);
            }
        });
        this.lstFilterInstances.addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent evt) {
                lstFilterInstancesValueChanged(evt);
            }
        });
        this.lstFilterInstances.addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent evt) {
                lstFilterInstancesMouseClicked(evt);
            }

            public void mousePressed(MouseEvent evt) {
                lstFilterInstancesMousePressed(evt);
            }
        });
        this.jScrollPane3.setViewportView(this.lstFilterInstances);

        this.panelFilterInstances.add(this.jScrollPane3, "Center");

        this.panelFilterSequences.add(this.panelFilterInstances, "Center");

        this.tabPageMain.addTab("Filter Sequences", this.panelFilterSequences);

        this.panelSequencesCache.setLayout(new GridLayout(1, 0, 20, 0));

        this.jPanel13.setLayout(new GridLayout(2, 1, 0, 20));

        this.jPanel16.setLayout(new BorderLayout());

        this.jLabel7.setText("Sequence Cache:");
        this.jPanel17.add(this.jLabel7);

        this.jPanel16.add(this.jPanel17, "North");

        this.lstCache.setSelectionMode(0);
        this.lstCache.addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent evt) {
                lstCacheValueChanged(evt);
            }
        });
        this.jScrollPane5.setViewportView(this.lstCache);

        this.jPanel16.add(this.jScrollPane5, "Center");

        this.jPanel13.add(this.jPanel16);

        this.jPanel18.setLayout(new BorderLayout());

        this.jPanel6.setLayout(new GridLayout(5, 0));

        this.btnLoadCache.setText("Load...");
        this.btnLoadCache.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                btnLoadCacheActionPerformed(evt);
            }
        });
        this.jPanel19.add(this.btnLoadCache);

        this.btnSaveCache.setText("Save...");
        this.btnSaveCache.setEnabled(false);
        this.btnSaveCache.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                btnSaveCacheActionPerformed(evt);
            }
        });
        this.jPanel19.add(this.btnSaveCache);

        this.btnClearCache.setText("Clear All");
        this.btnClearCache.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                btnClearCacheActionPerformed(evt);
            }
        });
        this.jPanel19.add(this.btnClearCache);

        this.jPanel6.add(this.jPanel19);

        this.btnRemoveSequenceFromCache.setText("Remove Sequence");
        this.btnRemoveSequenceFromCache.setEnabled(false);
        this.btnRemoveSequenceFromCache.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                btnRemoveSequenceFromCacheActionPerformed(evt);
            }
        });
        this.jPanel20.add(this.btnRemoveSequenceFromCache);

        this.btnRenameSequence.setText("Rename Sequence");
        this.btnRenameSequence.setEnabled(false);
        this.btnRenameSequence.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                btnRenameSequenceActionPerformed(evt);
            }
        });
        this.jPanel20.add(this.btnRenameSequence);

        this.jPanel6.add(this.jPanel20);

        this.btnRemoveCache.setText("Remove Cache");
        this.btnRemoveCache.setEnabled(false);
        this.btnRemoveCache.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                btnRemoveCacheActionPerformed(evt);
            }
        });
        this.jPanel21.add(this.btnRemoveCache);

        this.btnRenameCache.setText("Rename Cache");
        this.btnRenameCache.setEnabled(false);
        this.btnRenameCache.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                btnRenameCacheActionPerformed(evt);
            }
        });
        this.jPanel21.add(this.btnRenameCache);

        this.jPanel6.add(this.jPanel21);

        this.jLabel8.setText("Active Cache:");
        this.jPanel22.add(this.jLabel8);

        this.cbActiveCache.setModel(new DefaultComboBoxModel(new String[]{"default"}));
        this.cbActiveCache.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                cbActiveCacheActionPerformed(evt);
            }
        });
        this.jPanel22.add(this.cbActiveCache);

        this.btnCreateNewCache.setText("Create New Cache");
        this.btnCreateNewCache.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                btnCreateNewCacheActionPerformed(evt);
            }
        });
        this.jPanel22.add(this.btnCreateNewCache);

        this.jPanel6.add(this.jPanel22);

        this.btnRunDiagnosticsOnCache.setText("Run Diagnostics");
        this.btnRunDiagnosticsOnCache.setEnabled(false);
        this.btnRunDiagnosticsOnCache.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                btnRunDiagnosticsOnCacheActionPerformed(evt);
            }
        });
        this.jPanel23.add(this.btnRunDiagnosticsOnCache);

        this.jPanel6.add(this.jPanel23);

        this.jPanel18.add(this.jPanel6, "North");

        this.jPanel13.add(this.jPanel18);

        this.panelSequencesCache.add(this.jPanel13);

        this.jPanel14.setLayout(new BorderLayout());

        this.jLabel6.setText("Sequences:");
        this.jPanel15.add(this.jLabel6);

        this.jPanel14.add(this.jPanel15, "North");

        this.lstCacheSequences.setSelectionMode(0);
        this.lstCacheSequences.addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent evt) {
                lstCacheSequencesValueChanged(evt);
            }
        });
        this.jScrollPane4.setViewportView(this.lstCacheSequences);

        this.jPanel14.add(this.jScrollPane4, "Center");

        this.panelSequencesCache.add(this.jPanel14);

        this.tabPageMain.addTab("Sequences Cache", this.panelSequencesCache);

        this.panelDiagnosticResults.setLayout(new BorderLayout());
        this.panelDiagnosticResults.add(this.tabPageDiagnosticResults, "Center");

        this.tabPageMain.addTab("Diagnostic Results", this.panelDiagnosticResults);

        this.mainPanel.add(this.tabPageMain, "Center");

        getContentPane().add(this.mainPanel, "Center");

        this.jMenu1.setText("File");

        this.menuFileOpenFasta.setText("Open Fasta File");
        this.menuFileOpenFasta.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                menuFileOpenFastaActionPerformed(evt);
            }
        });
        this.jMenu1.add(this.menuFileOpenFasta);

        this.menuFileExit.setText("Exit");
        this.menuFileExit.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                menuFileExitActionPerformed(evt);
            }
        });
        this.jMenu1.add(this.menuFileExit);

        this.jMenuBar1.add(this.jMenu1);

        this.jMenu2.setText("Edit");
        this.jMenuBar1.add(this.jMenu2);

        setJMenuBar(this.jMenuBar1);

        pack();
    }

    private void menuFileOpenFastaActionPerformed(ActionEvent evt) {
        if (this.fileDlg.showDialog(this, "Open Fasta File") == 0) {
            try {
                File fastaFile = this.fileDlg.getSelectedFile();
                loadFastaFile(fastaFile);
            } catch (IOException ex) {
                Logger.getLogger(FrmMain.class.getName()).log(Level.SEVERE, null, ex);
                showError("Could not load file: \n" + ex.getMessage());
            }
        }
    }

    private void btnViewSequencePrevPageActionPerformed(ActionEvent evt) {
        if (this.pagerControl.previousPage()) {
            try {
                updateIndexLists();
            } catch (IOException ex) {
                Logger.getLogger(FrmMain.class.getName()).log(Level.SEVERE, null, ex);
                showError(ex.getMessage());
            }
        }
    }

    private void btnViewSequenceNextPageActionPerformed(ActionEvent evt) {
        if (this.pagerControl.nextPage()) {
            try {
                updateIndexLists();
            } catch (IOException ex) {
                Logger.getLogger(FrmMain.class.getName()).log(Level.SEVERE, null, ex);
                showError(ex.getMessage());
            }
        }
    }

    private void btnViewSequenceFirstPageActionPerformed(ActionEvent evt) {
        if (this.pagerControl.firstPage()) {
            try {
                updateIndexLists();
            } catch (IOException ex) {
                Logger.getLogger(FrmMain.class.getName()).log(Level.SEVERE, null, ex);
                showError(ex.getMessage());
            }
        }
    }

    private void btnViewSequenceLastPageActionPerformed(ActionEvent evt) {
        if (this.pagerControl.lastPage()) {
            try {
                updateIndexLists();
            } catch (IOException ex) {
                Logger.getLogger(FrmMain.class.getName()).log(Level.SEVERE, null, ex);
                showError(ex.getMessage());
            }
        }
    }

    private void lstViewSequencesValueChanged(ListSelectionEvent evt) {
        StringBuffer buffer = new StringBuffer();
        for (Iterator i$ = getSelectedSequences().iterator(); i$.hasNext();) {
            ProteinSequence sequence = (ProteinSequence) i$.next();
            buffer.append(sequence.toString());
        }
        this.txtViewSequence.setText(buffer.toString());

        boolean enabled = this.lstViewSequences.getSelectedIndices().length > 0;
        this.btnRunDiagnostics.setEnabled(enabled);
        this.btnAddToCache.setEnabled(enabled);
        this.menuViewSequencesAddToCache.setEnabled(enabled);
        this.menuViewSequencesRunDiagnostics.setEnabled(enabled);
    }

    private void lstSequenceFiltersValueChanged(ListSelectionEvent evt) {
        boolean enabled = this.lstSequenceFilters.getSelectedIndex() != -1;
        this.btnAddFilterInstance.setEnabled(enabled);
        this.menuAvailableFiltersAddFilter.setEnabled(enabled);
        this.btnFilterInfo.setEnabled(enabled);
    }

    private void lstFilterInstancesValueChanged(ListSelectionEvent evt) {
        boolean enabled = this.lstFilterInstances.getSelectedIndex() != -1;
        this.btnRemoveFilterInstance.setEnabled(enabled);
        this.btnConfigureFilterInstance.setEnabled(enabled);
        this.btnMoveFilterDown.setEnabled(enabled);
        this.btnMoveFilterUp.setEnabled(enabled);
        this.btnRenameFilterInstance.setEnabled(enabled);
        this.btnTestInit.setEnabled(enabled);
        this.menuFilterInstanceConfigure.setEnabled(enabled);
        this.menuFilterInstanceRemove.setEnabled(enabled);
        this.menuFilterInstanceRename.setEnabled(enabled);
    }

    private void btnAddFilterInstanceActionPerformed(ActionEvent evt) {
        int selectedIndex;
        try {
            selectedIndex = this.lstSequenceFilters.getSelectedIndex();

            this.filterInstanceManager.addPluginInstance(selectedIndex);
            this.lstSequenceFilters.setSelectedIndex(selectedIndex);
            this.btnExecuteFilters.setEnabled(this.lstFilterInstances.getModel().getSize() > 0);
        } catch (ConfigurationException ex) {
            Logger.getLogger(FrmMain.class.getName()).log(Level.SEVERE, null, ex);
            showError(ex.getMessage());
        }
    }

    private void btnRemoveFilterInstanceActionPerformed(ActionEvent evt) {
        this.filterInstanceManager.removePluginInstance((PluginInstance) this.lstFilterInstances.getSelectedValue());
        if (this.lstFilterInstances.getModel().getSize() > 0) {
            this.lstFilterInstances.setSelectedIndex(0);
        }
        this.btnExecuteFilters.setEnabled(this.lstFilterInstances.getModel().getSize() > 0);
    }

    private void btnMoveFilterUpActionPerformed(ActionEvent evt) {
        int selectedIndex = this.lstFilterInstances.getSelectedIndex();
        PluginInstance instance = (PluginInstance) this.lstFilterInstances.getSelectedValue();

        if (this.filterInstanceManager.moveInstanceUp(instance)) {
            --selectedIndex;
        }
        this.lstFilterInstances.setSelectedIndex(selectedIndex);
    }

    private void btnMoveFilterDownActionPerformed(ActionEvent evt) {
        int selectedIndex = this.lstFilterInstances.getSelectedIndex();
        PluginInstance instance = (PluginInstance) this.lstFilterInstances.getSelectedValue();

        if (this.filterInstanceManager.moveInstanceDown(instance)) {
            ++selectedIndex;
        }
        this.lstFilterInstances.setSelectedIndex(selectedIndex);
    }

    private void btnFilterInfoActionPerformed(ActionEvent evt) {
        Class filterClass = this.filterInstanceManager.getPluginClass(this.lstSequenceFilters.getSelectedIndex());
        String documentation = this.pluginManager.getDocumentation(filterClass);
        DocViewer.showDocumentation(this.panelFilterSequences, documentation, filterClass.getSimpleName() + " Documentation");
    }

    private void btnRenameFilterInstanceActionPerformed(ActionEvent evt) {
        PluginInstance instance = (PluginInstance) this.lstFilterInstances.getSelectedValue();

        String name = JOptionPane.showInputDialog("Enter a new name for this filter", instance.getName());
        if (name != null) {
            instance.setName(name);
            this.lstFilterInstances.repaint();
        }
    }

    private void btnConfigureFilterInstanceActionPerformed(ActionEvent evt) {
        PluginInstance instance;
        try {
            instance = (PluginInstance) this.lstFilterInstances.getSelectedValue();
            IPropertyConfigurator configurator = this.pluginManager.getConfigurator(instance.getPluginInstance());
            configurator.showDialog(this.panelFilterInstances, instance.getPluginInstance());
        } catch (ConfigurationException ex) {
            Logger.getLogger(FrmMain.class.getName()).log(Level.SEVERE, null, ex);
            showError(ex.getMessage());
        }
    }

    private void menuFileExitActionPerformed(ActionEvent evt) {
        setVisible(false);
        System.exit(0);
    }

    private void btnExecuteFiltersActionPerformed(ActionEvent evt) {
        List filterInstances = new ArrayList();
        Iterator iterator = this.filterInstanceManager.getPluginInstances();
        while (iterator.hasNext()) {
            filterInstances.add(iterator.next());
        }
        this.filterDialog.showDialog(filterInstances);
    }

    private void btnTestInitActionPerformed(ActionEvent evt) {
        PluginInstance instance = (PluginInstance) this.lstFilterInstances.getSelectedValue();
        try {
            ((IProteinSequenceFilter) instance.getPluginInstance()).initialize();
            JOptionPane.showMessageDialog(this.mainPanel, "Filter passed initialization", "Success", 1);
        } catch (InitializationException ex) {
            String[] arr$ = ex.getReasons();
            int len$ = arr$.length;
            int i$ = 0;
            while (true) {
                if (i$ >= len$) {
                    return;
                }
                String reason = arr$[i$];
                showError(reason);

                ++i$;
            }
        }
    }

    private void btnRunDiagnosticsActionPerformed(ActionEvent evt) {
        List sequences = new ArrayList();
        int[] arr$ = this.lstViewSequences.getSelectedIndices();
        int len$ = arr$.length;
        for (int i$ = 0; i$ < len$; ++i$) {
            int i = arr$[i$];
            sequences.add(this.pagerControl.getItemDetails(i));
        }
        if (sequences.size() > 0) {
            runDiagnostics(sequences);
        }
    }

    private void btnLoadCacheActionPerformed(ActionEvent evt) {
        if (this.fileDlg.showDialog(this.mainPanel, "Select a fasta file to load") == 0) {
            try {
                this.sequenceCacheControl.readFromFile(this.fileDlg.getSelectedFile().getName(), this.fileDlg.getSelectedFile());
            } catch (IOException ex) {
                showError("Problem loading sequences into cache:\n" + ex.getMessage());
                Logger.getLogger(FrmMain.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void btnClearCacheActionPerformed(ActionEvent evt) {
        this.sequenceCacheControl.clearAll();
    }

    private void btnRemoveCacheActionPerformed(ActionEvent evt) {
        if (this.lstCache.getSelectedIndex() != -1) {
            String name = this.lstCache.getSelectedValue().toString();
            this.sequenceCacheControl.removeFromCache(name);
        }
    }

    private void btnAddToCacheActionPerformed(ActionEvent evt) {
        List sequences = this.sequenceCacheControl.getSequences(this.sequenceCacheControl.getActiveCacheName());

        int[] arr$ = this.lstViewSequences.getSelectedIndices();
        int len$ = arr$.length;
        for (int i$ = 0; i$ < len$; ++i$) {
            int i = arr$[i$];
            ProteinSequence sequence = this.pagerControl.getItemDetails(i);
            System.out.println("Add to cache: " + sequence.getName());
            if (!(sequences.contains(sequence))) {
                sequences.add(sequence);
            }
        }

        populateCacheSequences();
    }

    private void lstCacheValueChanged(ListSelectionEvent evt) {
        populateCacheSequences();

        boolean enabled = this.lstCache.getSelectedIndex() != -1;
        this.btnSaveCache.setEnabled(enabled);
        this.btnRemoveCache.setEnabled(enabled);
        this.btnRenameCache.setEnabled(enabled);

        this.btnRunDiagnosticsOnCache.setEnabled((enabled) && (this.sequenceCacheControl.getSequences(this.lstCache.getSelectedValue().toString()).size() > 0));
    }

    private void btnSaveCacheActionPerformed(ActionEvent evt) {
        String name = this.lstCache.getSelectedValue().toString();
        String key = name;
        if (!(name.endsWith(".fsa"))) {
            name = name + ".fsa";
        }
        File saveFile = new File(name);
        this.fileDlg.setSelectedFile(saveFile);
        if (this.fileDlg.showSaveDialog(this.mainPanel) == 0) {
            saveFile = this.fileDlg.getSelectedFile();
            try {
                saveFile.createNewFile();
                this.sequenceCacheControl.saveToFile(key, saveFile);
            } catch (IOException ex) {
                ex.printStackTrace();
                showError("Problem saving cache:\n" + ex.getMessage());
            }
        }
    }

    private void btnRemoveSequenceFromCacheActionPerformed(ActionEvent evt) {
        List sequences = this.sequenceCacheControl.getSequences(this.lstCache.getSelectedValue().toString());

        int index = this.lstCacheSequences.getSelectedIndex();
        sequences.remove(index);
        populateCacheSequences();
        this.lstCacheSequences.setSelectedIndex(Math.min(this.lstCacheSequences.getModel().getSize() - 1, index));
    }

    private void lstCacheSequencesValueChanged(ListSelectionEvent evt) {
        boolean enabled = this.lstCacheSequences.getSelectedIndex() != -1;
        this.btnRemoveSequenceFromCache.setEnabled(enabled);
        this.btnRenameSequence.setEnabled(enabled);
    }

    private void btnCreateNewCacheActionPerformed(ActionEvent evt) {
        String name = JOptionPane.showInputDialog("Enter name for new cache");
        if ((name != null) && (!(name.equals("")))) {
            try {
                this.sequenceCacheControl.createNewCache(name);
            } catch (Exception ex) {
                Logger.getLogger(FrmMain.class.getName()).log(Level.SEVERE, null, ex);
                showError("Error creating new cache:\n" + ex.getMessage());
            }
        }
    }

    private void btnRenameCacheActionPerformed(ActionEvent evt) {
        String name = JOptionPane.showInputDialog("Enter a new name for cache");
        if ((name != null) && (!(name.equals("")))) {
            try {
                String oldname = this.lstCache.getSelectedValue().toString();
                this.sequenceCacheControl.renameCache(oldname, name);
            } catch (Exception ex) {
                Logger.getLogger(FrmMain.class.getName()).log(Level.SEVERE, null, ex);
                showError("Problem renaming cache:\n" + ex.getMessage());
            }
        }
    }

    private void cbActiveCacheActionPerformed(ActionEvent evt) {
        this.sequenceCacheControl.setActiveCacheName(this.cbActiveCache.getSelectedItem().toString());
    }

    private void btnRenameSequenceActionPerformed(ActionEvent evt) {
        List sequences = this.sequenceCacheControl.getSequences(this.lstCache.getSelectedValue().toString());

        ProteinSequence sequence = (ProteinSequence) sequences.get(this.lstCacheSequences.getSelectedIndex());
        String header = sequence.getHeader().substring(1);
        String newHeader = JOptionPane.showInputDialog("Enter new name for sequence", header);

        if ((newHeader != null) && (newHeader.length() > 0)) {
            sequence.setHeader(">" + newHeader);
            populateCacheSequences();
        }
    }

    private void btnRunDiagnosticsOnCacheActionPerformed(ActionEvent evt) {
        List sequences = this.sequenceCacheControl.getSequences(this.lstCache.getSelectedValue().toString());

        runDiagnostics(sequences);
    }

    private void menuViewSequencesRunDiagnosticsActionPerformed(ActionEvent evt) {
        btnRunDiagnosticsActionPerformed(evt);
    }

    private void menuViewSequencesAddToCacheActionPerformed(ActionEvent evt) {
        btnAddToCacheActionPerformed(evt);
    }

    private void menuAvailableFiltersAddFilterActionPerformed(ActionEvent evt) {
        btnAddFilterInstanceActionPerformed(evt);
    }

    private void menuAvailableFiltersInfoActionPerformed(ActionEvent evt) {
        btnFilterInfoActionPerformed(evt);
    }

    private void lstSequenceFiltersMouseClicked(MouseEvent evt) {
        if ((evt.getClickCount() == 2) &&
                (this.lstSequenceFilters.getSelectedIndex() > -1)) {
            btnAddFilterInstanceActionPerformed(null);
        }
    }

    private void lstFilterInstancesMouseClicked(MouseEvent evt) {
        if ((evt.getClickCount() == 2) &&
                (this.lstFilterInstances.getSelectedIndex() > -1)) {
            btnConfigureFilterInstanceActionPerformed(null);
        }
    }

    private void menuFilterInstanceConfigureActionPerformed(ActionEvent evt) {
        btnConfigureFilterInstanceActionPerformed(evt);
    }

    private void menuFilterInstanceRenameActionPerformed(ActionEvent evt) {
        btnRenameFilterInstanceActionPerformed(evt);
    }

    private void menuFilterInstanceRemoveActionPerformed(ActionEvent evt) {
        btnRemoveFilterInstanceActionPerformed(evt);
    }

    private void lstFilterInstancesMousePressed(MouseEvent evt) {
        this.selectedFilterInstanceIndex = this.lstFilterInstances.locationToIndex(evt.getPoint());
    }

    private void lstFilterInstancesMouseDragged(MouseEvent evt) {
        int index = this.lstFilterInstances.locationToIndex(evt.getPoint());
        if (index != this.selectedFilterInstanceIndex) {
            PluginInstance instance = (PluginInstance) this.lstFilterInstances.getModel().getElementAt(this.selectedFilterInstanceIndex);

            int direction = (int) Math.signum(index - this.selectedFilterInstanceIndex);
            switch (direction) {
                case 1:
                    this.filterInstanceManager.moveInstanceDown(instance);
                    break;
                case -1:
                    this.filterInstanceManager.moveInstanceUp(instance);
            }
        }

        this.selectedFilterInstanceIndex = index;
        this.lstFilterInstances.setSelectedIndex(index);
    }

    private void menuViewSequenceCutActionPerformed(ActionEvent evt) {
        this.txtViewSequence.cut();
    }

    private void menuViewSequeneCopyActionPerformed(ActionEvent evt) {
        this.txtViewSequence.copy();
    }

    private void runDiagnostics(List<ProteinSequence> sequences) {
        Iterator iterator = this.diagnosticsInstanceManager.getPluginInstances();
        boolean error = false;
        try {
            while (iterator.hasNext()) {
                PluginInstance instance = (PluginInstance) iterator.next();
                ((IProteinDiagnosticsTool) instance.getPluginInstance()).initialize();
            }
        } catch (InitializationException ex) {
            error = true;
            ex.printStackTrace();
            String[] arr$ = ex.getReasons();
            int len$ = arr$.length;
            for (int i$ = 0; i$ < len$; ++i$) {
                String reason = arr$[i$];
                showError(reason);
            }
        }
        if (!(error)) {
            ProteinDiagnosticResult result = new ProteinDiagnosticResult();
            iterator = this.diagnosticsInstanceManager.getPluginInstances();
            while (iterator.hasNext()) {
                PluginInstance instance = (PluginInstance) iterator.next();
                try {
                    result.addDiagnostic(((IProteinDiagnosticsTool) instance.getPluginInstance()).runDiagnostics(sequences));
                } catch (Exception ex) {
                    showError("Error running diagnostics tool " + instance.getName() + ":\n" + ex.getMessage());
                }
            }
            if ((result.getGraphicResults().size() == 0) && (result.getTextResults().size() == 0)) {
                JOptionPane.showMessageDialog(this.pagingPanel, "No results to display", "Empty Result Set", 2);
            } else {
                for (Iterator i$ = result.getTextResults().iterator(); i$.hasNext();) {
                    ProteinDiagnosticTextElement textElement = (ProteinDiagnosticTextElement) i$.next();
                    this.tabPageDiagnosticResults.addTab(textElement.getName(), new DiagnosticsTextDisplay(textElement.getText()));
                }

                for (ProteinDiagnosticImageElement imageElement : result.getGraphicResults()) {
                    this.tabPageDiagnosticResults.addTab(imageElement.getName(), new DiagnosticsImageDisplay(imageElement.getImage()));
                }
                this.tabPageMain.setSelectedComponent(this.panelDiagnosticResults);
            }
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {

            public void run() {
                new FrmMain().setVisible(true);
            }
        });
    }

    public void update() {
        String[] plugins;
        try {
            plugins = this.filterInstanceManager.getAvailablePlugins();
            final DefaultListModel model = new DefaultListModel();
            String[] arr$ = plugins;
            int len$ = arr$.length;
            for (int i$ = 0; i$ < len$; ++i$) {
                String pluginName = arr$[i$];
                model.add(model.size(), pluginName);
            }

            Iterator instances = this.filterInstanceManager.getPluginInstances();
            final DefaultListModel instanceModel = new DefaultListModel();
            while (instances.hasNext()) {
                PluginInstance instance = (PluginInstance) instances.next();
                instanceModel.add(instanceModel.size(), instance);
            }

            Runnable updater = new Runnable() {

                public void run() {
                    lstSequenceFilters.setModel(model);
                    lstFilterInstances.setModel(instanceModel);
                }
            };
            if (SwingUtilities.isEventDispatchThread()) {
                updater.run();
            } else {
                SwingUtilities.invokeAndWait(updater);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            showError(ex.getMessage());
        }
    }
}