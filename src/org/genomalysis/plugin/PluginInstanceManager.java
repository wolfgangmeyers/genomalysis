package org.genomalysis.plugin;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.genomalysis.plugin.configuration.ConfigurationException;

public class PluginInstanceManager<T>
  implements IObserver
{
  private Class<T> clazz;
  private List<PluginInstance<T>> pluginInstances = new ArrayList();
  private List<IObserver> observers = new ArrayList();
  private AbstractPluginManager pluginManager;
  private boolean showingSimpleNames = true;

  public PluginInstanceManager(Class<T> clazz, AbstractPluginManager pluginManager)
  {
    this.clazz = clazz;
    this.pluginManager = pluginManager;
    pluginManager.addObserver(this);
  }

  public void dispose() {
    this.pluginManager.removeObserver(this);
  }

  public void addObserver(IObserver observer) {
    synchronized (this.observers) {
      if (!(this.observers.contains(observer)))
        this.observers.add(observer);
    }
  }

  public void removeObserver(IObserver observer) {
    synchronized (this.observers) {
      this.observers.remove(observer);
    }
  }

  private void notifyObservers() {
    synchronized (this.observers) {
      for (Iterator i$ = this.observers.iterator(); i$.hasNext(); ) { IObserver observer = (IObserver)i$.next();
        observer.update();
      }
    }
  }

  private void notifyObserversOfError(String message) {
    synchronized (this.observers) {
      for (Iterator i$ = this.observers.iterator(); i$.hasNext(); ) { IObserver observer = (IObserver)i$.next();
        observer.showError(message);
      }
    }
  }

  public boolean isShowingSimpleNames() {
    return this.showingSimpleNames;
  }

  public void setShowingSimpleNames(boolean showingSimpleNames) {
    this.showingSimpleNames = showingSimpleNames;
  }

  public String[] getAvailablePlugins() {
    List pluginTypes = this.pluginManager.getPluginTypes(this.clazz);
    List pluginTypeNames = new ArrayList();
    for (Iterator i$ = pluginTypes.iterator(); i$.hasNext(); ) { Class pluginType = (Class)i$.next();
      String pluginTypeName = (this.showingSimpleNames) ? pluginType.getSimpleName() : pluginType.getName();
      pluginTypeNames.add(pluginTypeName);
    }
    String[] result = new String[pluginTypeNames.size()];
    result = (String[])pluginTypeNames.toArray(result);
    return result;
  }

  public boolean moveInstanceDown(PluginInstance<T> instance)
  {
    boolean result = false;
    int instanceIndex = this.pluginInstances.indexOf(instance);
    if ((instanceIndex != -1) && (instanceIndex < this.pluginInstances.size() - 1)) {
      this.pluginInstances.remove(instance);
      this.pluginInstances.add(instanceIndex + 1, instance);
      notifyObservers();
      result = true;
    }
    return result;
  }

  public boolean moveInstanceUp(PluginInstance<T> instance) {
    boolean result = false;
    int instanceIndex = this.pluginInstances.indexOf(instance);
    if (instanceIndex > 0) {
      this.pluginInstances.remove(instance);
      this.pluginInstances.add(instanceIndex - 1, instance);
      notifyObservers();
      result = true;
    }
    return result;
  }

  public void removePluginInstance(PluginInstance<T> instance) {
    this.pluginInstances.remove(instance);
    notifyObservers();
  }

  public Iterator<PluginInstance<T>> getPluginInstances() {
    return this.pluginInstances.iterator();
  }

  public void clearPluginInstnaces() {
    this.pluginInstances.clear();
  }

  public Class<?> getPluginClass(int index) {
    Class result = null;
    List classes = this.pluginManager.getPluginTypes(this.clazz);
    if ((index >= 0) && (index < classes.size()))
      result = (Class)classes.get(index);

    return result;
  }

  public void addPluginInstance(int index) throws ConfigurationException {
    if (index >= 0) {
      List pluginTypes = this.pluginManager.getPluginTypes(this.clazz);
      if (index < pluginTypes.size())
      {
        try
        {
          Class pluginType = (Class)pluginTypes.get(index);
          Constructor ctor = pluginType.getConstructor(new Class[0]);
          Object instance = ctor.newInstance(new Object[0]);
          PluginInstance instanceWrapper = new PluginInstance(instance);
          this.pluginInstances.add(instanceWrapper);
          notifyObservers();
        } catch (Exception ex) {
          Logger.getLogger(PluginInstanceManager.class.getName()).log(Level.SEVERE, null, ex);
          throw new ConfigurationException("Unable to create plugin instance", ex);
        }
      }
    }
  }

  public void update() {
    notifyObservers();
  }

  public void showError(String errorMsg) {
    notifyObserversOfError(errorMsg);
  }
}