package org.genomalysis.proteintools.standard;

import org.genomalysis.plugin.configuration.annotations.Author;
import org.genomalysis.plugin.configuration.annotations.Documentation;
import org.genomalysis.plugin.configuration.annotations.PropertyGetter;
import org.genomalysis.plugin.configuration.annotations.PropertySetter;
import org.genomalysis.proteintools.IProteinSequenceFilter;
import org.genomalysis.proteintools.InitializationException;
import org.genomalysis.proteintools.ProteinSequence;

@Documentation("This filter simply wraps another, and returns the opposite result of that filter. If the inner filter passes a sequence, this filter will not pass it. If the inner filter does not pass a sequence, this filter will pass it. I thought this might be useful for knowing which sequences didn't meet your filter criteria.")
@Author(Name="Wolfgang Meyers", EmailAddress="wolfgangmeyers@gmail.com")
public class NotFilter
  implements IProteinSequenceFilter
{
  private IProteinSequenceFilter innerFilter;

  public void initialize()
    throws InitializationException
  {
    if (this.innerFilter == null)
      throw new InitializationException(new String[] { "NotFilter: Inner Filter cannot be null" });
  }

  public boolean filterProteinSequence(ProteinSequence sequence)
  {
    return (!(this.innerFilter.filterProteinSequence(sequence)));
  }

  @PropertyGetter(PropertyName="Inner Filter")
  public IProteinSequenceFilter getInnerFilter() {
    return this.innerFilter;
  }

  @PropertySetter(PropertyName="Inner Filter")
  public void setInnerFilter(IProteinSequenceFilter innerFilter) {
    this.innerFilter = innerFilter;
  }
}