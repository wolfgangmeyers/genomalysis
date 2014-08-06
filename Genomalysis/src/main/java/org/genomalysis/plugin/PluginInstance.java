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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((factoryName == null) ? 0 : factoryName.hashCode());
        result = prime * result
                + ((instance == null) ? 0 : instance.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PluginInstance other = (PluginInstance) obj;
        if (factoryName == null) {
            if (other.factoryName != null)
                return false;
        } else if (!factoryName.equals(other.factoryName))
            return false;
        if (instance == null) {
            if (other.instance != null)
                return false;
        } else if (!instance.equals(other.instance))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }
}