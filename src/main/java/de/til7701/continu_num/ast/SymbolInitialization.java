package de.til7701.continu_num.ast;

public record SymbolInitialization(
        boolean isMutable,
        String type,
        String name,
        Expression value
) implements Instruction {

}
