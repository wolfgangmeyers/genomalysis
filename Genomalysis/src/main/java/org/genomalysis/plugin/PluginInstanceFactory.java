package org.genomalysis.plugin;

public interface PluginInstanceFactory<T> {

    String getName();

    PluginInstance<T> createInstance() throws Exception;

    String getDocumentation();

}
