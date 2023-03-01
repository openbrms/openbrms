package tech.kuleshov.ruleengine.domain;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum VariableType {
  NUMERIC,
  STRING,
  BOOLEAN,
  DICT;

  public static VariableType from(String value) {
    if (value != null) {
      for (VariableType vt : values()) {
        if (vt.name().equalsIgnoreCase(value)) {
          return vt;
        }
      }
    }
    throw new RuntimeException("UNKNOWN_VAR_TYPE: " + value);
  }

  @JsonCreator
  public VariableType create(String value) {
    return from(value);
  }
}
