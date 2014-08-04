package org.genomalysis.clustalw;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.genomalysis.proteintools.ProteinSequence;

public class ClustalWInterface {
	public ClustalWOutput runClustal(ProteinSequence a, ProteinSequence b)
			throws IOException {
		ClustalWOutput result = null;
		File tmpdir = new File("tmp");
		if (!tmpdir.exists()) {
			tmpdir.mkdir();
		}
		File outfile = new File(tmpdir, "csinput.fsa");
		if (!(outfile.exists())) {
			outfile.createNewFile();
		}

		FileOutputStream fout = null;
		FileInputStream fin = null;
		try {
			fout = new FileOutputStream(outfile);
			PrintWriter pw = new PrintWriter(fout);
			pw.write(a.toString());
			pw.write(b.toString());
			pw.flush();
			fout.flush();
			fout.close();
			fout = null;

			ProcessBuilder builder = new ProcessBuilder(new String[] {
					"clustalo", "-infile=tmp/csinput.fsa",
					"-outfile=tmp/csoutput.aln" });
			Process p = builder.start();
			p.waitFor();

			File infile = new File("tmp/csoutput.aln");
			fin = new FileInputStream(infile);
			InputStreamReader reader1 = new InputStreamReader(fin);
			BufferedReader reader2 = new BufferedReader(reader1);
			String line = null;
			ArrayList lines = new ArrayList();
			while ((line = reader2.readLine()) != null)
				lines.add(line);

			result = parseOutput(lines, a.getLength());

			outfile.delete();
			infile.delete();
		} catch (InterruptedException ex) {
			Logger.getLogger(ClustalWInterface.class.getName()).log(
					Level.SEVERE, null, ex);
		} finally {
			if (fout != null)
				fout.close();

			if (fin != null)
				fin.close();
		}

		return result;
	}

	private ClustalWOutput parseOutput(List<String> lines, int sequenceLength) {
		String line;
		int identity = 0;
		int strong = 0;
		int weak = 0;

		StringBuffer raw_output = new StringBuffer();
		for (Iterator i$ = lines.iterator(); i$.hasNext();) {
			line = (String) i$.next();
			raw_output.append(line + "\n");
		}

		for (int i = 5; i < lines.size(); i += 4) {
			line = (String) lines.get(i);
			for (int j = 0; j < line.length(); ++j) {
				char symbol = line.charAt(j);
				switch (symbol) {
				case '*':
					++identity;
					break;
				case ':':
					++strong;
					break;
				case '.':
					++weak;
				}
			}
		}

		double identity_percent = identity / sequenceLength;
		double strong_percent = strong / sequenceLength;
		double weak_percent = weak / sequenceLength;
		double nomatch_percent = 1D - identity_percent + strong_percent
				+ weak_percent;
		return new ClustalWOutput(raw_output.toString(), identity_percent,
				strong_percent, weak_percent, nomatch_percent);
	}
}