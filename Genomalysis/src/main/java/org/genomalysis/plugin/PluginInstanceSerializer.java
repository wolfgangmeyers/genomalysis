package org.genomalysis.plugin;

import java.io.Serializable;
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class PluginInstanceSerializer implements
        JsonDeserializer<PluginInstance<?>>, JsonSerializer<PluginInstance<?>> {

    private final Gson gson = new Gson();
    private PluginManager pluginManager;
    
    public PluginInstanceSerializer(PluginManager pluginManager) {
        this.pluginManager = pluginManager;
    }

    @Override
    public JsonElement serialize(PluginInstance<?> p, Type t,
            JsonSerializationContext ctx) {
        JsonObject result = new JsonObject();

        Object pluginInstance = p.getPluginInstance();
        if (pluginInstance instanceof Serializable) {
            result.addProperty("instanceData",
                    gson.toJson(p.getPluginInstance()));
            result.addProperty("className", p.getPluginInstance().getClass()
                    .getName());
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
                Class<?> instanceClass = (Class<?>) Class.forName(className);
                Object instance = null;
                if (obj.has("instanceData")) {
                    String instanceData = obj.get("instanceData").getAsString();
                    instance = gson.fromJson(instanceData, instanceClass);
                } else {
                    try {
                        instance = instanceClass.newInstance();
                    } catch (InstantiationException | IllegalAccessException e) {
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
                throw new RuntimeException(
                        "className or factoryName is required");
            }

        } catch (Exception e) {
            throw new JsonParseException(e);
        }
    }
}
