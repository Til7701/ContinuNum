package de.til7701.javelin.ast.expression;

import de.til7701.javelin.ast.Span;
import de.til7701.javelin.ast.type.Type;

public record TypeCastExpression(
        Span span,
        Type targetType,
        Expression expression
) implements Expression {
}
