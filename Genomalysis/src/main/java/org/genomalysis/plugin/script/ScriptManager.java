package org.genomalysis.plugin.script;

import java.io.File;

import org.genomalysis.proteintools.IProteinSequenceFilter;
import org.python.core.Py;
import org.python.core.PyClass;
import org.python.core.PyJavaClass;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.core.PySystemState;
import org.python.util.PythonInterpreter;

public class ScriptManager {
    
    
    
    public void initialize() {
        File scripts = new File("scripts");
        if (!scripts.exists()) {
            scripts.mkdir();
        }
        
    }
    
    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        File scripts = new File("scripts");
        if (!scripts.exists()) {
            scripts.mkdir();
        }
        PythonInterpreter i = new PythonInterpreter(null, new PySystemState());
        PySystemState state = Py.getSystemState();
        state.path.append(new PyString("scripts"));
        i.exec(
            "from org.genomalysis.proteintools import IProteinSequenceFilter\n\n" +
            "class Foo(IProteinSequenceFilter):\n" +
            "    def filterProteinSequence(sequence):\n" +
            "        return True\n");
        System.out.println(i.getLocals());
        System.out.println(i.get("IProteinSequenceFilter") instanceof PyJavaClass);
        System.out.println(i.get("Foo") instanceof PyJavaClass);
        PyClass clazz = (PyClass)i.get("Foo");
        
        Class<?> exp = (Class)clazz.__tojava__(Class.class);
        System.out.println(exp);
        exp.asSubclass(IProteinSequenceFilter.class);
       
        //System.out.println(exp.newInstance());
        
        PyObject o = clazz.__call__();
        Object o2 = o.__tojava__(IProteinSequenceFilter.class);
        System.out.println(o2 instanceof IProteinSequenceFilter);
        System.out.println(o2.getClass() == exp);
        System.out.println(exp.getCanonicalName());
        
        i.exec("from test import foobar\nresult = foobar('hello, world!')\n");
        System.out.println(i.get("result", String.class));
        
    }
    
}
