package org.genomalysis.proteintools;

import org.genomalysis.plugin.configuration.annotations.Documentation;

@Documentation("Represents the textual output of a diagnostic")
public class ProteinDiagnosticTextElement {
    private String name;
    private String text;

    public ProteinDiagnosticTextElement() {
    }

    public ProteinDiagnosticTextElement(String name, String text) {
        this.name = name;
        this.text = text;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }
}