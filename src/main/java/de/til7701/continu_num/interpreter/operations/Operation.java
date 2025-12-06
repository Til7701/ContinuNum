package de.til7701.continu_num.interpreter.operations;

import de.til7701.continu_num.interpreter.Variable;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Operation {

    private final String leftType;
    private final String rightType;
    private final String resultType;

    public String leftType() {
        return leftType;
    }

    public String rightType() {
        return rightType;
    }

    public String resultType() {
        return resultType;
    }

    public Variable execute(Variable left, Variable right) {
        throw new UnsupportedOperationException("Not implemented");
    }

}
