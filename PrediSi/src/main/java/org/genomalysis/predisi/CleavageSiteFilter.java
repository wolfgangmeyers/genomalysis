package org.genomalysis.predisi;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

import jspp.SearchMatrix;
import jspp.SignalPeptidePredictor;

import org.genomalysis.plugin.configuration.annotations.Author;
import org.genomalysis.plugin.configuration.annotations.Documentation;
import org.genomalysis.proteintools.IProteinSequenceFilter;
import org.genomalysis.proteintools.InitializationException;
import org.genomalysis.proteintools.ProteinSequence;

@Documentation("CleavageSiteFilter:   This filter uses the PrediSi library (http://www.predisi.de/home.html) to predict cleavage sites and secretion signals. Sequences without cleavage sites and secretion signals will not pass this filter.")
@Author(Name = "Wolfgang Meyers", EmailAddress = "wolfgangmeyers@gmail.com")
public class CleavageSiteFilter implements IProteinSequenceFilter {

	private SignalPeptidePredictor pd;

	@Override
	public void initialize() throws InitializationException {
		InputStream ins = null;
		ObjectInputStream oin = null;

		try {
			//TODO: make this configurable?
			ins = getClass().getResourceAsStream("eukarya.smx");
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
