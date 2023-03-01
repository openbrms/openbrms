package tech.kuleshov.ruleengine.base;

import com.twineworks.tweakflow.lang.TweakFlow;
import com.twineworks.tweakflow.lang.parse.ParseResult;
import com.twineworks.tweakflow.lang.values.Value;
import java.util.Map;
import java.util.Optional;

public class RuleExecutor {

  public Optional<Value> execute(RuleDefinition rd, Map<String, Value> input) {
    String code = prepare(rd.getWhen(), input);
    ParseResult r = TweakFlow.parse(code);
    if (r.isError()) throw new RuntimeException();
    Value value = TweakFlow.evaluate(code);
    boolean isApplicable = value.bool();
    if (!isApplicable) return Optional.empty();

    Value result = TweakFlow.evaluate(prepare(rd.getThen(), input));
    return Optional.of(result);
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
