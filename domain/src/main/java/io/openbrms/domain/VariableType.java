package io.openbrms.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.Set;

public enum VariableType {
    NUMERIC(Set.of("numeric", "long")), STRING(Set.of("string")), BOOLEAN(Set.of("boolean")), DICT(Set.of("dict"));

    private final Set<String> types;

    VariableType(Set<String> types) {
        this.types = types;
    }

    public static VariableType from(String value) {
        if (value != null) {
            for (VariableType vt : values()) {
                for (String t : vt.getTypes()) {
                    if (t.equalsIgnoreCase(value)) {
                        return vt;
                    }
                }
            }
        }
        throw new UnknownVariableTypeException(value);
    }

    @JsonCreator
    public VariableType create(String value) {
        return from(value);
    }

    public Set<String> getTypes() {
        return types;
    }
}
