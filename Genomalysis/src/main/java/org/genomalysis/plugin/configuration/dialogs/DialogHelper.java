package org.genomalysis.plugin.configuration.dialogs;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.genomalysis.plugin.configuration.ConfigurationException;
import org.genomalysis.plugin.configuration.ConfigurationTables;
import org.genomalysis.plugin.configuration.Property;
import org.genomalysis.plugin.configuration.PropertyDescriptor;
import org.genomalysis.plugin.configuration.PropertyIntrospector;
import org.genomalysis.plugin.configuration.VoidConfigurator;

public class DialogHelper {
    public static Frame getRootFrame(JComponent component) {
        Component c = component;
        while (!(c instanceof Frame))
            c = c.getParent();

        return ((Frame) c);
    }

    public static void attemptNullPropertyInstantiation(
            Property configurationProperty, Object host, JComponent base)
            throws ConfigurationException {
        Frame frame = getRootFrame(base);
        TypeSelectionDialog typeSelectionDlg = new TypeSelectionDialog(frame);

        System.out
                .println("DialogHelper: attempting to create instance for null property...");

        Class<?> propertyType = configurationProperty.getPropertyType();
        System.out.println("DialogHelper: Property type: "
                + propertyType.getName());

        List<Class<?>> replacements = new ArrayList<>();
        replacements.addAll(ConfigurationTables.getReplacementTable()
                .recursivelyGetReplacements(propertyType));

        System.out.println("DialogHelper: Possible replacement types:");
        for (Class<?> clazzm : replacements) {
            System.out.println("DialogHelper:      " + clazzm.getName());
        }

        if (replacements.size() > 0) {
            if ((!(propertyType.isInterface()))
                    && (propertyType != Object.class)) {
                replacements.add(0, propertyType);
            }

            Class<?> selectedReplacement = null;

            if (replacements.size() > 1) {
                if (typeSelectionDlg.showDialog(replacements)) {
                    selectedReplacement = typeSelectionDlg.getSelectedType();
                } else {
                    selectedReplacement = replacements.get(0);
                }

            } else {
                selectedReplacement = replacements.get(0);
            }

            try {
                Constructor<?> ctor = selectedReplacement
                        .getConstructor(new Class[0]);
                Object instance = ctor.newInstance(new Object[0]);

                configurationProperty.getSetter().invoke(host,
                        new Object[] { instance });

                PropertyIntrospector.setPropertyConfigurator(
                        configurationProperty, host);
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new ConfigurationException(
                        "Could not instantiate new instance", ex);
            }
        }
    }

    public static void populateGenericDialog(GenericDialog dlg,
            final Object toBeConfigured) throws ConfigurationException {
        JPanel mainPanel = dlg.getMainPanel();

        mainPanel.removeAll();
        mainPanel.setLayout(new GridLayout(0, 1, 5, 5));

        final GenericDialog configDlg = dlg;

        dlg.setTitle("Configuration: "
                + toBeConfigured.getClass().getSimpleName());

        PropertyDescriptor descriptor = PropertyIntrospector
                .getProperties(toBeConfigured);
        for (Property prop : descriptor.getProperties()) {
            final JPanel panel = new JPanel(new BorderLayout());
            panel.setVisible(true);
            JLabel nameLabel = new JLabel(prop.getPropertyName() + ": ");
            nameLabel.setVisible(true);
            String labelText = (prop.getPropertyValue() == null) ? "null"
                    : prop.getPropertyValue().toString();
            final JLabel valueLabel = new JLabel(labelText);

            JButton configureButton = new JButton("Configure...");

            panel.add(nameLabel, "West");
            panel.add(valueLabel, "Center");
            panel.add(configureButton, "East");

            if (prop.getConfigurator() instanceof VoidConfigurator) {
                configureButton.setEnabled(false);
            }

            mainPanel.add(panel);

            final Property configurationProperty = prop;
            configureButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
                    try {
                        if (configurationProperty.getPropertyValue() == null) {
                            DialogHelper.attemptNullPropertyInstantiation(
                                    configurationProperty, toBeConfigured,
                                    panel);
                        }

                        configurationProperty.configure(panel);
                        String text = (configurationProperty.getPropertyValue() == null) ? "null"
                                : configurationProperty.getPropertyValue()
                                        .toString();
                        valueLabel.setText((text.length() > 10) ? text
                                .substring(0, 10) + "..." : text);

                        configDlg.pack();
                    } catch (ConfigurationException ex) {
                        Logger.getLogger(DialogHelper.class.getName()).log(
                                Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(panel, ex.getMessage(),
                                "Configuration Error", 0);
                    }
                }
            });
        }

        JPanel controlPanel = new JPanel(new FlowLayout());
        JButton okButton = new JButton("OK");
        controlPanel.add(okButton);

        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                configDlg.setVisible(false);
            }

        });
        mainPanel.add(controlPanel);
        dlg.pack();
    }
}