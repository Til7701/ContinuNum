package de.til7701.javelin.annotation;

import de.til7701.javelin.ast.type_definition.classes.ClassFieldDefinition;

public class GetterAnnotationProcessor implements AnnotationProcessor<ClassFieldDefinition> {

    @Override
    public ProcessingResult process(ClassFieldDefinition node) {
        return null;
    }

}
