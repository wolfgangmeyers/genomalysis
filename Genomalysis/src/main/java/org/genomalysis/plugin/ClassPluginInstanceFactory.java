package org.genomalysis.plugin;

import org.genomalysis.plugin.configuration.annotations.Documentation;

public class ClassPluginInstanceFactory<T> implements PluginInstanceFactory<T>{

    private Class<T> clazz;

    public ClassPluginInstanceFactory(Class<T> clazz) {
        this.clazz = clazz;
    }
    
    @Override
    public PluginInstance<T> createInstance() throws Exception {
        return new PluginInstance<T>(clazz.newInstance(), getName());
    }

    @Override
    public String getName() {
        return clazz.getSimpleName();
    }

    @Override
    public String getDocumentation() {
        Documentation doc = clazz.getAnnotation(Documentation.class);
        return doc.value();
    }
    
}
