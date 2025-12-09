package de.til7701.javelin.core.reflect;

import de.til7701.javelin.core.ast.BinaryOperator;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface BinaryOp {

    BinaryOperator operator();

}
