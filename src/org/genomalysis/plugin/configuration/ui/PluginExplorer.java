package org.genomalysis.plugin.configuration.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.PrintStream;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;

import org.genomalysis.plugin.FilePluginManager;
import org.genomalysis.plugin.PluginInstanceManager;
import org.genomalysis.plugin.PluginLoader;
import org.genomalysis.plugin.configuration.ConfigurationTables;
import org.genomalysis.plugin.configuration.DocumentationTable;

public class PluginExplorer extends JFrame
{
  private PluginLoader pluginLoader;
  private FilePluginManager pluginManager;
  private List<PluginInstanceManager<?>> instanceManagers;
  private List<Class<?>> foundTypes = new ArrayList();
  private JFileChooser fileDlg = new JFileChooser();
  private JarFilter jarFilter = new JarFilter();
  private JButton btnAddPluginType;
  private JButton btnBrowseForPluginTypes;
  private JButton btnPluginTypeInfo;
  private ButtonGroup buttonGroup1;
  private JLabel jLabel1;
  private JPanel jPanel1;
  private JPanel jPanel2;
  private JPanel jPanel3;
  private JScrollPane jScrollPane1;
  private JList lstFoundTypes;
  private JPanel mainPage;
  private JTabbedPane mainTabControl;
  private JRadioButton rbLoadClasses;
  private JRadioButton rbLoadClassesAndInterfaces;
  private JRadioButton rbLoadInterfaces;

  public PluginExplorer()
  {
    initComponents();

    this.pluginLoader = PluginLoader.getInstance();
    this.pluginManager = new FilePluginManager();
    this.pluginManager.setDaemon(false);
    this.instanceManagers = new ArrayList();
    this.fileDlg.setFileFilter(this.jarFilter);

    this.buttonGroup1.add(this.rbLoadClasses);
    this.buttonGroup1.add(this.rbLoadInterfaces);
    this.buttonGroup1.add(this.rbLoadClassesAndInterfaces);

    File file = new File("plugins");
    if (!(file.exists()))
      file.mkdirs();
  }

