package de.til7701.javelin.core.ast;

public sealed interface Expression extends Node permits BinaryExpression, BooleanLiteralExpression, CollectionCreationExpression, InstanceMethodCall, IntegerLiteralExpression, StaticMethodCall, StringLiteralExpression, SymbolExpression {
}
