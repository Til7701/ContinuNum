package de.til7701.continu_num.core.type_checker;

import de.til7701.continu_num.core.ast.*;
import de.til7701.continu_num.core.environment.Environment;
import de.til7701.continu_num.core.reflect.*;

import java.util.Arrays;
import java.util.List;

public class TypeChecker {

    private final OperationsRegister operationsRegister;
    private final KlassRegister klassRegister;

    private ContextStack context;

    public TypeChecker(Environment environment) {
        this.operationsRegister = environment.getOperationsRegister();
        this.klassRegister = environment.getKlassRegister();
    }

    public void check(Ast ast) {
        context = new ContextStack();
        context.push(new Context());

        List<Instruction> instructions = ast.instructions();
        for (Instruction instruction : instructions) {
            check(instruction);
        }
    }

    private void check(Instruction instruction) {
        switch (instruction) {
            case SymbolInitialization symbolInitialization -> checkSymbolInitialization(symbolInitialization);
            case MethodCall methodCall -> checkMethodCall(methodCall);
            case Assignment assignment -> checkAssignment(assignment);
            case InstructionList instructionList -> {
                context.push(new Context());
                instructionList.instructions().forEach(this::check);
                context.pop();
            }
            case WhileStatement whileStatement -> {
                Expression condition = whileStatement.condition();
                Type conditionType = evaluateExpressionType(condition);
                if (!conditionType.equals(Bool.instance())) {
                    throw new RuntimeException("Type mismatch: while condition must be of type Bool, but found " + conditionType);
                }
                check(whileStatement.body());
            }
        }
    }

    private void checkAssignment(Assignment assignment) {
        Type expressionType = evaluateExpressionType(assignment.value());
        Type variableType = context.getVariable(assignment.name());
        if (!variableType.isAssignableFrom(expressionType)) {
            throw new RuntimeException("Type mismatch: cannot assign " + expressionType + " to " + variableType);
        }
    }

    private Type checkMethodCall(MethodCall methodCall) {
        Klass klass = klassRegister.getKlass(methodCall.typeName().orElseThrow()).orElseThrow();

        Type[] parameterTypes = methodCall.arguments().stream()
                .map(this::evaluateExpressionType)
                .toArray(Type[]::new);

        Metod metod = klass.getMethod(methodCall.methodName(), parameterTypes).orElseThrow(() ->
                new RuntimeException("Method " + methodCall.methodName() + "(" + String.join(", ", Arrays.stream(parameterTypes)
                        .map(Type::toString)
                        .toArray(String[]::new)) + ") not found in class " + klass.name())
        );
        if (!(metod instanceof JavaMetod)) {
            throw new UnsupportedOperationException();
        }
        return metod.returnType();
    }

    private void checkSymbolInitialization(SymbolInitialization symbolInitialization) {
        Type symbolType = symbolInitialization.type();
        Type expressionType = evaluateExpressionType(symbolInitialization.value());
        if (!symbolType.isAssignableFrom(expressionType)) {
            throw new RuntimeException("Type mismatch: cannot assign " + expressionType + " to " + symbolType);
        }
        context.initializeVariable(symbolInitialization.name(), symbolType);
    }

    private Type evaluateExpressionType(Expression value) {
        return switch (value) {
            case BooleanLiteralExpression _ -> throw new UnsupportedOperationException();
            case IntegerLiteralExpression _ -> I32.instance();
            case StringLiteralExpression _ -> Str.instance();
            case MethodCall methodCall -> checkMethodCall(methodCall);
            case BinaryExpression(Expression left, BinaryOperator operator, Expression right) -> {
                Type leftType = evaluateExpressionType(left);
                Type rightType = evaluateExpressionType(right);
                Operation operation = operationsRegister.getBinaryOperation(
                        operator, leftType, rightType).orElseThrow();
                yield operation.resultType();
            }
            case SymbolExpression(String identifier) -> context.getVariable(identifier);
        };
    }

}
