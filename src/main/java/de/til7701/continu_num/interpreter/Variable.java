package de.til7701.continu_num.interpreter;

public interface Variable<T> {

    boolean isMutable();

    Result<T> getValue();

}
