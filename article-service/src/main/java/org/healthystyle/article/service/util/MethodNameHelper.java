package org.healthystyle.article.service.util;

import java.util.Arrays;

public class MethodNameHelper {
	private MethodNameHelper() {
	}

	public static String[] getMethodParamNames(Class<?> clazz, String methodName, Class<?>... params) {
		try {
			return Arrays.stream(
					clazz.getMethod(methodName, params).getParameters())
					.map(p -> p.getName()).toArray(String[]::new);
		} catch (NoSuchMethodException | SecurityException e) {
			throw new RuntimeException("Exception occurred while getting method param names", e);
		}
	}
}
