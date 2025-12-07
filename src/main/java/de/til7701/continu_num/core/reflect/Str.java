package de.til7701.continu_num.core.reflect;

import de.til7701.continu_num.core.ast.BinaryOperator;

public non-sealed interface Str extends Type {

    default boolean isAssignableFrom(Type other) {
        return switch (other) {
            case Str _ -> true;
            default -> false;
        };
    }

    @BinaryOp(operator = BinaryOperator.COLLECTION_ACCESS)
    Str charAt(Str str, I32 index);

    static Str instance() {
        return Impl.INSTANCE;
    }

    final class Impl implements Str {

        private static final Str INSTANCE = new Impl();

        private Impl() {
        }

        @Override
        public Str charAt(Str str, I32 index) {
            throw new UnsupportedOperationException();
        }

        @Override
        public String toString() {
            return "str";
        }
    }

}
