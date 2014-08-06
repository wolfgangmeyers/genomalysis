package org.genomalysis.plugin.script.python;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.genomalysis.plugin.PluginInstanceFactory;
import org.genomalysis.plugin.PluginUtil;
import org.genomalysis.plugin.script.ScriptException;
import org.genomalysis.plugin.script.ScriptPlugin;
import org.python.core.Py;
import org.python.core.PyClass;
import org.python.core.PyDictionary;
import org.python.core.PyList;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.core.PyStringMap;
import org.python.core.PySystemState;
import org.python.util.PythonInterpreter;

public class PythonScriptPlugin implements ScriptPlugin {

    public PythonScriptPlugin() {
        new PythonInterpreter(null, new PySystemState());
        Py.getSystemState().path.append(new PyString("scripts"));
    }
    
    @Override
    public String extension() {
        return "py";
    }

    private List<PyClass> getPythonClasses(String script) throws ScriptException {
        List<PyClass> result = new LinkedList<PyClass>();
        PythonInterpreter interpreter = new PythonInterpreter();
        interpreter.exec(script);
        PyStringMap locals = (PyStringMap)interpreter.getLocals();
        PyList keys = locals.keys();
        Iterator<?> i = keys.iterator();
        while (i.hasNext()) {
            String key = (String)i.next();
            Object obj = interpreter.get(key);
            if (obj instanceof PyClass) {
                PyClass pyclass = (PyClass)obj;
                result.add(pyclass);
            }
        }
        return result;
    }
    
    @Override
    public Class<?> discoverPluginType(String script, List<Class<?>> pluginTypes)
            throws ScriptException {
        try {
            List<PyClass> pyclasses = getPythonClasses(script);
            for (PyClass pyclass : pyclasses) {
                Class<?> clazz = (Class<?>)pyclass.__tojava__(Class.class);
                for (Class<?> pluginType : pluginTypes) {
                    if (pluginType != clazz && PluginUtil.implementsInterface(clazz, pluginType)) {
                        return pluginType;
                    }
                }
            }
            return null;
        } catch (Exception e) {
            throw new ScriptException(e);
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public PluginInstanceFactory<?> createInstanceFactory(String script, Class<?> pluginType)
            throws ScriptException {
        try {
            List<PyClass> pyclasses = getPythonClasses(script);
            for (PyClass pyclass : pyclasses) {
                Class<?> clazz = (Class<?>)pyclass.__tojava__(Class.class);
                if (clazz != pluginType && PluginUtil.implementsInterface(clazz, pluginType)) {
                    return new PythonScriptPluginInstanceFactory(pyclass, pluginType);
                }
            }
            return null;
        } catch (Exception e) {
            throw new ScriptException(e);
        }
    }

//  i.exec(
//      "from org.genomalysis.proteintools import IProteinSequenceFilter\n\n" +
//      "class Foo(IProteinSequenceFilter):\n" +
//      "    def filterProteinSequence(sequence):\n" +
//      "        return True\n");
//  System.out.println(i.getLocals());
//  System.out.println(i.get("IProteinSequenceFilter") instanceof PyJavaClass);
//  System.out.println(i.get("Foo") instanceof PyJavaClass);
//  PyClass clazz = (PyClass)i.get("Foo");
//  
//  Class<?> exp = (Class)clazz.__tojava__(Class.class);
//  System.out.println(exp);
//  exp.asSubclass(IProteinSequenceFilter.class);
// 
//  //System.out.println(exp.newInstance());
//  
//  PyObject o = clazz.__call__();
//  Object o2 = o.__tojava__(IProteinSequenceFilter.class);
//  System.out.println(o2 instanceof IProteinSequenceFilter);
//  System.out.println(o2.getClass() == exp);
//  System.out.println(exp.getCanonicalName());
//  
//  i.exec("from test import foobar\nresult = foobar('hello, world!')\n");
//  System.out.println(i.get("result", String.class));
//  
//}
//
}
