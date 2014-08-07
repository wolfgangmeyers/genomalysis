/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.genomalysis.proteintools;

import org.genomalysis.plugin.configuration.annotations.Documentation;

/**
 *
 * @author ameyers
 */

@Documentation("Used to indicate that a sequence filter couldn't find necessary resources to run.")
public class InitializationException extends Exception {
    private static final long serialVersionUID = 1L;
    private String[] reasons;

    public String[] getReasons() {
        return reasons;
    }

    public InitializationException() {
        this.reasons = new String[] { "Plugin Initialization: Reason for error unknown" };
    }

    public InitializationException(String... reasons) {
        this.reasons = reasons;
    }

    public InitializationException(Exception e) {
        this.reasons = new String[] { e.getMessage() };
    }
}
