package de.til7701.javelin.klass;

import de.til7701.javelin.ast.type.Type;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class KlassRegister {

    private final Map<String, Klass> klasses = new HashMap<>();

    public void registerKlass(Klass klass) {
        klasses.put(klass.name(), klass);
    }

    public Optional<Klass> getKlass(String name) {
        return Optional.ofNullable(klasses.get(name));
    }

    public Optional<Klass> getKlass(Type type) {
        return Optional.empty();
    }

}
