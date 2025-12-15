package de.til7701.javelin.annotation;

import de.til7701.javelin.ast.methods.MethodDefinition;

import java.util.List;
import java.util.Optional;

public record ProcessingResult(
        Optional<List<MethodDefinition>> addedMethods
) {
}
