package io.openbrms.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.openbrms.domain.VariableType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VariableDefinition {

    @JsonProperty(value = "rule_id")
    private String ruleId;

    private String var;
    private VariableType type;
}
