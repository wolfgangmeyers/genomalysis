package org.genomalysis.control;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.genomalysis.proteintools.ISequenceIO;
import org.genomalysis.proteintools.ProteinSequence;
import org.genomalysis.proteintools.SequenceIOImpl;

public class SequenceCacheControl {

    Map<String, List<ProteinSequence>> cache = new HashMap();
    private ISequenceIO io = SequenceIOImpl.getDefaultIO();
    private List<IObserver> observers = new ArrayList();
    private String activeCache = "default";

    public SequenceCacheControl() {
        clearAll();
    }

    public void createNewCache(String name) throws Exception {
        if (this.cache.get(name) != null) {
            throw new Exception("Cache with the same name already exists");
        }
        this.cache.put(name, new ArrayList());
        notifyObservers();
    }

    public void renameCache(String oldname, String newname) throws Exception {
        if (this.cache.get(newname) != null) {
            throw new Exception("Cannot rename cache " + oldname + " to " + newname + " because a cache with that name already exists");
        }

        if (this.cache.get(oldname) == null) {
            throw new Exception("Cache named " + oldname + " not found. Please file a bug report");
        }
        List sequences = (List) this.cache.get(oldname);
        this.cache.remove(oldname);
        this.cache.put(newname, sequences);
        if (oldname.equals(this.activeCache)) {
            this.activeCache = newname;
        }
        notifyObservers();
    }

    public void clearAll() {
        this.cache.clear();
        this.cache.put("default", new ArrayList());
        this.activeCache = "default";
        notifyObservers();
    }

    public String[] getCacheNames() {
        Set nameset = this.cache.keySet();
        String[] result = new String[nameset.size()];
        int i = 0;
        Iterator nameIterator = nameset.iterator();
        while (nameIterator.hasNext()) {
            result[(i++)] = ((String) nameIterator.next());
        }
        return result;
    }

    public List<ProteinSequence> getSequences(String name) {
        return ((List) this.cache.get(name));
    }

    public void removeFromCache(String name) {
        this.cache.remove(name);
        if (this.cache.size() == 0) {
            clearAll();
        }
        notifyObservers();
    }

    public void addToCache(String name, List<ProteinSequence> sequences) {
        List cacheSequences = (List) this.cache.get(name);
        if (cacheSequences == null) {
            cacheSequences = new ArrayList();
            this.cache.put(name, cacheSequences);
        }
        cacheSequences.addAll(sequences);
        notifyObservers();
    }

    public void readFromFile(String name, File inFile) throws IOException {
        FileInputStream in = null;
        try {
            in = new FileInputStream(inFile);
            Iterator sequences = this.io.readSequences(in);
            List lst = new ArrayList();
            boolean EOF = false;
            do {
                if (!(sequences.hasNext())) {
                    EOF = true;
                }else{
                    lst.add(sequences.next());
                }
            } while (!EOF && lst.size() <= 1000);
            
            this.cache.put(name, lst);
            notifyObservers();
        } finally {
            if (in != null) {
                in.close();
            }
        }
    }

    public void saveToFile(String name, File outFile) throws IOException {
        List sequences = (List) this.cache.get(name);
        if (sequences == null) {
            throw new IOException("Sequence cache with name " + name + " does not exist");
        }
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(outFile);
            this.io.writeSequences(out, sequences.iterator());
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    public void addObserver(IObserver observer) {
        synchronized (this.observers) {
            if (!(this.observers.contains(observer))) {
                this.observers.add(observer);
            }
        }
    }

    public void removeObserver(IObserver observer) {
        synchronized (this.observers) {
            this.observers.remove(observer);
        }
    }

    private void notifyObservers() {
        System.out.println("SequenceCacheControl::notifyObservers");
        synchronized (this.observers) {
            for (Iterator i$ = this.observers.iterator(); i$.hasNext();) {
                IObserver observer = (IObserver) i$.next();
                observer.update();
            }
        }
    }

    public String getActiveCacheName() {
        return this.activeCache;
    }

    public void setActiveCacheName(String name) {
        if (this.cache.get(name) != null) {
            this.activeCache = name;
        }
    }
}