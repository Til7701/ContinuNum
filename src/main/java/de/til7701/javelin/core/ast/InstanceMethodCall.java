package de.til7701.javelin.core.ast;

import java.util.List;

public record InstanceMethodCall(
        Expression instance,
        String methodName,
        List<Expression> arguments
) implements Expression, Instruction {
}
