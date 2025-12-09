package de.til7701.javelin.core.ast;

import java.util.List;

public record InstructionList(
        List<Instruction> instructions
) implements Instruction {
}
