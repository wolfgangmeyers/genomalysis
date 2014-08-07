package org.genomalysis.control;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.genomalysis.proteintools.ISequenceIO;
import org.genomalysis.proteintools.ProteinSequence;
import org.genomalysis.proteintools.SequenceIOImpl;

public class SequenceCacheControl implements IObservable {

    Map<String, List<ProteinSequence>> cache = new HashMap<>();
    private ISequenceIO io = SequenceIOImpl.getDefaultIO();
    private String activeCache = "default";
    private EventSupport eventSupport = new EventSupport();

    public SequenceCacheControl() {
        clearAll();
    }

    public void createNewCache(String name) throws Exception {
        if (this.cache.get(name) != null) {
            throw new Exception("Cache with the same name already exists");
        }
        this.cache.put(name, new ArrayList<ProteinSequence>());
        eventSupport.notifyObservers();
    }

    public void renameCache(String oldname, String newname) throws Exception {
        if (this.cache.get(newname) != null) {
            throw new Exception("Cannot rename cache " + oldname + " to "
                    + newname
                    + " because a cache with that name already exists");
        }

        if (this.cache.get(oldname) == null) {
            throw new Exception("Cache named " + oldname
                    + " not found. Please file a bug report");
        }
        List<ProteinSequence> sequences = this.cache.get(oldname);
        this.cache.remove(oldname);
        this.cache.put(newname, sequences);
        if (oldname.equals(this.activeCache)) {
            this.activeCache = newname;
        }
        eventSupport.notifyObservers();
    }

    public void clearAll() {
        this.cache.clear();
        this.cache.put("default", new ArrayList<ProteinSequence>());
        this.activeCache = "default";
        eventSupport.notifyObservers();
    }

    public String[] getCacheNames() {
        Set<String> nameset = this.cache.keySet();
        String[] result = new String[nameset.size()];
        int i = 0;
        Iterator<String> nameIterator = nameset.iterator();
        while (nameIterator.hasNext()) {
            result[(i++)] = (nameIterator.next());
        }
        return result;
    }

    public List<ProteinSequence> getSequences(String name) {
        return this.cache.get(name);
    }

    public void removeFromCache(String name) {
        this.cache.remove(name);
        if (this.cache.size() == 0) {
            clearAll();
        }
        eventSupport.notifyObservers();
    }

    public void addToCache(String name, List<ProteinSequence> sequences) {
        List<ProteinSequence> cacheSequences = this.cache.get(name);
        if (cacheSequences == null) {
            cacheSequences = new ArrayList<ProteinSequence>();
            this.cache.put(name, cacheSequences);
        }
        cacheSequences.addAll(sequences);
        eventSupport.notifyObservers();
    }

    public void readFromFile(String name, File inFile) throws IOException {
        FileInputStream in = null;
        try {
            in = new FileInputStream(inFile);
            Iterator<ProteinSequence> sequences = this.io.readSequences(in);
            List<ProteinSequence> lst = new ArrayList<ProteinSequence>();
            boolean EOF = false;
            do {
                if (!(sequences.hasNext())) {
                    EOF = true;
                } else {
                    lst.add(sequences.next());
                }
            } while (!EOF && lst.size() <= 1000);

            this.cache.put(name, lst);
            eventSupport.notifyObservers();
        } finally {
            if (in != null) {
                in.close();
            }
        }
    }

    public void saveToFile(String name, File outFile) throws IOException {
        List<ProteinSequence> sequences = this.cache.get(name);
        if (sequences == null) {
            throw new IOException("Sequence cache with name " + name
                    + " does not exist");
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

    public String getActiveCacheName() {
        return this.activeCache;
    }

    public void setActiveCacheName(String name) {
        if (this.cache.get(name) != null) {
            this.activeCache = name;
        }
    }

    @Override
    public void addObserver(IObserver observer) {
        eventSupport.addObserver(observer);
    }

    @Override
    public void removeObserver(IObserver observer) {
        eventSupport.addObserver(observer);
    }
}