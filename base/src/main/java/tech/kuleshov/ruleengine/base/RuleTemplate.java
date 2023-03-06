package tech.kuleshov.ruleengine.base;

import com.twineworks.tweakflow.lang.values.Value;
import java.util.Map;
import java.util.Set;

public interface RuleTemplate {

  Set<String> getDependencies();

  Map<String, VariableDefinition> getVariables();

  boolean when(Map<String, Value> input);

  Value then();
}
