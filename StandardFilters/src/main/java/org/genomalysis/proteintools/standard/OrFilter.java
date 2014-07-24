package org.genomalysis.proteintools.standard;

import java.util.Iterator;
import java.util.List;

import org.genomalysis.plugin.configuration.annotations.Author;
import org.genomalysis.plugin.configuration.annotations.Configurator;
import org.genomalysis.plugin.configuration.annotations.Documentation;
import org.genomalysis.plugin.configuration.annotations.PropertyGetter;
import org.genomalysis.plugin.configuration.annotations.PropertySetter;
import org.genomalysis.plugin.configuration.dialogs.GenericConfigurator;
import org.genomalysis.proteintools.IProteinSequenceFilter;
import org.genomalysis.proteintools.InitializationException;
import org.genomalysis.proteintools.ProteinSequence;

@Documentation("OrFilter contains a list of IProteinSequenceFilters. When this filter is invoked, it returns true if at least one of the contained filters returns true.")
@Author(Name="Wolfgang Meyers", EmailAddress="wolfgangmeyers@gmail.com")
@Configurator(GenericConfigurator.class)
public class OrFilter
  implements IProteinSequenceFilter
{
  private List<IProteinSequenceFilter> filters;

  @PropertyGetter(PropertyName="Filters")
  public List<IProteinSequenceFilter> getFilters()
  {
    return this.filters;
  }

  @PropertySetter(PropertyName="Filters")
  public void setFilters(List<IProteinSequenceFilter> filters) {
    this.filters = filters;
  }

  public void initialize() throws InitializationException
  {
    if (this.filters != null)
      for (Iterator i$ = this.filters.iterator(); i$.hasNext(); ) { IProteinSequenceFilter filter = (IProteinSequenceFilter)i$.next();
        if (filter == null)
          throw new InitializationException(new String[] { "OrFilter: Some of my filters were null. Make sure to \"Configure\" each\n one before you run this filter." });

        filter.initialize();
      }
    else
      throw new InitializationException(new String[] { "OrFilter: Filters cannot be null.\n Please configure filters before running." });
  }

  public boolean filterProteinSequence(ProteinSequence sequence)
  {
    boolean pass = false;
    if ((this.filters == null) || (this.filters.size() == 0))
      pass = true;
    else
      for (Iterator i$ = this.filters.iterator(); i$.hasNext(); ) { IProteinSequenceFilter filter = (IProteinSequenceFilter)i$.next();
        pass = (pass) || (filter.filterProteinSequence(sequence));
      }

    return pass;
  }
}