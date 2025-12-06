package de.til7701.continu_num.interpreter;

import java.util.Map;

public record Klass(
        String name,
        Map<String, Metod> methods
) {

    Variable executeMethod(String methodName, Object... args) {
        Metod metod = methods.get(methodName);
        if (metod == null) {
            throw new RuntimeException("Method " + methodName + " not found in class " + name);
        }
        return metod.execute(args);
    }

}
