/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.genomalysis.proteintools;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;

import org.genomalysis.plugin.configuration.annotations.Author;
import org.genomalysis.plugin.configuration.annotations.Documentation;

@Documentation("This interface was designed to describe a class that can load large numbers of protein (or DNA) sequences through an iterator. Implementing classes should also be able to write from such an iterator.\n\nMethods:\nIterator<ProteinSequence> readSequences(InputStream inFile) throws IOException;\nvoid writeSequences(OutputStream outFile, Iterator<ProteinSequence> data) throws IOException;")
@Author(Name = "Wolfgang Meyers", EmailAddress = "wolfgangmeyers@gmail.com")
public interface ISequenceIO {
    Iterator<ProteinSequence> readSequences(InputStream inFile)
            throws IOException;

    void writeSequences(OutputStream outFile, Iterator<ProteinSequence> data)
            throws IOException;

}
