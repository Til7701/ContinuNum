package de.til7701.javelin.core.ast;

public record Assignment(
        String name,
        Expression value
) implements Instruction {
}
