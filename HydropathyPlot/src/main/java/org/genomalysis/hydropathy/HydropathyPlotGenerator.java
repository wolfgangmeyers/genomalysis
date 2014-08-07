package org.genomalysis.hydropathy;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.util.List;

import org.genomalysis.plugin.configuration.annotations.Author;
import org.genomalysis.plugin.configuration.annotations.Documentation;
import org.genomalysis.plugin.configuration.annotations.PropertyGetter;
import org.genomalysis.plugin.configuration.annotations.PropertySetter;
import org.genomalysis.proteintools.IProteinDiagnosticsTool;
import org.genomalysis.proteintools.InitializationException;
import org.genomalysis.proteintools.ProteinDiagnosticImageElement;
import org.genomalysis.proteintools.ProteinDiagnosticResult;
import org.genomalysis.proteintools.ProteinSequence;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.DefaultXYDataset;

@Documentation("Uses QN Plot to generate hydropathy plots for given Protein Sequences. QN Plot can be found at http://quies.net/java/math/plot/\nUses the Kyte-Doolittle hydrophobicity scale for predicting protein hydropathicity\nReference: Kyte J and Doolittle RF: A simple method for displaying the hydropathic character of a protien. J Mol Biol 157:105, 1982.")
@Author(EmailAddress = "wolfgangmeyers@gmail.com", Name = "Wolfgang Meyers")
public class HydropathyPlotGenerator implements IProteinDiagnosticsTool {
    private int windowSize;
    private String plotName;
    private Color backgroundcolor;

    public HydropathyPlotGenerator() {
        this.windowSize = 7;
        this.plotName = "Hydropathy Profile";
        this.backgroundcolor = Color.white;
    }

    public void initialize() throws InitializationException {
    }

    public ProteinDiagnosticResult runDiagnostics(
            List<ProteinSequence> sequences) {
        try {
            ProteinDiagnosticResult result = new ProteinDiagnosticResult();

            DefaultXYDataset dataset = new DefaultXYDataset();

            for (ProteinSequence sequence : sequences) {
                double[][] data = new double[2][sequence.getLength()];
                for (int i = 0; i < sequence.getLength(); ++i) {
                    data[1][i] = HydropathyPlotHelper.averageHydrophobicity(
                            sequence.getData(), i, this.windowSize);
                    data[0][i] = i;
                }
                dataset.addSeries(sequence.getName(), data);
            }

            JFreeChart chart = ChartFactory.createXYLineChart(this.plotName,
                    null, null, dataset, PlotOrientation.VERTICAL, true, false,
                    false);

            XYPlot plot = (XYPlot) chart.getPlot();
            plot.setBackgroundPaint(this.backgroundcolor);
            plot.setRangeZeroBaselinePaint(Color.DARK_GRAY);
            plot.setRangeZeroBaselineStroke(new BasicStroke(2F));
            plot.setRangeZeroBaselineVisible(true);
            plot.getDomainAxis()
                    .setTickLabelFont(Font.decode("Arial-PLAIN-15"));

            BufferedImage img = chart.createBufferedImage(800, 600);
            result.getGraphicResults().add(
                    new ProteinDiagnosticImageElement(this.plotName + "("
                            + this.windowSize + ")", img));
            return result;
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    @PropertyGetter(PropertyName = "Window Size")
    public int getWindowSize() {
        return this.windowSize;
    }

    @PropertySetter(PropertyName = "Window Size")
    public void setWindowSize(int windowSize) {
        this.windowSize = windowSize;
    }

    @PropertyGetter(PropertyName = "Plot Name")
    public String getPlotName() {
        return this.plotName;
    }

    @PropertySetter(PropertyName = "Plot Name")
    public void setPlotName(String plotName) {
        this.plotName = plotName;
    }

    @PropertyGetter(PropertyName = "Background Color")
    public Color getBackgroundcolor() {
        return this.backgroundcolor;
    }

    @PropertySetter(PropertyName = "Background Color")
    public void setBackgroundcolor(Color backgroundcolor) {
        this.backgroundcolor = backgroundcolor;
    }
}