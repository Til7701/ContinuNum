package de.til7701.continu_num.core.reflect;

public non-sealed interface Bool extends Type {

    default boolean isAssignableFrom(Type other) {
        return switch (other) {
            case Bool _ -> true;
            default -> false;
        };
    }

    static Bool instance() {
        return Impl.INSTANCE;
    }

    final class Impl implements Bool {

        private static final Bool INSTANCE = new Impl();

        private Impl() {
        }

        @Override
        public String toString() {
            return "bool";
        }
    }

}
