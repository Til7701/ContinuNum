package de.til7701.javelin.interpreter;


import de.til7701.javelin.operation.Operation;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

@Slf4j
public class Operations {

    private final VariableFactory variableFactory;

    public Operations(VariableFactory variableFactory) {
        this.variableFactory = variableFactory;
    }

    public Variable invokeBinary(Operation operation, Variable leftValue, Variable rightValue) {
        Method method = operation.declaringJavaMethod();
        try {
            Object result = method.invoke(leftValue, leftValue, rightValue);
//            return variableFactory.createVariableFromJavaObject(operation.resultType(), result);
            return null;
        } catch (Exception e) {
            log.debug("Error invoking binary operation: %s with left: %s and right: %s", operation, leftValue, rightValue);
            throw new RuntimeException("Failed to invoke operation method", e);
        }
    }

}
