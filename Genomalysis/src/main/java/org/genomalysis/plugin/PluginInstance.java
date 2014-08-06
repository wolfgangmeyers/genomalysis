package org.genomalysis.plugin;

import java.io.Serializable;

public class PluginInstance<T> implements Serializable {
    private String name;
    private T instance;
    private String factoryName;

    private static int counter = 1;

    public PluginInstance(T instance, String factoryName, String name) {
        this.instance = instance;
        this.name = name;
        this.factoryName = factoryName;
    }

    public PluginInstance(T instance, String factoryName) {
        this(instance, factoryName, factoryName + "_" + (counter++));
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

    public String toString() {
        return getName();
    }

    public String getFactoryName() {
        return factoryName;
    }

    public void setFactoryName(String factoryName) {
        this.factoryName = factoryName;
    }
}