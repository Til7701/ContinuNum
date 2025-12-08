package de.til7701.continu_num.core.ast;

public record WhileStatement(
        Expression condition,
        Instruction body
) implements Instruction {
}
