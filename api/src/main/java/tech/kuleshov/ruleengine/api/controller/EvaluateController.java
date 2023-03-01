package tech.kuleshov.ruleengine.api.controller;

import java.util.Map;
import org.springframework.web.bind.annotation.*;
import tech.kuleshov.ruleengine.api.service.RuleEngineService;
import tech.kuleshov.ruleengine.domain.EvalResultDto;

@RestController
@RequestMapping("/eval")
public class EvaluateController {

  private final RuleEngineService ruleEngineService;

  public EvaluateController(RuleEngineService ruleEngineService) {
    this.ruleEngineService = ruleEngineService;
  }

  @PostMapping("/{id}")
  public EvalResultDto eval(@PathVariable String id, @RequestBody Map<String, Object> params) {
    return ruleEngineService.eval(id, params);
  }
}
