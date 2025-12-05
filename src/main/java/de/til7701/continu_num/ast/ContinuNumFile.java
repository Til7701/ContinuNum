package de.til7701.continu_num.ast;

import java.util.List;

public record ContinuNumFile(
        List<Instruction> instructions
) implements Node {
}
