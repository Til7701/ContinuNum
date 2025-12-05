package de.til7701.continu_num.interpreter;

import de.til7701.continu_num.interpreter.variables.I16Variable;
import de.til7701.continu_num.interpreter.variables.I32Variable;

public class VariableFactory {


    public <T> Variable<T> createVariable(boolean mutable, String type, Object value) {
        return (Variable<T>) switch (type) {
            case "i16" -> new I16Variable(mutable, value);
            case "i32" -> new I32Variable(mutable, value);
            default -> throw new IllegalStateException("Cannot construct variable for unknown type: " + type);
        };
    }
}
