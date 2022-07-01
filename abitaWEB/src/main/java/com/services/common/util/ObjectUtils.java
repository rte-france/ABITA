package com.services.common.util;


/**
 * Helper class for common operations on objects.
 *
 */
public final class ObjectUtils {

	/**
	 * Private constructor.
	 */
	private ObjectUtils() {
		// Nothing to do here
	}

	/**
	 * Null safe comparison of the given two objects.
	 *
	 * @param obj1 first object
	 * @param obj2 second object
	 * @return boolean
	 */
	public static boolean safeEquals(Object obj1, Object obj2) {
		if (obj1 == null && obj2 == null) {
			return true;
		} else if (obj1 == null && obj2 != null) {
			return false;
		} else if (obj1 != null && obj2 == null) {
			return false;
		} else {
			return obj1.equals(obj2);
		}
	}
}
