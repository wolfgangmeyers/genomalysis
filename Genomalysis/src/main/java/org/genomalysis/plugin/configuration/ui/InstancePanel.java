package org.genomalysis.plugin.configuration.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.genomalysis.control.IObserver;
import org.genomalysis.plugin.PluginManager;
import org.genomalysis.plugin.PluginInstance;
import org.genomalysis.plugin.PluginInstanceFactory;
import org.genomalysis.plugin.PluginInstanceManager;
import org.genomalysis.plugin.configuration.ConfigurationException;
import org.genomalysis.plugin.configuration.ConfigurationTables;
import org.genomalysis.plugin.configuration.IPropertyConfigurator;
import org.genomalysis.plugin.configuration.dialogs.DialogHelper;

public class InstancePanel<T> extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PluginInstanceManager<T> instanceManager;
	private PluginManager pluginManager;
	private IObserver observer;
	private RenameDialog dlg;
	private JButton btnClose;
	private JButton btnConfigureInstance;
	private JButton btnCreateInstance;
	private JButton btnInfo;
	private JButton btnReload;
	private JButton btnRemoveInstance;
	private JButton btnRenameInstance;
	private JPanel buttonPanel;
	private JLabel jLabel1;
	private JLabel jLabel2;
	private JLabel jLabel3;
	private JPanel jPanel1;
	private JPanel jPanel2;
	private JPanel jPanel3;
	private JPanel jPanel4;
	private JScrollPane jScrollPane1;
	private JScrollPane jScrollPane2;
	private JList<PluginInstance<T>> lstPluginInstances;
	private JList<String> lstPluginTypes;

	public InstancePanel(final PluginInstanceManager<T> instanceManager,
			PluginManager pluginManager, boolean closeable) {
		this.observer = null;
		this.dlg = null;

		initComponents();
		this.pluginManager = pluginManager;
		this.instanceManager = instanceManager;
		final InstancePanel<T> panel = this;

		this.observer = new IObserver() {
			public void update() {
				DefaultListModel<String> model = new DefaultListModel<String>();
				String[] availablePlugins = instanceManager
						.getAvailablePlugins();
				for (int i = 0; i < availablePlugins.length; i++) {
					String pluginName = availablePlugins[i];
					model.add(model.size(), pluginName);
				}

				lstPluginTypes.setModel(model);

				DefaultListModel<PluginInstance<T>> pluginModel = new DefaultListModel<PluginInstance<T>>();
				Iterator<PluginInstance<T>> instances = instanceManager
						.getPluginInstances();
				while (instances.hasNext()) {
					PluginInstance<T> instance = instances.next();
					pluginModel.add(pluginModel.size(), instance);
				}
				lstPluginInstances.setModel(pluginModel);
			}

			public void showError(String errorMsg) {
				JOptionPane.showMessageDialog(panel, errorMsg);
			}

		};
		instanceManager.addObserver(this.observer);
		if (!(closeable)) {
			this.buttonPanel.remove(this.btnClose);
			int rows = ((GridLayout) this.buttonPanel.getLayout()).getRows();
			((GridLayout) this.buttonPanel.getLayout()).setRows(rows - 1);
		}
	}

	public InstancePanel(PluginInstanceManager<T> instanceManager,
			PluginManager pluginManager) {
		this(instanceManager, pluginManager, false);
	}

	private void initComponents() {
		this.jPanel1 = new JPanel();
		this.jLabel1 = new JLabel();
		this.jScrollPane1 = new JScrollPane();
		this.lstPluginTypes = new JList<String>();
		this.jPanel2 = new JPanel();
		this.jPanel3 = new JPanel();
		this.buttonPanel = new JPanel();
		this.jLabel3 = new JLabel();
		this.btnCreateInstance = new JButton();
		this.btnRemoveInstance = new JButton();
		this.btnConfigureInstance = new JButton();
		this.btnInfo = new JButton();
		this.btnRenameInstance = new JButton();
		this.btnReload = new JButton();
		this.btnClose = new JButton();
		this.jPanel4 = new JPanel();
		this.jLabel2 = new JLabel();
		this.jScrollPane2 = new JScrollPane();
		this.lstPluginInstances = new JList<PluginInstance<T>>();

		setLayout(new GridLayout(1, 0, 20, 0));

		this.jPanel1.setLayout(new BorderLayout(0, 10));

		this.jLabel1.setHorizontalAlignment(0);
		this.jLabel1.setText("Available Implementing Types:");
		this.jPanel1.add(this.jLabel1, "North");

		this.lstPluginTypes.setSelectionMode(0);
		this.lstPluginTypes
				.addListSelectionListener(new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent evt) {
						lstPluginTypesValueChanged(evt);
					}

				});
		this.lstPluginTypes.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				lstPluginTypesMouseClicked(evt);
			}

		});
		this.jScrollPane1.setViewportView(this.lstPluginTypes);

		this.jPanel1.add(this.jScrollPane1, "Center");

		add(this.jPanel1);

		this.jPanel2.setLayout(new BorderLayout(20, 0));

		this.jPanel3.setLayout(new BorderLayout());

		this.buttonPanel.setLayout(new GridLayout(8, 0, 0, 10));

		this.jLabel3.setHorizontalAlignment(0);
		this.jLabel3.setText("Options:");
		this.buttonPanel.add(this.jLabel3);

		this.btnCreateInstance.setText("Create >>");
		this.btnCreateInstance
				.setToolTipText("<html>\nCreates a new instance of the selected plugin<br>\nin the <font color=\"00AA00\">Available Implementing Types</font> list and adds<br>\nit to the <font color=\"00AA00\">Instances</font> list.\n</html>");
		this.btnCreateInstance.setEnabled(false);
		this.btnCreateInstance.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnCreateInstanceActionPerformed(evt);
			}

		});
		this.buttonPanel.add(this.btnCreateInstance);

		this.btnRemoveInstance.setText("Remove");
		this.btnRemoveInstance
				.setToolTipText("<html>\nRemoves the selected filter\nin the <font color=\"00AA00\">Instances</font> list\n</html>");
		this.btnRemoveInstance.setEnabled(false);
		this.btnRemoveInstance.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnRemoveInstanceActionPerformed(evt);
			}

		});
		this.buttonPanel.add(this.btnRemoveInstance);

		this.btnConfigureInstance.setText("Configure...");
		this.btnConfigureInstance
				.setToolTipText("<html>\nConfigures the selected filter in\nthe <font color=\"00AA00\">Instances</font> list\n</html>");
		this.btnConfigureInstance.setEnabled(false);
		this.btnConfigureInstance.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnConfigureInstanceActionPerformed(evt);
			}

		});
		this.buttonPanel.add(this.btnConfigureInstance);

		this.btnInfo.setText("Info...");
		this.btnInfo
				.setToolTipText("<html>\nRetrieves documentation about the selected<br>\nplugin in the <font color=\"00AA00\">Available Implementing Types</font> list\n</html>");
		this.btnInfo.setEnabled(false);
		this.btnInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnInfoActionPerformed(evt);
			}

		});
		this.buttonPanel.add(this.btnInfo);

		this.btnRenameInstance.setText("Rename...");
		this.btnRenameInstance
				.setToolTipText("<html>\nRenames the selected filter in<br>\nthe <font color=\"00AA00\">Instances</font> list\n</html>");
		this.btnRenameInstance.setEnabled(false);
		this.btnRenameInstance.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnRenameInstanceActionPerformed(evt);
			}

		});
		this.buttonPanel.add(this.btnRenameInstance);

		this.btnReload.setText("Load");
		this.btnReload.setToolTipText("Forces reload of plugins");
		this.btnReload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnReloadActionPerformed(evt);
			}

		});
		this.buttonPanel.add(this.btnReload);

		this.btnClose.setText("Close");
		this.btnClose.setToolTipText("Closes this tab");
		this.btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnCloseActionPerformed(evt);
			}

		});
		this.buttonPanel.add(this.btnClose);

		this.jPanel3.add(this.buttonPanel, "North");

		this.jPanel2.add(this.jPanel3, "West");

		this.jPanel4.setLayout(new BorderLayout(0, 10));

		this.jLabel2.setHorizontalAlignment(0);
		this.jLabel2.setText("Instances:");
		this.jPanel4.add(this.jLabel2, "North");

		this.lstPluginInstances.setSelectionMode(0);
		this.lstPluginInstances
				.addListSelectionListener(new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent evt) {
						lstPluginInstancesValueChanged(evt);
					}

				});
		this.lstPluginInstances
				.addMouseListener(new java.awt.event.MouseAdapter() {
					public void mouseClicked(MouseEvent evt) {
						lstPluginInstancesMouseClicked(evt);
					}

				});
		this.jScrollPane2.setViewportView(this.lstPluginInstances);

		this.jPanel4.add(this.jScrollPane2, "Center");

		this.jPanel2.add(this.jPanel4, "Center");

		add(this.jPanel2);
	}

	private void btnReloadActionPerformed(ActionEvent evt) {
		this.pluginManager.findPlugins();
	}

	private void btnCloseActionPerformed(ActionEvent evt) {
		getParent().remove(this);
		this.instanceManager.removeObserver(this.observer);
		this.instanceManager.dispose();
	}

	private void lstPluginTypesValueChanged(ListSelectionEvent evt) {
		boolean enabled = this.lstPluginTypes.getSelectedIndex() != -1;
		this.btnCreateInstance.setEnabled(enabled);
		this.btnInfo.setEnabled(enabled);
	}

	private void lstPluginInstancesValueChanged(ListSelectionEvent evt) {
		boolean enabled = this.lstPluginInstances.getSelectedIndex() != -1;
		this.btnConfigureInstance.setEnabled(enabled);
		this.btnRemoveInstance.setEnabled(enabled);
		this.btnRenameInstance.setEnabled(enabled);
	}

	private void btnCreateInstanceActionPerformed(ActionEvent evt) {
		int index = this.lstPluginTypes.getSelectedIndex();
		if (index > -1)
			try {
				this.instanceManager.addPluginInstance(index);
			} catch (ConfigurationException ex) {
				Logger.getLogger(InstancePanel.class.getName()).log(
						Level.SEVERE, null, ex);
				_showError("Configuration Error:\n" + ex.getMessage());
			}
	}

	private void btnRemoveInstanceActionPerformed(ActionEvent evt) {
		PluginInstance<T> instance = this.lstPluginInstances.getSelectedValue();
		if (instance != null)
			this.instanceManager.removePluginInstance(instance);
	}

	private void btnConfigureInstanceActionPerformed(ActionEvent evt) {
		PluginInstance<T> instanceWrapper = this.lstPluginInstances
				.getSelectedValue();
		if (instanceWrapper != null)
			try {
				Object instance = instanceWrapper.getPluginInstance();
				IPropertyConfigurator configurator = this.pluginManager
						.getConfigurator(instance);
				configurator.showDialog(this, instance);
			} catch (ConfigurationException ex) {
				Logger.getLogger(InstancePanel.class.getName()).log(
						Level.SEVERE, null, ex);
				_showError("Configuration Error:\n" + ex.getMessage());
			}
	}

	private void btnInfoActionPerformed(ActionEvent evt) {
		String documentation = null;
		if (this.lstPluginTypes.getSelectedIndex() != -1) {
			PluginInstanceFactory<?> factory = this.instanceManager
					.getInstanceFactory(this.lstPluginTypes.getSelectedIndex());
			documentation = ConfigurationTables.getDocumentationTable()
					.getDocumentation(factory);
			DocViewer.showDocumentation(this, documentation);
		}
	}

	private void btnRenameInstanceActionPerformed(ActionEvent evt) {
		if (this.dlg == null)
			this.dlg = new RenameDialog(DialogHelper.getRootFrame(this));

		PluginInstance<T> instance = this.lstPluginInstances.getSelectedValue();
		String name = instance.getName();
		instance.setName(this.dlg.showDialog(name));
		this.lstPluginInstances.repaint();
	}

	private void lstPluginTypesMouseClicked(MouseEvent evt) {
		if ((this.lstPluginTypes.getSelectedIndex() > -1)
				&& (evt.getClickCount() == 2))
			btnCreateInstanceActionPerformed(null);
	}

	private void lstPluginInstancesMouseClicked(MouseEvent evt) {
		if ((this.lstPluginInstances.getSelectedIndex() > -1)
				&& (evt.getClickCount() == 2))
			btnConfigureInstanceActionPerformed(null);
	}

	private void _showError(String errorMessage) {
		JOptionPane.showMessageDialog(this, errorMessage, "Error", 0);
	}
}