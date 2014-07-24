package org.genomalysis.proteintools.signalp;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;

public class SignalPOutputParser {

    public static void main(String[] args)
            throws Exception {
        if (args.length != 1) {
            System.out.println("Wrong number of arguments");
            return;
        }
        File file = new File(args[0]);
        if (!(file.exists())) {
            System.out.println("File does not exist.");
            return;
        }

        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        PrintWriter pw = new PrintWriter(bout);

        ProcessBuilder builder = new ProcessBuilder(new String[]{"signalp", "-t", "euk", args[0]});
        Process process = builder.start();
        InputStream processOutput = process.getInputStream();
        InputStreamReader reader1 = new InputStreamReader(processOutput);
        BufferedReader reader2 = new BufferedReader(reader1);
        String line = null;
        while ((line = reader2.readLine()) != null) {
            pw.write(line + "\n");
        }
        pw.flush();
        ByteArrayInputStream bin = new ByteArrayInputStream(bout.toByteArray());

        SignalPOutputParser parser = new SignalPOutputParser();
        SignalPOutputParserResult result = parser.getResults(bin);

        System.out.println("Max c: " + result.getMaxC());
        System.out.println("Max s: " + result.getMaxS());
        System.out.println("Max y: " + result.getMaxY());
    }

    public SignalPOutputParserResult getResults(InputStream signalPOutput) throws Exception {
        SignalPOutputParserResult result = new SignalPOutputParserResult();
        boolean resultsFound = false;

        InputStreamReader reader1 = new InputStreamReader(signalPOutput);

        BufferedReader reader2 = new BufferedReader(reader1);

        StringBuffer buffer = new StringBuffer();

        String line = null;
        while (!resultsFound && (line = reader2.readLine()) != null) {
            buffer.append(line + "\n");
            if (line.equals("# Measure  Position  Value  Cutoff  signal peptide?")) {
                String maxCLine = reader2.readLine();
                String maxYLine = reader2.readLine();
                String maxSLine = reader2.readLine();
                if ((maxCLine != null) && (maxYLine != null) && (maxSLine != null)) {
                    resultsFound = true;

                    SignalPVariable maxC = parseVariable(maxCLine);
                    SignalPVariable maxY = parseVariable(maxYLine);
                    SignalPVariable maxS = parseVariable(maxSLine);
                    result.setMaxC(maxC);
                    result.setMaxS(maxS);
                    result.setMaxY(maxY);
                }
            }
        }

        if (!(resultsFound)) {
            System.out.println(buffer.toString());
            throw new Exception("Input for SignalP was not valid");
        }
        return result;
    }

    private SignalPVariable parseVariable(String data) {
        String[] parts = data.split("\\s+");
        SignalPVariable result = new SignalPVariable(Double.parseDouble(parts[4]), Double.parseDouble(parts[5]));
        return result;
    }
}