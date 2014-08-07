package org.genomalysis.history;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.genomalysis.plugin.PluginInstance;
import org.genomalysis.proteintools.IProteinSequenceFilter;

public class FilterExecution implements Serializable {

    private static final long serialVersionUID = 1L;

    private String infile;

    private String outfile;

    private Date date;

    private List<PluginInstance<IProteinSequenceFilter>> filters;

    public String getInfile() {
        return infile;
    }

    public void setInfile(String infile) {
        this.infile = infile;
    }

    public String getOutfile() {
        return outfile;
    }

    public void setOutfile(String outfile) {
        this.outfile = outfile;
    }

    public List<PluginInstance<IProteinSequenceFilter>> getFilters() {
        return filters;
    }

    public void setFilters(List<PluginInstance<IProteinSequenceFilter>> filters) {
        this.filters = filters;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public FilterExecution(String infile, String outfile,
            List<PluginInstance<IProteinSequenceFilter>> filters) {
        this.infile = infile;
        this.outfile = outfile;
        this.date = new Date();
        this.filters = filters;
    }

    public FilterExecution() {

    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((filters == null) ? 0 : filters.hashCode());
        result = prime * result + ((infile == null) ? 0 : infile.hashCode());
        result = prime * result + ((outfile == null) ? 0 : outfile.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        FilterExecution other = (FilterExecution) obj;
        if (filters == null) {
            if (other.filters != null)
                return false;
        } else if (!filters.equals(other.filters))
            return false;
        if (infile == null) {
            if (other.infile != null)
                return false;
        } else if (!infile.equals(other.infile))
            return false;
        if (outfile == null) {
            if (other.outfile != null)
                return false;
        } else if (!outfile.equals(other.outfile))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "FilterExecution [infile=" + infile + ", outfile=" + outfile
                + ", date=" + date + ", filters=" + filters + "]";
    }

}
