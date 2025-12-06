package de.til7701.continu_num.interpreter.operations;

import de.til7701.continu_num.interpreter.Variable;
import de.til7701.continu_num.interpreter.VariableFactory;
import picocli.CommandLine;

import java.lang.reflect.Method;
import java.util.*;

public class OperationsRegister {

    private final Map<String, List<Operation>> operations = new HashMap<>();
    private final VariableFactory variableFactory = new VariableFactory();

    public void registerOperationsFromJavaClass(Class<?> cls) {
        Method[] methods = cls.getDeclaredMethods();
        for (Method method : methods) {
            BinaryOperation binaryOperation = method.getAnnotation(BinaryOperation.class);
            if (binaryOperation != null) {
                String operator = binaryOperation.operator();
                String leftType = binaryOperation.leftType();
                String rightType = binaryOperation.rightType();
                String resultType = binaryOperation.resultType();

                Operation operation = new Operation(leftType, rightType, resultType) {
                    @Override
                    public Variable execute(Variable left, Variable right) {
                        try {
                            CommandLine.tracer().debug("Executing operation " + operator +
                                    " with left type " + left.getType() + " and value " + left.getValue()
                                    + " and right type " + right.getType() + " and value " + right.getValue());
                            Object result = method.invoke(null, left, right);
                            return (Variable) result;
                        } catch (Exception e) {
                            throw new RuntimeException("Error executing operation " + operator, e);
                        }
                    }
                };
                registerOperation(operator, operation);
            }
        }
    }

    public void registerOperation(String operator, Operation ops) {
        operations.computeIfAbsent(operator, _ -> new ArrayList<>())
                .add(ops);
    }

    public Optional<Operation> getOperation(String name, String leftType, String rightType) {
        List<Operation> ops = operations.get(name);
        if (ops != null) {
            for (Operation op : ops) {
                String left = op.leftType();
                String right = op.rightType();
                if (left.equals(leftType) && right.equals(rightType)) {
                    return Optional.of(op);
                }
            }
        }
        return Optional.empty();
    }

    public void discoverOperations(Class<?>[] operationClasses) {
    }

}
