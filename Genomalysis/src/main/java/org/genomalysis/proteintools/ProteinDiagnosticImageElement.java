package org.genomalysis.proteintools;

import java.awt.Image;

import org.genomalysis.plugin.configuration.annotations.Documentation;

@Documentation("Represents the image output of a diagnostic")
public class ProteinDiagnosticImageElement {
    private String name;
    private Image image;

    public ProteinDiagnosticImageElement(String name, Image image) {
        this.name = name;
        this.image = image;
    }

    public ProteinDiagnosticImageElement() {
        this("untitled", null);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Image getImage() {
        return this.image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}