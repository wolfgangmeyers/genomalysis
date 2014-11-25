/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.genomalysis.proteintools.standard;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
@Documentation("RegexFilter: this filter uses standard regular expressions for mining specific patterns out of protein or DNA sequences. Some basic examples of regular expressions that will match amino acid patterns in proteins are as follows: \n \nExpression ---> What it matches \n \nC ---> Cysteine residues \n \nCARWV ---> The exact sequence “CARWV” \n \nG\\w{8}R ---> Any sequence where a glycine is followed by 8 residues then an arginine \n \nC\\w{2,10}C ---> Any sequence where there are 2 to 10 residues between two cysteines \n \nFor a more detailed discussion of how to construct expressions see “A Primer on Regex” at the end of the filters documentation accessed through the button below.")
@Author(Name = "Wolfgang Meyers", EmailAddress = "wolfgangmeyers@gmail.com")
@Configurator(GenericConfigurator.class)
public class RegexFilter implements IProteinSequenceFilter {

    private Pattern pattern;
    private int minimumOccurrences = 1;
    private int maximumOccurrences = 10;

    @PropertyGetter(PropertyName = "Minimum Occurrences")
    public int getMinimumOccurrences() {
        return this.minimumOccurrences;
    }

    @PropertySetter(PropertyName = "Minimum Occurrences")
    public void setMinimumOccurrences(int value) {
        this.minimumOccurrences = value;
    }

    @PropertyGetter(PropertyName = "Regular Expression Pattern")
    public Pattern getPattern() {
        return pattern;
    }

    @PropertySetter(PropertyName = "Regular Expression Pattern")
    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }

    public void initialize() throws InitializationException {
        if (this.pattern == null) {
            throw new InitializationException(
                    new String[] { "Regular expression pattern for Regex Filter cannot be null" });
        }
    }

    public boolean filterProteinSequence(ProteinSequence sequence) {
        int count = 0;
        Matcher matcher = pattern.matcher(sequence.getData());
        while (matcher.find()) {
            count++;
        }
        return count >= minimumOccurrences && count <= maximumOccurrences;
    }

    @PropertyGetter(PropertyName = "Maximum Occurrences")
    public int getMaximumOccurrences() {
        return maximumOccurrences;
    }

    @PropertySetter(PropertyName = "Maximum Occurrences")
    public void setMaximumOccurrences(int maximumOccurrences) {
        this.maximumOccurrences = maximumOccurrences;
    }

}
