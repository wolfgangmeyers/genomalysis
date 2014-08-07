/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.genomalysis.proteintools;

import org.genomalysis.plugin.configuration.annotations.Author;
import org.genomalysis.plugin.configuration.annotations.Configurator;
import org.genomalysis.plugin.configuration.annotations.Documentation;
import org.genomalysis.plugin.configuration.dialogs.GenericConfigurator;

@Documentation("Interface for protein sequence filters. Implementing classes should be able to analyze protein sequences for certain attributes, and provide a pass/fail response. Filters can also check the environment for required resources in initialize() and throw an exception if resources are not found (like an executable program or internet connection).")
@Author(Name = "Wolfgang Meyers", EmailAddress = "wolfgangmeyers@gmail.com")
@Configurator(GenericConfigurator.class)
public interface IProteinSequenceFilter {
    void initialize() throws InitializationException;

    boolean filterProteinSequence(ProteinSequence sequence);
}
