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

@Documentation("AndFilter contains a list of IProteinSequenceFilters. In order for AndFilter to pass, each one of the contained filters must pass.")
@Author(Name="Wolfgang Meyers", EmailAddress="wolfgangmeyers@gmail.com")
@Configurator(GenericConfigurator.class)
public class AndFilter
  implements IProteinSequenceFilter
{
  private List<IProteinSequenceFilter> filters;

  public void initialize()
    throws InitializationException
  {
    if (this.filters != null)
      for (Iterator i$ = this.filters.iterator(); i$.hasNext(); ) { IProteinSequenceFilter filter = (IProteinSequenceFilter)i$.next();
        if (filter == null)
          throw new InitializationException(new String[] { "AndFilter: Some of my filters were null. Make sure to \"Configure\" each\n one before you run this filter." });

        filter.initialize();
      }
    else
      throw new InitializationException(new String[] { "AndFilter: Filters cannot be null.\n Please configure filters before running." });
  }

  public boolean filterProteinSequence(ProteinSequence sequence)
  {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @PropertyGetter(PropertyName="Filters")
  public List<IProteinSequenceFilter> getFilters() {
    return this.filters;
  }

  @PropertySetter(PropertyName="Filters")
  public void setFilters(List<IProteinSequenceFilter> filters) {
    this.filters = filters;
  }
}