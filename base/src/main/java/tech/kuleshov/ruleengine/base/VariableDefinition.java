package tech.kuleshov.ruleengine.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tech.kuleshov.ruleengine.domain.VariableType;

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
