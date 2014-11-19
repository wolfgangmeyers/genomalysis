package org.genomalysis.predisi;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

import jspp.SearchMatrix;
import jspp.SignalPeptidePredictor;

import org.genomalysis.plugin.configuration.annotations.Author;
import org.genomalysis.plugin.configuration.annotations.Configurator;
import org.genomalysis.plugin.configuration.annotations.Documentation;
import org.genomalysis.plugin.configuration.dialogs.GenericConfigurator;
import org.genomalysis.proteintools.IProteinSequenceFilter;
import org.genomalysis.proteintools.InitializationException;
import org.genomalysis.proteintools.ProteinSequence;
import org.genomalysis.plugin.configuration.VoidConfigurator;

@Documentation("SecSigFilterGramPos: This filter uses the PrediSi Gram positive algorithm to search for secretion signals in protein sequences from Gram positive bacteria. The algorithm was trained against 236 Gram positive bacterial protein sequences containing secretion signals and is reported as having an 88.14% success rate at predicting Gram positive secretion signals when tested against control sequences.\n \nThis is a pass-fail filter and is not configurable: if a peptide has both a secretion signal and an associated cleavage site, then it passes the filter.\n \n \nReference: \n \nHiller K, Grote A, Scheer M, Münch R, Jahn D. PrediSi: prediction of signal peptides and their cleavage positions. Nucleic Acids Res. 2004 Jul 1;32(Web Server issue):W375-9.")

@Author(Name = "Wolfgang Meyers", EmailAddress = "wolfgangmeyers@gmail.com")
@Configurator(VoidConfigurator.class)
public class SecSigFilterGramPos implements IProteinSequenceFilter {

    private SignalPeptidePredictor pd;

    @Override
    public void initialize() throws InitializationException {
        InputStream ins = null;
        ObjectInputStream oin = null;

        try {
            // TODO: make this configurable?
            ins = getClass().getResourceAsStream("gramp.smx");
            oin = new ObjectInputStream(ins);
            SearchMatrix smp = (SearchMatrix) oin.readObject();
            pd = new SignalPeptidePredictor(smp);
        } catch (Exception ex) {
            throw new InitializationException(ex.getMessage());
        } finally {
            if (oin != null) {
                try {
                    oin.close();
                } catch (IOException ignored) {
                }
            }
            if (ins != null) {
                try {
                    ins.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

    @Override
    public boolean filterProteinSequence(ProteinSequence sequence) {
        pd.predictEnhancedPosition(sequence.getData());
        return pd.isSignalPeptide();
    }

}
