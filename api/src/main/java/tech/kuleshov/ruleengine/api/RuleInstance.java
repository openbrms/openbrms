package tech.kuleshov.ruleengine.api;

import com.twineworks.tweakflow.lang.values.Value;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import tech.kuleshov.ruleengine.base.Rule;
import tech.kuleshov.ruleengine.base.RuleDefinition;
import tech.kuleshov.ruleengine.base.VariableDefinition;

public class RuleInstance implements Rule {

  private final RuleDefinition ruleDefinition;

  public RuleInstance(RuleDefinition ruleDefinition) {
    this.ruleDefinition = ruleDefinition;
  }

  @Override
  public Set<String> getDependencies() {
    return ruleDefinition.getVariables().values().stream()
        .map(VariableDefinition::getRuleId)
        .filter(Objects::nonNull)
        .collect(Collectors.toSet());
  }

  @Override
  public Map<String, VariableDefinition> getVariables() {
    return ruleDefinition.getVariables();
  }

  @Override
  public boolean when(Map<String, Value> input) {
    // TweakFlow.compile()
    return false;
  }

  @Override
  public Value then() {
    return null;
  }
}
