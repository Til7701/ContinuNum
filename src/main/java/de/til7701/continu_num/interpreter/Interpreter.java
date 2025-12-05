package de.til7701.continu_num.interpreter;

import de.til7701.continu_num.ast.*;

public class Interpreter {

    private static final Class<?>[] JAVA_CLASSES = new Class<?>[]{
            IO.class
    };

    private final KlassLoader klassLoader = new KlassLoader();
    private final KlassRegister klassRegister = new KlassRegister();
    private final Context context = new Context();
    private final VariableFactory variableFactory = new VariableFactory();

    public Interpreter() {
        for (Class<?> javaClass : JAVA_CLASSES) {
            klassRegister.registerKlass(klassLoader.loadJavaClass(javaClass));
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
        }
    }

    private void executeSymbolInitialization(SymbolInitialization symbolInitialization) {
        String type = symbolInitialization.type();
        String name = symbolInitialization.name();
        Result<?> value = evaluateExpression(symbolInitialization.value());
        Variable<?> variable = variableFactory.createVariable(symbolInitialization.isMutable(), type, value.value());
        context.initializeVariable(name, variable);
    }

    private Result<?> executeMethodCall(MethodCall methodCall) {
        if (methodCall.typeName().isPresent()) {
            String typeName = methodCall.typeName().get();
            String methodName = methodCall.methodName();
            Klass klass = klassRegister.getKlass(typeName).orElseThrow();
            Object[] args = methodCall.arguments().stream()
                    .map(this::evaluateExpression)
                    .map(Result::value)
                    .toArray();
            klass.executeMethod(methodName, args);
        } else {
            throw new UnsupportedOperationException();
        }
        return null;
    }

    private Result<?> evaluateExpression(Expression expression) {
        return switch (expression) {
            case BooleanLiteralExpression(boolean value) -> new Result<>("boolean", value);
            case IntegerLiteralExpression(String value) -> new Result<>("i32", Integer.parseInt(value));
            case StringLiteralExpression(String value) -> new Result<>("string", value);
            case MethodCall methodCall -> executeMethodCall(methodCall);
            case SymbolExpression(String identifier) -> context.getVariable(identifier).getValue();
        };
    }

}
