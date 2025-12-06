package de.til7701.continu_num.interpreter.operations;

import de.til7701.continu_num.interpreter.Variable;
import de.til7701.continu_num.interpreter.variables.I32Variable;

public class AddOperations {

    @BinaryOperation(
            operator = "+",
            leftType = "i32",
            rightType = "i32",
            resultType = "i32"
    )
    public static Variable addI32I32(Variable left, Variable right) {
        Integer leftValue = (Integer) left.getValue();
        Integer rightValue = (Integer) right.getValue();
        return new I32Variable(false, leftValue + rightValue);
    }

}
