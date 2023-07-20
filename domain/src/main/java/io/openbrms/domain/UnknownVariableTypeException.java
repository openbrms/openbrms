package io.openbrms.domain;

public class UnknownVariableTypeException extends RuntimeException {
    private final String type;

    public UnknownVariableTypeException(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
