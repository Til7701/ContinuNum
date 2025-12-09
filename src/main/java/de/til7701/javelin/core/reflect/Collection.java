package de.til7701.javelin.core.reflect;

import de.til7701.javelin.core.ast.BinaryOperator;
import lombok.Getter;

public non-sealed interface Collection extends Type {

    default boolean isAssignableFrom(Type other) {
        return switch (other) {
            case Collection that -> this.getElementType().isAssignableFrom(that.getElementType());
            default -> false;
        };
    }

    Type indexType();

    Type getElementType();

    @BinaryOp(operator = BinaryOperator.COLLECTION_ACCESS)
    Type getElementAt(Collection collection, I32 index);

    static Collection instance(Type indexType, Type elementType) {
        return new Impl(indexType, elementType);
    }

    final class Impl implements Collection {

        @Getter
        private final Type elementType;
        @Getter
        private final Type indexType;

        private Impl(Type indexType, Type elementType) {
            this.indexType = indexType;
            this.elementType = elementType;
        }

        @Override
        public String toString() {
            return "collection";
        }

        @Override
        public Type indexType() {
            return indexType;
        }

        @Override
        public Type getElementAt(Collection collection, I32 index) {
            throw new UnsupportedOperationException();
        }
    }

}
