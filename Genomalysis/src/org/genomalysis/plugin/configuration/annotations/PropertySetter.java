/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.genomalysis.plugin.configuration.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.genomalysis.plugin.configuration.*;

/**
 *
 * @author ameyers
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PropertySetter {
    String PropertyName();
    Class<? extends IPropertyConfigurator> ConfiguratorType() default VoidConfigurator.class;
}
