package de.til7701.continu_num.interpreter.variables;

import de.til7701.continu_num.interpreter.Variable;
import lombok.AllArgsConstructor;

import java.util.Objects;

@AllArgsConstructor
public final class I16Variable implements Variable {

    private final boolean mutable;

    private short value;

    public I16Variable(boolean mutable, Object value) {
        this.mutable = mutable;
        this.value = toShort(value);
    }

    private static short toShort(Object value) {
        return switch (value) {
            case Short s -> s;
            case Number n -> n.shortValue();
            case String str -> Short.parseShort(str);
            case Boolean b -> (short) (b ? 1 : 0);
            default -> throw new IllegalArgumentException("Cannot convert value to short: " + value);
        };
    }

    @Override
    public String getType() {
        return "i16";
    }

    @Override
    public boolean isMutable() {
        return mutable;
    }

    @Override
    public Short getValue() {
        return value;
    }

    @Override
    public Variable asMutable() {
        return new I16Variable(true, value);
    }

    public void setValue(short value) {
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
