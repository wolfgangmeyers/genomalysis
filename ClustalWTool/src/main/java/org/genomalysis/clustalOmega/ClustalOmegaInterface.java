package org.genomalysis.clustalOmega;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;
import org.genomalysis.proteintools.ProteinSequence;

public class ClustalOmegaInterface {
    public ClustalOmegaOutput runClustal(ProteinSequence a, ProteinSequence b)
            throws IOException {
        ClustalOmegaOutput result = null;
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
                    "clustalo", "-i", "tmp/csinput.fsa", "-o", "tmp/csoutput.aln", "--outfmt=clustal", "-v", "--force"
                     });
            Process p = builder.start();
            p.waitFor();

            File infile = new File("tmp/csoutput.aln");
            List<String> lines = new ArrayList<>(FileUtils.readLines(infile));

            result = parseOutput(lines, a.getLength());

            outfile.delete();
            infile.delete();
        } catch (InterruptedException ex) {
            Logger.getLogger(ClustalOmegaInterface.class.getName()).log(
                    Level.SEVERE, null, ex);
        } finally {
            if (fout != null)
                fout.close();

            if (fin != null)
                fin.close();
        }

        return result;
    }

    private ClustalOmegaOutput parseOutput(List<String> lines, int sequenceLength) {
        int identity = 0;
        int strong = 0;
        int weak = 0;

        StringBuffer rawOutput = new StringBuffer();
        for (String line : lines) {
            rawOutput.append(line + "\n");
        }

        for (int i = 5; i < lines.size(); i += 4) {
            String line = lines.get(i);
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

        return new ClustalOmegaOutput(rawOutput.toString(), sequenceLength,
                identity, strong, weak);
    }
}