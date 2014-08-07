package org.genomalysis.plugin.configuration;

import java.util.HashMap;
import java.util.Map;

import org.genomalysis.plugin.PluginInstanceFactory;
import org.genomalysis.plugin.configuration.annotations.Author;
import org.genomalysis.plugin.configuration.annotations.Documentation;

public class DocumentationTable {
    private Map<String, String> innerTable;

    public DocumentationTable() {
        this.innerTable = new HashMap<>();
    }

    public void registerDocumentation(Class<?> clazz) {
        if (this.innerTable.get(clazz.getName()) == null) {
            Documentation doc = (Documentation) clazz
                    .getAnnotation(Documentation.class);
            Author auth = (Author) clazz.getAnnotation(Author.class);
            String classname = clazz.getName();

            StringBuffer buffer = new StringBuffer();
            if (auth == null) {
                buffer.append("Author not specified\n\n");
            } else {
                buffer.append("Author:  " + auth.Name() + "\n");
                buffer.append("Email:  " + auth.EmailAddress() + "\n\n");
            }
            if (doc == null)
                buffer.append("No documentation available");
            else
                buffer.append(doc.value());

            this.innerTable.put(classname, buffer.toString());
        }
    }

    public String getDocumentation(Class<?> clazz) {
        String classname = clazz.getName();
        String documentation = (String) this.innerTable.get(classname);
        if (documentation == null)
            documentation = "No documenation available";
        return documentation;
    }

    public String getDocumentation(PluginInstanceFactory<?> factory) {
        return factory.getDocumentation();
    }
}