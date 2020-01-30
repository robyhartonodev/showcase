package com.roby.showcase.util;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Component;

/**
 * Utility class for null properties for post / update method in service classes 
 * 
 * @author Roby Hartono
 *
 */
@Component
public class NullPropertiesUtils {

	/**
	 * Copy all properties of old value to the existing one if not one. This method
	 * is necessary for update method in service classes 
	 * 
	 * @param src    posted object for update (new one)
	 * @param target existed object for comparing (old one)
	 */
	public static void copyNonNullProperties(Object src, Object target) {
		BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
	}

	/**
	 * Get all properties from the object that is null
	 * 
	 * @param source
	 * @return a list string of properties that is null
	 */
	public static String[] getNullPropertyNames(Object source) {
		final BeanWrapper src = new BeanWrapperImpl(source);
		java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

		Set<String> emptyNames = new HashSet<String>();
		for (java.beans.PropertyDescriptor pd : pds) {
			Object srcValue = src.getPropertyValue(pd.getName());
			if (srcValue == null)
				emptyNames.add(pd.getName());
		}
		String[] result = new String[emptyNames.size()];
		return emptyNames.toArray(result);
	}
}
