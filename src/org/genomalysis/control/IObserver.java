/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.genomalysis.control;

/**
 *
 * @author ameyers
 */
public interface IObserver {
    void update();
    void showError(String errorMsg);
}
