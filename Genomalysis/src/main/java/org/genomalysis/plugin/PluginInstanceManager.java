package org.genomalysis.plugin;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.genomalysis.control.EventSupport;
import org.genomalysis.control.IObserver;
import org.genomalysis.plugin.configuration.ConfigurationException;
import org.genomalysis.proteintools.IProteinSequenceFilter;
import org.genomalysis.proteintools.signalp.LegacyCleavageSiteFilter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

public class PluginInstanceManager<T> implements IObserver {

    private EventSupport eventSupport = new EventSupport();
    private Class<T> clazz;
    private List<PluginInstance<T>> pluginInstances = new ArrayList<PluginInstance<T>>();
    private PluginManager pluginManager;
    private boolean showingSimpleNames = true;
    private final Gson gson = new GsonBuilder().registerTypeAdapter(
            PluginInstance.class, new PluginInstanceSerializer()).create();

    class PluginInstanceSerializer implements
            JsonDeserializer<PluginInstance<?>>,
            JsonSerializer<PluginInstance<?>> {

        private final Gson gson = new Gson();

        @Override
        public JsonElement serialize(PluginInstance<?> p, Type t,
                JsonSerializationContext ctx) {
            JsonObject result = new JsonObject();

            Object pluginInstance = p.getPluginInstance();
            if (pluginInstance instanceof Serializable) {
                result.addProperty("instanceData",
                        gson.toJson(p.getPluginInstance()));
                result.addProperty("className", p.getPluginInstance()
                        .getClass().getName());
            } else {
                result.addProperty("factoryName", p.getFactoryName());
            }
            result.addProperty("instanceName", p.getName());
            return result;
        }

        @Override
        public PluginInstance<?> deserialize(JsonElement j, Type t,
                JsonDeserializationContext ctx) throws JsonParseException {
            try {
                JsonObject obj = (JsonObject) j;
                String instanceName = obj.get("instanceName").getAsString();

                if (obj.has("className")) {
                    String className = obj.get("className").getAsString();
                    Class<?> instanceClass = (Class<?>) Class
                            .forName(className);
                    Object instance = null;
                    if (obj.has("instanceData")) {
                        String instanceData = obj.get("instanceData")
                                .getAsString();
                        instance = gson.fromJson(instanceData, instanceClass);
                    } else {
                        try {
                            instance = instanceClass.newInstance();
                        } catch (InstantiationException
                                | IllegalAccessException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    return new PluginInstance<Object>(instance, instanceName);
                } else if (obj.has("factoryName")) {
                    String factoryName = obj.get("factoryName").getAsString();
                    PluginInstanceFactory<?> factory = pluginManager
                            .getFactoryByName(factoryName);
                    PluginInstance<?> instance = factory.createInstance();
                    instance.setName(instanceName);
                    return instance;
                } else {
                    throw new RuntimeException("className or factoryName is required");
                }

            } catch (Exception e) {
                throw new JsonParseException(e);
            }
        }
    }

    public String save() {
        Type t = new TypeToken<List<PluginInstance<?>>>() {
        }.getType();
        return gson.toJson(pluginInstances, t);
    }

    public void load(String data) {
        Type t = new TypeToken<List<PluginInstance<T>>>() {
        }.getType();
        List<PluginInstance<T>> result = gson.fromJson(data, t);
        pluginInstances.clear();
        for (PluginInstance<T> instance : result) {
            addPluginInstance(instance);
        }
        notifyObservers();
    }

    public PluginInstanceManager(Class<T> clazz, PluginManager pluginManager) {
        this.clazz = clazz;
        this.pluginManager = pluginManager;
        pluginManager.addObserver(this);

    }

    public void dispose() {
        this.pluginManager.removeObserver(this);
    }

    public void addObserver(IObserver observer) {
        eventSupport.addObserver(observer);
    }

    public void removeObserver(IObserver observer) {
        eventSupport.removeObserver(observer);
    }

    private void notifyObservers() {
        eventSupport.notifyObservers();
    }

    private void notifyObserversOfError(String message) {
        eventSupport.notifyObserversOfError(message);
    }

    public boolean isShowingSimpleNames() {
        return this.showingSimpleNames;
    }

    public void setShowingSimpleNames(boolean showingSimpleNames) {
        this.showingSimpleNames = showingSimpleNames;
    }

    public String[] getAvailablePlugins() {
        List<PluginInstanceFactory<?>> pluginTypes = this.pluginManager
                .getPluginTypes(this.clazz);
        List<String> pluginTypeNames = new ArrayList<String>();
        for (PluginInstanceFactory<?> pluginType : pluginTypes) {
            String pluginTypeName = pluginType.getName();
            pluginTypeNames.add(pluginTypeName);
        }
        String[] result = new String[pluginTypeNames.size()];
        result = (String[]) pluginTypeNames.toArray(result);
        return result;
    }

    public boolean moveInstanceDown(PluginInstance<T> instance) {
        boolean result = false;
        int instanceIndex = this.pluginInstances.indexOf(instance);
        if ((instanceIndex != -1)
                && (instanceIndex < this.pluginInstances.size() - 1)) {
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

    public Iterator<PluginInstance<T>> getPluginInstances() {
        return this.pluginInstances.iterator();
    }

    public void clearPluginInstnaces() {
        this.pluginInstances.clear();
    }

    public PluginInstanceFactory<?> getInstanceFactory(int index) {
        PluginInstanceFactory<?> result = null;
        List<PluginInstanceFactory<?>> factories = this.pluginManager
                .getPluginTypes(this.clazz);
        if ((index >= 0) && (index < factories.size()))
            result = factories.get(index);

        return result;
    }

    public void removePluginInstance(PluginInstance<T> instance) {
        this.pluginInstances.remove(instance);
        notifyObservers();
    }

    public void addPluginInstance(PluginInstance<T> instance) {
        this.pluginInstances.add(instance);
        notifyObservers();
    }

    public void addPluginInstance(int index) throws ConfigurationException {
        if (index >= 0) {
            List<PluginInstanceFactory<?>> pluginTypes = this.pluginManager
                    .getPluginTypes(this.clazz);
            if (index < pluginTypes.size()) {
                try {
                    PluginInstanceFactory<?> pluginType = pluginTypes
                            .get(index);
                    @SuppressWarnings("unchecked")
                    PluginInstance<T> instanceWrapper = (PluginInstance<T>) pluginType
                            .createInstance();
                    addPluginInstance(instanceWrapper);
                } catch (Exception ex) {
                    Logger.getLogger(PluginInstanceManager.class.getName())
                            .log(Level.SEVERE, null, ex);
                    throw new ConfigurationException(
                            "Unable to create plugin instance", ex);
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