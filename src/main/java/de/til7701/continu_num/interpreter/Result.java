package de.til7701.continu_num.interpreter;

public record Result<T>(
        String type,
        T value
) {
}
