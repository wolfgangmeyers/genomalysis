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
@Documentation("The Regex Filter uses regular expressions to find"
+ " patterns in protein sequences. Set the Minimum Occurrences property"
        + " to the minimum number of times that the specified regular"
        + " expressions pattern must appear in the sequence.")
@Author(Name="Wolfgang Meyers", EmailAddress="wolfgangmeyers@gmail.com")
@Configurator(GenericConfigurator.class)
public class RegexFilter implements IProteinSequenceFilter{

    private Pattern pattern;
    private int minimumOccurrences = 1;
    private int maximumOccurrences = Integer.MAX_VALUE;
    
    @PropertyGetter(PropertyName="Minimum Occurrences")
    public int getMinimumOccurrences(){
        return this.minimumOccurrences;
    }
    
    @PropertySetter(PropertyName="Minimum Occurrences")
    public void setMinimumOccurrences(int value){
        this.minimumOccurrences = value;
    }
    
    @PropertyGetter(PropertyName="Regular Expression Pattern")
    public Pattern getPattern(){
        return pattern;
    }
    
    @PropertySetter(PropertyName="Regular Expression Pattern")
    public void setPattern(Pattern pattern){
        this.pattern = pattern;
    }
    
    public void initialize() throws InitializationException {
        if(this.pattern == null){
            throw new InitializationException(new String[]{"Regular expression pattern for Regex Filter cannot be null"});
        }
    }

    public boolean filterProteinSequence(ProteinSequence sequence) {
        int count = 0;
        Matcher matcher = pattern.matcher(sequence.getData());
        while(matcher.find()){
            count++;
        }
        return count >= minimumOccurrences && count <= maximumOccurrences;
    }

    @PropertyGetter(PropertyName="Maximum Occurrences")
    public int getMaximumOccurrences() {
        return maximumOccurrences;
    }

    @PropertySetter(PropertyName="Maximum Occurrences")
    public void setMaximumOccurrences(int maximumOccurrences) {
        this.maximumOccurrences = maximumOccurrences;
    }

}
