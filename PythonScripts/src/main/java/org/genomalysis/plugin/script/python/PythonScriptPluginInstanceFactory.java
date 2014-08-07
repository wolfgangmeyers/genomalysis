package org.genomalysis.plugin.script.python;

import org.genomalysis.plugin.PluginInstance;
import org.genomalysis.plugin.PluginInstanceFactory;
import org.python.core.PyClass;
import org.python.core.PyObject;
import org.python.core.PyString;

public class PythonScriptPluginInstanceFactory<T> implements
        PluginInstanceFactory<T> {

    private PyClass pyClass;
    private Class<T> pluginType;

    public PythonScriptPluginInstanceFactory(PyClass pyClass,
            Class<T> pluginType) {
        this.pyClass = pyClass;
        this.pluginType = pluginType;
    }

    @Override
    public String getName() {
        return pyClass.__name__;
    }

    @SuppressWarnings("unchecked")
    @Override
    public PluginInstance<T> createInstance() throws Exception {
        PyObject instance = pyClass.__call__();
        T result = (T) instance.__tojava__(pluginType);
        return new PluginInstance<T>(result, getName());
    }

    @Override
    public String getDocumentation() {
        PyString doc = (PyString) pyClass.getDoc();
        if (doc != null) {
            return doc.toString();
        }
        return null;
    }

}
