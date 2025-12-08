package de.til7701.continu_num.core.ast;

import java.util.List;

public record InstructionList(
        List<Instruction> instructions
) implements Instruction {
}
