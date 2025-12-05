package de.til7701.continu_num.interpreter;

import java.util.HashMap;
import java.util.Map;

public class Context {

    private final Map<String, Variable> variables = new HashMap<>();

    public void initializeVariable(String name, Variable variable) {
        variables.put(name, variable);
    }

    public Variable getVariable(String name) {
        return variables.get(name);
    }

}
