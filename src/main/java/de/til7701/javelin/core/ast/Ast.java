package de.til7701.javelin.core.ast;

import java.util.List;

public record Ast(
        List<Instruction> instructions
) implements Node {
}
