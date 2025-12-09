package de.til7701.javelin.core.ast;

import java.util.List;

public record CollectionCreationExpression(
        List<Expression> elements
) implements Expression {
}
