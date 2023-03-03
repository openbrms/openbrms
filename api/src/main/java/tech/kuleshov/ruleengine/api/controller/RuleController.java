package tech.kuleshov.ruleengine.api.controller;

import java.util.List;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import tech.kuleshov.ruleengine.api.service.RuleRetrieveService;
import tech.kuleshov.ruleengine.api.service.RuleUpdateService;
import tech.kuleshov.ruleengine.base.RuleDefinition;

@Slf4j
@RestController
@RequestMapping(value = "/rule")
public class RuleController {

  private final RuleUpdateService ruleUpdateService;
  private final RuleRetrieveService ruleRetrieveService;

  public RuleController(
      RuleUpdateService ruleUpdateService, RuleRetrieveService ruleRetrieveService) {
    this.ruleUpdateService = ruleUpdateService;
    this.ruleRetrieveService = ruleRetrieveService;
  }

  @PostMapping("/{workflowId}")
  public void submitRule(@Valid @RequestBody RuleDefinition rule) {
    log.info("rule updated: {}/{}", rule.getWorkflowId(), rule.getId());
    ruleUpdateService.updateRule(rule);
  }

  @GetMapping("/{workflowId}")
  public List<RuleDefinition> listRoles(@PathVariable String workflowId) {
    return ruleRetrieveService.findAllByWorkflowId(workflowId);
  }

  @GetMapping("/{workflowId}/{ruleId}")
  public RuleDefinition getRole(@PathVariable String workflowId, @PathVariable String ruleId) {
    return ruleRetrieveService.findRuleDefinitionById(workflowId, ruleId).orElseThrow();
  }
}
