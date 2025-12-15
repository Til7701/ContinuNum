package de.til7701.javelin.annotation;

import de.til7701.javelin.ast.Node;

public interface AnnotationProcessor<T extends Node> {

    ProcessingResult process(T node);

}
