package de.til7701.javelin.checker;

import de.til7701.javelin.ast.type.Type;

import java.util.HashMap;
import java.util.Map;

public class Scope {

    private final Map<String, Type> variables = new HashMap<>();

    public void initializeVariable(String name, Type type) {
        variables.put(name, type);
    }

    public Type getVariable(String name) {
        return variables.get(name);
    }

    public void destroyVariable(String name) {
        variables.remove(name);
    }

}
