package de.til7701.javelin.core.ast;

public sealed interface Node permits
        Ast,
        Expression,
        Instruction {
}
