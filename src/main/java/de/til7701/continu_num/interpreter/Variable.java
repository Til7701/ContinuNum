package de.til7701.continu_num.interpreter;

public interface Variable {

    String getType();

    boolean isMutable();

    Object getValue();

    Variable asMutable();

}
