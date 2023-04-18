package tech.kuleshov.ruleengine.base.exception;

import java.util.*;

public class MissingVariablesException extends RuntimeException {
    private final List<String> missingVariables;

    public MissingVariablesException(List<String> missingVariables) {
        this.missingVariables = missingVariables;
    }

    public List<String> getMissingVariables() {
        return missingVariables;
    }
}
