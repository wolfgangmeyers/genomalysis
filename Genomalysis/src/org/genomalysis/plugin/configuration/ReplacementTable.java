package org.genomalysis.plugin.configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.genomalysis.plugin.configuration.dialogs.GenericConfigurator;

public final class ReplacementTable {
	private static HashMap<String, List<Class<?>>> replacements = new HashMap();

	public ReplacementTable() {
		try {
			registerReplacement(List.class, ArrayList.class);
			registerReplacement(Map.class, HashMap.class);
			registerReplacement(IPropertyConfigurator.class,
					GenericConfigurator.class);
			registerReplacement(Object.class, String.class);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public List<Class<?>> recursivelyGetReplacements(Class<?> baseClass) {
		List result = new ArrayList();
		List<Class<?>> replacementList = getReplacements(baseClass);
		for (Class clazz : replacementList) {
			if (!(result.contains(clazz))) {
				result.add(clazz);
				List<Class<?>> replacementList2 = recursivelyGetReplacements(clazz);
				for (Class clazz2 : replacementList2) {
					if (!(result.contains(clazz2)))
						result.add(clazz2);
				}
			}
		}
		return result;
	}

	public List<Class<?>> getReplacements(Class<?> baseClass) {
		List replacementList = (List) replacements.get(baseClass.getName());
		if (replacementList == null) {
			replacementList = new ArrayList();
			replacements.put(baseClass.getName(), replacementList);
		}
		return replacementList;
	}

	public void registerReplacement(Class<?> baseClass,
			Class<?> replacementClass)
			throws org.genomalysis.plugin.configuration.ConfigurationException {
		try {
			replacementClass.asSubclass(baseClass);
		} catch (ClassCastException ex) {
			throw new org.genomalysis.plugin.configuration.ConfigurationException(
					"Class " + replacementClass.getName()
							+ " declares replacement for type "
							+ baseClass.getName() + " but does not extend it");
		}

		List replacementList = getReplacements(baseClass);
		if (!(replacementList.contains(replacementClass)))
			replacementList.add(replacementClass);
	}
}