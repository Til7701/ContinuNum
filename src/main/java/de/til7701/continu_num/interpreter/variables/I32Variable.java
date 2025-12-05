package de.til7701.continu_num.interpreter.variables;

import de.til7701.continu_num.interpreter.Result;
import de.til7701.continu_num.interpreter.Variable;
import lombok.AllArgsConstructor;

import java.util.Objects;

@AllArgsConstructor
public final class I32Variable implements Variable<Integer> {

    private final boolean mutable;

    private int value;

    public I32Variable(boolean mutable, Object value) {
        this.mutable = mutable;
        this.value = toInt(value);
    }

    private static int toInt(Object value) {
        return switch (value) {
            case Integer i -> i;
            case Number n -> n.intValue();
            case String str -> Integer.parseInt(str);
            case Boolean b -> b ? 1 : 0;
            default -> throw new IllegalArgumentException("Cannot convert value to int: " + value);
        };
    }

    @Override
    public boolean isMutable() {
        return mutable;
    }

    @Override
    public Result<Integer> getValue() {
        return new Result<>("i32", value);
    }

    public void setValue(int value) {
        if (!mutable) {
            throw new UnsupportedOperationException("Variable is immutable");
        }
        this.value = value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mutable, value);
    }

    @Override
    public String toString() {
        return "IntVariable[" +
                "mutable=" + mutable +
                ", value=" + value +
                ']';
    }

}
