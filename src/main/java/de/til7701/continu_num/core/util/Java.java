package de.til7701.continu_num.core.util;

import de.til7701.continu_num.core.reflect.*;

public class Java {

    public static Type mapTypes(Class<?> clazz) {
        if (clazz == Short.class || clazz == I16.class) return I16.instance();
        if (clazz == Integer.class || clazz == I32.class) return I32.instance();
        if (clazz == String.class || clazz == Str.class) return Str.instance();
        if (clazz == Void.class || clazz.getSimpleName().equals("void") || clazz == None.class) return None.instance();
        if (clazz == Boolean.class || clazz == Bool.class) return Bool.instance();
        if (clazz == Object.class || clazz == Any.class) return Any.instance();
        if (clazz.isArray()) {
            Class<?> componentType = clazz.getComponentType();
            Type elementType = mapTypes(componentType);
            return Collection.instance(elementType, Any.instance());
        }
        if (clazz == Collection.class) {
            return Collection.instance(Any.instance(), Any.instance());
        }
        if (clazz == Type.class) {
            return Any.instance();
        }
        throw new UnsupportedOperationException("Unsupported type: " + clazz.getName());
    }

}
