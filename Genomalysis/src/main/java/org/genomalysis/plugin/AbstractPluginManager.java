package org.genomalysis.plugin;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.genomalysis.plugin.configuration.ConfigurationTables;
import org.genomalysis.plugin.configuration.IPropertyConfigurator;
import org.genomalysis.plugin.configuration.PropertyConfiguratorTable;
import org.genomalysis.plugin.configuration.annotations.Configurator;
import org.genomalysis.plugin.configuration.dialogs.GenericConfigurator;

public abstract class AbstractPluginManager implements IPluginFoundCallback {

	/**
	 * Map from interface name to list of implementing classes
	 */
	private Map<String, List<Class<?>>> pluginTypes;
	private List<IObserver> observers;
	protected List<Class<?>> pluginInterfaces;

	public AbstractPluginManager() {
		this.pluginTypes = new HashMap<>();

		this.observers = new ArrayList<>();

		this.pluginInterfaces = new ArrayList<>();
	}

	/**
	 * Gets all known implementing classes of the given interface
	 * 
	 * @param pluginInterface
	 * @return
	 */
	public List<Class<?>> getPluginTypes(Class<?> pluginInterface) {
		String classname = pluginInterface.getName();
		List<Class<?>> result = null;
		result = this.pluginTypes.get(classname);
		if (result == null) {
			result = new ArrayList<>();
			this.pluginTypes.put(classname, result);
		}
		return result;
	}

	/**
	 * Clear everything
	 */
	public void clearAllPlugins() {
		this.pluginInterfaces.clear();
		this.pluginTypes.clear();
		notifyObservers();
	}

	/**
	 * Registers the plugin interface with this plugin manager. I have yet to
	 * figure out why we would want to do this...
	 * 
	 * @param iface
	 */
	public void addPluginInterface(Class<?> iface) {
		if (!(this.pluginInterfaces.contains(iface))) {
			this.pluginInterfaces.add(iface);
		}
	}

	/**
	 * This method appears to relay a message to any observers of this object.
	 */
	public void showError(String message) {
		notifyObserversOfError(message);
	}

	/**
	 * Gets the appropriate property configurator for the given object.
	 * Configurators spawn user interface dialogs that are used to set
	 * properties on the object. Once instantiated, property configurators are
	 * cached in a PropertyConfigurationTable, and reused on subsequent calls
	 * for objects of the same class.
	 * 
	 * Objects that don't specify a configurator, or whose configurator throws
	 * an exception during instantiation, are assigned a generic configurator
	 * that displays editable properties found by type introspection.
	 * 
	 * @param toBeConfigured
	 * @return
	 */
	public IPropertyConfigurator getConfigurator(Object toBeConfigured) {
		IPropertyConfigurator configurator = null;
		PropertyConfiguratorTable propertyConfigurationTable = ConfigurationTables
				.getPropertyConfiguratorTable();

		// first, see if there is a configurator cached
		if (propertyConfigurationTable
				.IsClassRegisteredForConfiguration(toBeConfigured.getClass())) {
			// if so, use it.
			configurator = propertyConfigurationTable
					.getConfigurationDialog(toBeConfigured.getClass());

		} else if (toBeConfigured.getClass().getAnnotation(Configurator.class) != null) {
			// if not, create it and cache it.
			Configurator ann = (Configurator) toBeConfigured.getClass()
					.getAnnotation(Configurator.class);
			Class<?> cfgClass = ann.value();
			try {
				Constructor<?> ctor = cfgClass.getConstructor(new Class[0]);

				configurator = (IPropertyConfigurator) ctor
						.newInstance(new Object[0]);
			} catch (Exception ex) {
				// TODO: log an error here?
				configurator = new GenericConfigurator();
			}
			// cache it
			propertyConfigurationTable.RegisterPropertyConfigurationDialog(
					toBeConfigured.getClass(), configurator);
		} else {
			// in the case that a configurator hasn't been specified, this type
			// of object gets a generic configurator.
			configurator = new GenericConfigurator();
			propertyConfigurationTable.RegisterPropertyConfigurationDialog(
					toBeConfigured.getClass(), configurator);
		}
		return configurator;
	}

	/**
	 * Implemented by FilePluginManager. Called on startup.
	 */
	public abstract void findPlugins();

	/**
	 * Registers an observer that can listen for state changes in the plugin
	 * manager.
	 * 
	 * @param observer
	 */
	public void addObserver(IObserver observer) {
		if (!(this.observers.contains(observer))) {
			this.observers.add(observer);
		}
	}

	/**
	 * Unregisters an observer
	 * 
	 * @param observer
	 */
	public void removeObserver(IObserver observer) {
		if (this.observers.contains(observer)) {
			this.observers.remove(observer);
		}
	}

	/**
	 * Retrieves documentation for this type, specified in the @Documentation
	 * annotation.
	 * 
	 * @param clazz
	 * @return
	 */
	public String getDocumentation(Class<?> clazz) {
		return ConfigurationTables.getDocumentationTable().getDocumentation(
				clazz);
	}

	/**
	 * Implementation of IPluginFoundCallback - called by PluginManager
	 * subclasses to register implementations of the plugin interface.
	 */
	public void pluginFound(Class<?> pluginInterface, Class<?> pluginClass) {
		System.out.println("AbstractPluginManager::pluginFound");

		String classname = pluginInterface.getName();

		try {
			ConfigurationTables.getReplacementTable().registerReplacement(
					pluginInterface, pluginClass);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		List<Class<?>> plugins = this.pluginTypes.get(classname);
		if (plugins == null) {
			plugins = new ArrayList<>();
			this.pluginTypes.put(classname, plugins);
		}
		if (!(plugins.contains(pluginClass))) {
			plugins.add(pluginClass);
		}
	}

	/**
	 * Notifies observers that the state of this plugin manager has changed.
	 */
	protected void notifyObservers() {
		System.out.println("AbstractPluginManager::notifyObservers");
		for (IObserver observer : this.observers) {
			observer.update();
		}
	}

	/**
	 * Notifies observers that an error has occurred, and that the end user may
	 * need to be notified that something has broken.
	 * 
	 * @param errorMsg
	 */
	protected void notifyObserversOfError(String errorMsg) {
		System.out.println("AbstractPluginManager::notifyObserversOfError");
		for (IObserver observer : this.observers) {
			observer.showError(errorMsg);
		}
	}
}