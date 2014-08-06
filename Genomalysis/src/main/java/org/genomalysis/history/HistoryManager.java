package org.genomalysis.history;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.genomalysis.plugin.PluginInstance;
import org.genomalysis.plugin.PluginInstanceSerializer;
import org.genomalysis.plugin.PluginManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class HistoryManager {

    private int limit;
    private File historyFolder = new File("history");
    private PluginManager pluginManager;
    private Gson gson;

    private File getHistoryFolder() {
        if (!historyFolder.exists()) {
            historyFolder.mkdir();
        }
        return historyFolder;
    }

    private File getHistoryFile() throws IOException {
        File historyFile = new File(getHistoryFolder(),
                "filter-execution.history");
        if (!historyFile.exists()) {
            historyFile.createNewFile();
        }
        return historyFile;
    }

    private void saveFilterExecutionHistory(List<FilterExecution> history)
            throws IOException {
        File historyFile = getHistoryFile();
        FileUtils.write(historyFile, gson.toJson(history));
    }

    public List<FilterExecution> getFilterExecutionHistory() throws IOException {
        File historyFile = getHistoryFile();
        String data = FileUtils.readFileToString(historyFile);
        List<FilterExecution> history = null;
        try {
            Type t = new TypeToken<List<FilterExecution>>() {
            }.getType();
            history = gson.<List<FilterExecution>> fromJson(data, t);
            if (history == null) {
                throw new RuntimeException();
            }
            return new ArrayList<FilterExecution>(history);
        } catch (Exception e) {
            e.printStackTrace();
            history = new ArrayList<FilterExecution>();
            saveFilterExecutionHistory(history);
            return history;
        }
    }

    public HistoryManager(PluginManager pluginManager, int limit) {
        this.pluginManager = pluginManager;
        this.limit = limit;
        this.gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd hh:mm:ss")
                .registerTypeAdapter(PluginInstance.class,
                        new PluginInstanceSerializer(pluginManager)).create();
    }

    public void addFilterExecution(FilterExecution execution) {
        try {
            List<FilterExecution> history = getFilterExecutionHistory();
            history.add(0, execution);
            if (history.size() > limit) {
                history.remove(history.size() - 1);
            }
            saveFilterExecutionHistory(history);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