  private void initComponents()
  {
    this.buttonGroup1 = new ButtonGroup();
    this.mainTabControl = new JTabbedPane();
    this.mainPage = new JPanel();
    this.jPanel1 = new JPanel();
    this.jLabel1 = new JLabel();
    this.jScrollPane1 = new JScrollPane();
    this.lstFoundTypes = new JList();
    this.jPanel2 = new JPanel();
    this.jPanel3 = new JPanel();
    this.btnBrowseForPluginTypes = new JButton();
    this.btnAddPluginType = new JButton();
    this.btnPluginTypeInfo = new JButton();
    this.rbLoadInterfaces = new JRadioButton();
    this.rbLoadClasses = new JRadioButton();
    this.rbLoadClassesAndInterfaces = new JRadioButton();

    setDefaultCloseOperation(3);
    setTitle("Plugin Framework Explorer");

    this.mainPage.setLayout(new BorderLayout(30, 0));

    this.jPanel1.setLayout(new BorderLayout(0, 10));

    this.jLabel1.setText("Types Found:");
    this.jPanel1.add(this.jLabel1, "North");

    this.lstFoundTypes.addListSelectionListener(new ListSelectionListener() {
      public void valueChanged(ListSelectionEvent evt) {
        lstFoundTypesValueChanged(evt);
      }

    });
    this.jScrollPane1.setViewportView(this.lstFoundTypes);

    this.jPanel1.add(this.jScrollPane1, "Center");

    this.mainPage.add(this.jPanel1, "Center");

    this.jPanel2.setLayout(new BorderLayout());

    this.jPanel3.setLayout(new GridLayout(6, 0, 10, 10));

    this.btnBrowseForPluginTypes.setText("Browse...");
    this.btnBrowseForPluginTypes.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        btnBrowseForPluginTypesActionPerformed(evt);
      }

    });
    this.jPanel3.add(this.btnBrowseForPluginTypes);

    this.btnAddPluginType.setText("Add Type");
    this.btnAddPluginType.setEnabled(false);
    this.btnAddPluginType.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        btnAddPluginTypeActionPerformed(evt);
      }

    });
    this.jPanel3.add(this.btnAddPluginType);

    this.btnPluginTypeInfo.setText("Documentation...");
    this.btnPluginTypeInfo.setEnabled(false);
    this.btnPluginTypeInfo.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        btnPluginTypeInfoActionPerformed(evt);
      }

    });
    this.jPanel3.add(this.btnPluginTypeInfo);

    this.rbLoadInterfaces.setSelected(true);
    this.rbLoadInterfaces.setText("Load Interfaces");
    this.jPanel3.add(this.rbLoadInterfaces);

    this.rbLoadClasses.setText("Load Classes");
    this.jPanel3.add(this.rbLoadClasses);

    this.rbLoadClassesAndInterfaces.setText("Load Both");
    this.jPanel3.add(this.rbLoadClassesAndInterfaces);

    this.jPanel2.add(this.jPanel3, "North");

    this.mainPage.add(this.jPanel2, "East");

    this.mainTabControl.addTab("Find Plugin Types", this.mainPage);

    getContentPane().add(this.mainTabControl, "Center");

    pack();
  }

  private void lstFoundTypesValueChanged(ListSelectionEvent evt) {
    boolean enabled = this.lstFoundTypes.getSelectedIndex() != -1;
    this.btnAddPluginType.setEnabled(enabled);
    this.btnPluginTypeInfo.setEnabled(enabled);
  }

  private void btnBrowseForPluginTypesActionPerformed(ActionEvent evt) {
    if (this.fileDlg.showOpenDialog(this.mainPage) == 0)
    {
      File file = this.fileDlg.getSelectedFile();
      try
      {
        List classes = null;
        URL jarfile = file.toURI().toURL();
        if (this.rbLoadClasses.isSelected())
          classes = this.pluginLoader.loadAllClasses(jarfile);
        else if (this.rbLoadInterfaces.isSelected())
          classes = this.pluginLoader.loadAllInterfaces(jarfile);
        else
          classes = this.pluginLoader.loadAllTypes(jarfile);

        this.foundTypes.clear();
        this.foundTypes.addAll(classes);
        DefaultListModel listModel = new DefaultListModel();
        for (Iterator i$ = this.foundTypes.iterator(); i$.hasNext(); ) { Class clazz = (Class)i$.next();
          listModel.add(listModel.size(), clazz.getName());
        }
        this.lstFoundTypes.setModel(listModel);
      } catch (Exception ex) {
        ex.printStackTrace();
        _showError(ex.getMessage());
      }
    }
  }

  private void btnPluginTypeInfoActionPerformed(ActionEvent evt)
  {
    int index = this.lstFoundTypes.getSelectedIndex();
    if (index == -1) {
      System.out.println("WARNING: btnPluginTypeInfo should be disabled, but it was clicked anyway!");
      this.btnPluginTypeInfo.setEnabled(false);
    } else {
      Class clazz = (Class)this.foundTypes.get(index);
      String documentation = ConfigurationTables.getDocumentationTable().getDocumentation(clazz);
      DocViewer.showDocumentation(this.mainPage, documentation);
    }
  }

  private void btnAddPluginTypeActionPerformed(ActionEvent evt) {
    int index = this.lstFoundTypes.getSelectedIndex();
    if (index == -1) {
      System.out.println("WARNING: btnPluginTypeInfo should be disabled, but it was clicked anyway!");
      this.btnAddPluginType.setEnabled(false);
    } else {
      Class clazz = (Class)this.foundTypes.get(index);
      PluginInstanceManager instanceManager = new PluginInstanceManager(clazz, this.pluginManager);
      InstancePanel panel = new InstancePanel(instanceManager, this.pluginManager, true);
      this.mainTabControl.addTab(clazz.getSimpleName(), panel);
      this.pluginManager.addPluginInterface(clazz);
    }
  }

  private void _showError(String message) {
    JOptionPane.showMessageDialog(this.mainPage, message, "Error", 0);
  }

  public static void main(String[] args)
  {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        new PluginExplorer().setVisible(true);
      }
    });
  }

  private class JarFilter extends FileFilter
  {
    public boolean accept(File arg0)
    {
      return ((arg0.isDirectory()) || (arg0.getName().endsWith(".jar")));
    }

    public String getDescription()
    {
      return "Jar files";
    }
  }
}