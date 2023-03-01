package tech.kuleshov.ruleengine.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.twineworks.tweakflow.lang.values.ListValue;
import com.twineworks.tweakflow.lang.values.Value;
import com.twineworks.tweakflow.lang.values.Values;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import tech.kuleshov.ruleengine.base.RuleDefinition;
import tech.kuleshov.ruleengine.base.RuleExecutor;

@Slf4j
public class RuleengineApplication {

  public static void main(String[] args) throws IOException {

    //        Value value = TweakFlow.evaluate("1 + 5");
    //
    //        System.out.println(value.bool());

    File file = new File("rule1.json");
    InputStream inputStream = new FileInputStream(file);
    String json = new String(inputStream.readAllBytes());
    System.out.println(json);

    ObjectMapper mapper = new ObjectMapper();
    RuleDefinition rd1 = mapper.readValue(json, RuleDefinition.class);

    Value v3 = Values.make(new ListValue().append(Values.make(10)).append(Values.make(15)));

    Map<String, Value> input = new HashMap<>();
    input.put("a1", Values.make(1000));
    input.put("a2", Values.make(2000));
    input.put("a3", v3);

    RuleExecutor ruleExecutor = new RuleExecutor();
    Optional<Value> opt = ruleExecutor.execute(rd1, input);
    opt.ifPresent(
        result -> {
          System.out.println("result >>> " + result);
        });
    if (!opt.isPresent()) {
      System.out.println("condition >>> false");
    }

    int a = 1;
  }
}
