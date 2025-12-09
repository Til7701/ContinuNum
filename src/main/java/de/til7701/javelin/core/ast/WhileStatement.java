package de.til7701.javelin.core.ast;

public record WhileStatement(
        Expression condition,
        Instruction body
) implements Instruction {
}
