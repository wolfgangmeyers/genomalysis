package org.genomalysis.plugin.script;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.genomalysis.plugin.IPluginFoundCallback;
import org.genomalysis.plugin.PluginInstanceFactory;

public class ScriptManager {

    private Map<String, ScriptPlugin> plugins = new HashMap<String, ScriptPlugin>();

    public void registerScriptPlugin(ScriptPlugin scriptPlugin) {
        String extension = scriptPlugin.extension();
        plugins.put(extension, scriptPlugin);
    }

    public void loadScripts(List<Class<?>> pluginTypes,
            IPluginFoundCallback callback) {
        File scripts = new File("scripts");
        if (!scripts.exists()) {
            scripts.mkdir();
        }
        File[] contents = scripts.listFiles();
        for (File file : contents) {
            if (!file.isDirectory()) {
                String extension = FilenameUtils.getExtension(file.getName());
                ScriptPlugin plugin = plugins.get(extension);
                if (plugin != null) {
                    try {
                        String script = FileUtils.readFileToString(file);
                        Class<?> pluginType = plugin.discoverPluginType(script,
                                pluginTypes);
                        if (pluginType != null) {
                            PluginInstanceFactory<?> factory = plugin
                                    .createInstanceFactory(script, pluginType);
                            callback.pluginFound(pluginType, factory);
                        }
                    } catch (IOException | ScriptException e) {
                        System.out.println("Unable to load script "
                                + file.getName());
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
