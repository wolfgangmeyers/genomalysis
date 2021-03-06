/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.genomalysis.proteintools.standard;

import org.genomalysis.plugin.configuration.annotations.Author;
import org.genomalysis.plugin.configuration.annotations.Configurator;
import org.genomalysis.plugin.configuration.annotations.Documentation;
import org.genomalysis.plugin.configuration.annotations.PropertyGetter;
import org.genomalysis.plugin.configuration.annotations.PropertySetter;
import org.genomalysis.plugin.configuration.dialogs.GenericConfigurator;
import org.genomalysis.proteintools.IProteinSequenceFilter;
import org.genomalysis.proteintools.InitializationException;
import org.genomalysis.proteintools.ProteinSequence;

/**
 *
 * @author ameyers
 */
@Documentation("Sequence Length Filter:  "
        + "This filter only accepts sequences that are "
        + "between the specified minimum and maximum lengths.")
@Author(Name = "Wolfgang Meyers", EmailAddress = "wolfgangmeyers@gmail.com")
@Configurator(GenericConfigurator.class)
public class SequenceLengthFilter implements IProteinSequenceFilter {

    private int minLength;
    private int maxLength;

    public boolean filterProteinSequence(ProteinSequence sequence) {
        return sequence.getLength() >= this.minLength
                && sequence.getLength() <= this.maxLength;
    }

    @PropertyGetter(PropertyName = "Minimum Length")
    public int getMinLength() {
        return minLength;
    }

    @PropertySetter(PropertyName = "Minimum Length")
    public void setMinLength(int minLength) {
        if (minLength <= maxLength) {
            this.minLength = minLength;
        }
    }

    @PropertyGetter(PropertyName = "Maximum Length")
    public int getMaxLength() {
        return maxLength;
    }

    @PropertySetter(PropertyName = "Maximum Length")
    public void setMaxLength(int maxLength) {
        if (maxLength >= this.minLength) {
            this.maxLength = maxLength;
        }
    }

    public void initialize() throws InitializationException {
        // no need to initialize, really
    }
}
