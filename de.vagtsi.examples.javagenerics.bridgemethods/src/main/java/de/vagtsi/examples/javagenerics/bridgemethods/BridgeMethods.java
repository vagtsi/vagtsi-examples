package de.vagtsi.examples.javagenerics.bridgemethods;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

public class BridgeMethods {

	public static void main(String[] args) throws ClassNotFoundException {
		Class<?> storageServiceClass = Class.forName(NamedEntityStorageService.class.getCanonicalName());
		String methodName = "insert";
		List<Method> methods = Arrays.stream(storageServiceClass.getMethods())
				.filter(m -> m.getName().equals(methodName))
				.toList();
		System.out.printf("Class %s contains %d '%s' methods:\n",
				storageServiceClass.getName(), methods.size(), methodName);
		for (Method method : methods) {
			String params = Arrays.stream(method.getParameterTypes())
				.map(t -> t.getName())
				.collect(Collectors.joining(", "));
			
			System.out.printf("> %s(%s) (bridge = %s, synthetic = %s, @Inject = %s)\n",
					method.getName(), params, method.isBridge(), method.isSynthetic(), method.isAnnotationPresent(Inject.class));
		}
	}

}
