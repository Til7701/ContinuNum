package de.til7701.continu_num.interpreter.operations;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface BinaryOperation {

    String operator();

    String leftType();

    String rightType();

    String resultType();

}
