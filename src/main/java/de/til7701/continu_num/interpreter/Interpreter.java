package de.til7701.continu_num.interpreter;

import de.til7701.continu_num.ast.*;
import de.til7701.continu_num.interpreter.operations.*;
import de.til7701.continu_num.interpreter.variables.I32Variable;
import de.til7701.continu_num.interpreter.variables.StringVariable;

public class Interpreter {

    private static final Class<?>[] JAVA_CLASSES = new Class<?>[]{
            IO.class
    };

    private static final Class<?>[] OPERATION_CLASSES = new Class<?>[]{
            AddOperations.class,
            MultiplyOperations.class,
            SubOperations.class,
            DivOperations.class
    };

    private final KlassLoader klassLoader = new KlassLoader();
    private final KlassRegister klassRegister = new KlassRegister();
    private final Context context = new Context();
    private final VariableFactory variableFactory = new VariableFactory();
    private final OperationsRegister operationsRegister = new OperationsRegister();

    public Interpreter() {
        for (Class<?> javaClass : JAVA_CLASSES) {
            klassRegister.registerKlass(klassLoader.loadJavaClass(javaClass));
        }
        for (Class<?> operationClass : OPERATION_CLASSES) {
            operationsRegister.registerOperationsFromJavaClass(operationClass);
        }
    }

    public void interpret(ContinuNumFile ast) {
        for (Instruction instruction : ast.instructions()) {
            execute(instruction);
        }
    }

    private void execute(Instruction instruction) {
        switch (instruction) {
            case SymbolInitialization symbolInitialization -> executeSymbolInitialization(symbolInitialization);
            case MethodCall methodCall -> executeMethodCall(methodCall);
            case Assignment assignment -> executeAssignment(assignment);
        }
    }

    private void executeSymbolInitialization(SymbolInitialization symbolInitialization) {
        String type = symbolInitialization.type();
        String name = symbolInitialization.name();
        Variable value = evaluateExpression(symbolInitialization.value());
        if (symbolInitialization.isMutable())
            value = value.asMutable();
        context.initializeVariable(name, value);
    }

    private void executeAssignment(Assignment assignment) {
        String name = assignment.name();
        Variable value = evaluateExpression(assignment.value());
        Variable previousValue = context.getVariable(name);
        if (previousValue == null) {
            throw new RuntimeException("Variable " + name + " is not initialized");
        }
        if (previousValue.isMutable()) {
            context.initializeVariable(name, value.asMutable());
        } else {
            throw new RuntimeException("Variable " + name + " is not mutable");
        }
    }

    private Variable executeMethodCall(MethodCall methodCall) {
        if (methodCall.typeName().isPresent()) {
            String typeName = methodCall.typeName().get();
            String methodName = methodCall.methodName();
            Klass klass = klassRegister.getKlass(typeName).orElseThrow();
            Object[] args = methodCall.arguments().stream()
                    .map(this::evaluateExpression)
                    .map(Variable::getValue)
                    .toArray();
            klass.executeMethod(methodName, args);
        } else {
            throw new UnsupportedOperationException();
        }
        return null;
    }

    private Variable evaluateExpression(Expression expression) {
        return switch (expression) {
            case BooleanLiteralExpression _ -> throw new UnsupportedOperationException();
            case IntegerLiteralExpression(String value) -> new I32Variable(Integer.parseInt(value));
            case StringLiteralExpression(String value) -> new StringVariable(value);
            case MethodCall methodCall -> executeMethodCall(methodCall);
            case SymbolExpression(String identifier) -> context.getVariable(identifier);
            case BinaryExpression(Expression left, BinaryOperator operator, Expression right) -> {
                Variable leftValue = evaluateExpression(left);
                Variable rightValue = evaluateExpression(right);
                Operation operation = operationsRegister.getOperation(
                        operator.getSymbol(),
                        leftValue.getType(),
                        rightValue.getType()
                ).orElseThrow(() -> new RuntimeException("Operation " + operator.getSymbol() +
                        " not found for types " + leftValue.getType() + " and " + rightValue.getType()));
                yield operation.execute(leftValue, rightValue);
            }
        };
    }

}
