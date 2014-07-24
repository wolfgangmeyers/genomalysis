/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.genomalysis.plugin.configuration.testing;

import org.genomalysis.plugin.configuration.annotations.Configurator;
import org.genomalysis.plugin.configuration.annotations.PropertyGetter;
import org.genomalysis.plugin.configuration.annotations.PropertySetter;

/**
 *
 * @author ameyers
 */
@Configurator(value=DogConfigurator.class)
public class Dog {
    private String name;
    private int age;
    private Integer numfleas;

    @PropertyGetter(PropertyName="Name")
    public String getName() {
        return name;
    }

    @PropertySetter(PropertyName="Name")
    public void setName(String name) {
        this.name = name;
    }

    @PropertyGetter(PropertyName="Age")
    public int getAge() {
        return age;
    }

    @PropertySetter(PropertyName="Age")
    public void setAge(int age) {
        this.age = age;
    }

    @PropertyGetter(PropertyName="Number of Fleas")
    public Integer getNumfleas() {
        return numfleas;
    }

    @PropertySetter(PropertyName="Number of Fleas")
    public void setNumfleas(Integer numfleas) {
        this.numfleas = numfleas;
    }
    
    @Override
    public String toString(){
        return "Dog: " + this.name;
    }
}
