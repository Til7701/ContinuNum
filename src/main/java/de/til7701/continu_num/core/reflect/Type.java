package de.til7701.continu_num.core.reflect;

public sealed interface Type permits Any, Bool, Collection, I16, I32, None, Str {

    boolean isAssignableFrom(Type other);

    static Type fromName(String name) {
        return switch (name) {
            case "i16" -> I16.instance();
            case "i32" -> I32.instance();
            case "str" -> Str.instance();
            case "bool" -> Bool.instance();
            case "none" -> None.instance();
            case "i32[]" -> Collection.instance(I32.instance(), Any.instance());
            default -> throw new IllegalArgumentException("Unknown type name: " + name);
        };
    }

}
