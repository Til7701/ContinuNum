package de.til7701.javelin.core.ast;

import de.til7701.javelin.core.reflect.Type;

public record SymbolInitialization(
        boolean isMutable,
        Type type,
        String name,
        Expression value
) implements Instruction {

}
