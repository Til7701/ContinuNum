package de.til7701.continu_num.interpreter;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class KlassLoader {

    public Klass loadJavaClass(Class<?> javaClass) {
        String className = javaClass.getSimpleName();
        Map<String, Metod<?>> methods = loadJavaMethods(javaClass);
        return new Klass(className, methods);
    }

    private Map<String, Metod<?>> loadJavaMethods(Class<?> javaClass) {
        Map<String, Metod<?>> metods = new HashMap<>();

        Method[] javaMethods = javaClass.getDeclaredMethods();
        for (Method javaMethod : javaMethods) {
            String methodName = javaMethod.getName();
            Metod<?> metod = args -> {
                try {
                    Object result = javaMethod.invoke(null, args);
                    String returnType = extractReturnType(javaMethod);
                    return new Result<>(returnType, result);
                } catch (Exception e) {
                    throw new RuntimeException("Error invoking method " + methodName + " of class " + javaClass.getSimpleName(), e);
                }
            };
            metods.put(methodName, metod);
        }

        return metods;
    }

    private static String extractReturnType(Method method) {
        String returnType = method.getReturnType().getSimpleName();
        return switch (returnType) {
            case "short" -> "i16";
            case "int" -> "i32";
            case "boolean" -> "bool";
            case "void" -> "void";
            default -> returnType;
        };
    }

}
