/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.genomalysis.plugin.configuration.testing;

import org.genomalysis.plugin.configuration.annotations.PropertyGetter;
import org.genomalysis.plugin.configuration.annotations.PropertySetter;
import org.genomalysis.plugin.configuration.dialogs.GenericConfigurator;
import org.genomalysis.plugin.configuration.dialogs.StringConfigurator;

/**
 *
 * @author ameyers
 */
public class Person {
    private String firstName;
    private String lastName;
    private int age;
    private Integer weight;
    private Dog firstDog;
    private Dog secondDog;

    @PropertyGetter(PropertyName="FirstName")
    public String getFirstName() {
        return firstName;
    }

    @PropertySetter(PropertyName="FirstName")
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @PropertyGetter(PropertyName="LastName")
    public String getLastName() {
        return lastName;
    }

    @PropertySetter(PropertyName="LastName", ConfiguratorType=CustomStringConfigurator.class)
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @PropertyGetter(PropertyName="Age")
    public int getAge() {
        return age;
    }

    @PropertySetter(PropertyName="Age")
    public void setAge(int age) {
        this.age = age;
    }

    @PropertyGetter(PropertyName="Weight")
    public Integer getWeight() {
        return weight;
    }

    @PropertySetter(PropertyName="Weight")
    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    @PropertyGetter(PropertyName="FirstDog")
    public Dog getFirstDog() {
        return firstDog;
    }

    @PropertySetter(PropertyName="FirstDog")
    public void setFirstDog(Dog firstDog) {
        this.firstDog = firstDog;
    }

    @PropertyGetter(PropertyName="SecondDog")
    public Dog getSecondDog() {
        return secondDog;
    }

    @PropertySetter(PropertyName="SecondDog", ConfiguratorType=GenericConfigurator.class)
    public void setSecondDog(Dog secondDog) {
        this.secondDog = secondDog;
    }
}
