package org.genomalysis.plugin.configuration.annotations;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.genomalysis.plugin.configuration.IPropertyConfigurator;

@Retention(RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.TYPE})
public @interface Configurator
{
  public abstract Class<? extends IPropertyConfigurator> value();
}