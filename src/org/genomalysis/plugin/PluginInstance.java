package org.genomalysis.plugin;

import java.io.Serializable;

public class PluginInstance<T>
  implements Serializable
{
  private String name;
  private T instance;
  private static int counter = 1;

  public PluginInstance(T instance)
  {
    this.instance = instance;
    Class clazz = instance.getClass();
    this.name = clazz.getSimpleName() + "_" + (counter++);
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return this.name;
  }

  public T getPluginInstance() {
    return this.instance;
  }

  public String toString()
  {
    return getName();
  }
}