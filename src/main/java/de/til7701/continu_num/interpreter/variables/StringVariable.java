package de.til7701.continu_num.interpreter.variables;

import de.til7701.continu_num.interpreter.Variable;

public class StringVariable implements Variable {

    private final boolean mutable;

    private String value;

    public StringVariable(String value) {
        this(false, value);
    }

    public StringVariable(boolean mutable, String value) {
        this.mutable = mutable;
        this.value = value;
    }

    public void setValue(String value) {
        if (!mutable) {
            throw new UnsupportedOperationException("Variable is immutable");
        }
        this.value = value;
    }

    @Override
    public String getType() {
        return "str";
    }

    @Override
    public boolean isMutable() {
        return mutable;
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public Variable asMutable() {
        return new StringVariable(true, value);
    }

}
