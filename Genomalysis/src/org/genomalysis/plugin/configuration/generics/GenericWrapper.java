package org.genomalysis.plugin.configuration.generics;

import org.genomalysis.plugin.configuration.ConfigurationException;
import org.genomalysis.plugin.configuration.Property;
import org.genomalysis.plugin.configuration.PropertyDescriptor;
import org.genomalysis.plugin.configuration.PropertyIntrospector;
import org.genomalysis.plugin.configuration.annotations.PropertyGetter;
import org.genomalysis.plugin.configuration.annotations.PropertySetter;

public class GenericWrapper {
	private Class<?> clazz;
	private Object value;
	private String description;

	public GenericWrapper(Class<?> clazz) {
		this(clazz, null);
	}

	public GenericWrapper(Class<?> clazz, Object value) {
		this.description = "GenericWrapper";

		this.clazz = clazz;
		this.value = value;
	}

	public Property getProperty() {
		Property p;
		try {
			p = null;
			Property[] props = PropertyIntrospector.getProperties(this)
					.getProperties();
			p = props[0];

			p.setPropertyType(this.clazz);
			return p;
		} catch (ConfigurationException ex) {
			throw new RuntimeException(ex);
		}
	}

	public Class<?> getValueType() {
		return this.clazz;
	}

	public String toString() {
		return this.description + ": " + this.clazz.getSimpleName();
	}

	@PropertySetter(PropertyName = "Value")
	public void setValue(Object value) {
		this.value = value;
	}

	@PropertyGetter(PropertyName = "Value")
	public Object getValue() {
		return this.value;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}