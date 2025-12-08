package de.til7701.continu_num.core.ast;

import lombok.Getter;

public enum BinaryOperator {

    ADD("+"),
    SUBTRACT("-"),
    MULTIPLY("*"),
    DIVIDE("/"),
    LT("<"),
    GT(">"),
    LTE("<="),
    GTE(">="),
    EQ("=="),
    NEQ("!="),
    COLLECTION_ACCESS(null);

    @Getter
    private final String symbol;

    BinaryOperator(String symbol) {
        this.symbol = symbol;
    }

    public static BinaryOperator fromString(String operator) {
        for (BinaryOperator op : values()) {
            if (op.symbol != null && op.symbol.equals(operator)) {
                return op;
            }
        }
        throw new IllegalArgumentException("Unknown operator: " + operator);
    }
}
