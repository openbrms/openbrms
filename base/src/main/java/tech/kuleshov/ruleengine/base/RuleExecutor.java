package tech.kuleshov.ruleengine.base;

import com.twineworks.tweakflow.lang.TweakFlow;
import com.twineworks.tweakflow.lang.parse.ParseResult;
import com.twineworks.tweakflow.lang.values.Value;
import java.util.*;

public class RuleExecutor {

  public Optional<Value> execute(RuleDefinition rd, Map<String, Value> input) {
    List<String> missingVariables = checkPrerequisites(rd, input);
    if (missingVariables.size() > 0) {
      if (rd.isRequired()) {
        throw new RuntimeException("Missing variables: " + String.join(",", missingVariables));
      } else {
        return Optional.empty();
      }
    }
    String whenSource = prepare(rd.getWhen(), input);
    ParseResult r = TweakFlow.parse(whenSource);
    if (r.isError()) {
      throw new RuntimeException(rd.getWorkflowId() + "/" + rd.getId());
    }
    Value value = TweakFlow.evaluate(whenSource);
    boolean isApplicable = value.bool();
    if (!isApplicable) return Optional.empty();

    String thenSource = prepare(rd.getThen(), input);
    System.out.println(thenSource);
    Value result = TweakFlow.evaluate(thenSource);
    return Optional.of(result);
  }

  private List<String> checkPrerequisites(RuleDefinition rd, Map<String, Value> input) {
    Set<String> rdKeySet = rd.getVariables().keySet();
    Set<String> inputKeySet = input.keySet();
    List<String> result = new ArrayList<>(rdKeySet);
    result.removeAll(inputKeySet);
    return result;
  }

  private String prepare(String when, Map<String, Value> input) {
    StringBuilder sb = new StringBuilder();
    sb.append("let {\n");
    for (Map.Entry<String, Value> entry : input.entrySet()) {
      String k = entry.getKey();
      Value v = entry.getValue();
      sb.append(String.format("%s: %s;\n", k, v));
    }
    sb.append("}\n");
    sb.append(when);

    return sb.toString();
  }
}
