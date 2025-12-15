package de.til7701.javelin.annotation;

import de.til7701.javelin.ast.Ast;
import de.til7701.javelin.ast.Node;
import de.til7701.javelin.ast.Script;
import de.til7701.javelin.ast.methods.MethodDefinition;
import de.til7701.javelin.ast.type_definition.annotations.AnnotationTypeDefinition;
import de.til7701.javelin.ast.type_definition.classes.ClassDefinition;
import de.til7701.javelin.ast.type_definition.classes.ClassFieldDefinition;
import de.til7701.javelin.ast.type_definition.enums.EnumTypeDefinition;

import java.util.List;
import java.util.Map;

public class AnnotationProcessorManager {

    private final Map<Class<? extends Node>, List<AnnotationProcessor<?>>> processors = Map.of(
            ClassFieldDefinition.class, List.of(
                    new GetterAnnotationProcessor()
            ),
            MethodDefinition.class, List.of(
                    new OperationsAnnotationProcessor()
            )
    );

    public Ast process(Ast ast) {
        Result result = new Result(ast, true);
        while (result.modified()) {
            result = switch (result.ast()) {
                case Script script -> new Result(script, false);
                case ClassDefinition classDefinition -> processClassDefinition(classDefinition);
                case AnnotationTypeDefinition annotationTypeDefinition -> new Result(annotationTypeDefinition, false);
                case EnumTypeDefinition enumTypeDefinition -> new Result(enumTypeDefinition, false);
            };
        }
        return result.ast();
    }

    private Result processClassDefinition(ClassDefinition classDefinition) {
        return classDefinition;
    }

    private record Result(Ast ast, boolean modified) {
    }

}
