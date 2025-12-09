package de.til7701.javelin.core.ast;

import java.util.List;
import java.util.Optional;

public record StaticMethodCall(
        Optional<String> typeName,
        String methodName,
        List<Expression> arguments
) implements Expression, Instruction {
}
