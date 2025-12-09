package de.til7701.javelin.core.reflect;

public sealed interface Metod permits JavaMetod {

    String name();

    Type returnType();

    Type[] parameterTypes();

    String[] parameterNames();

}
