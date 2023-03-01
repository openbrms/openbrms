package tech.kuleshov.ruleengine.api.controller;

import java.util.List;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.*;
import tech.kuleshov.ruleengine.api.service.RuleRetriveService;
import tech.kuleshov.ruleengine.api.service.RuleUpdateService;
import tech.kuleshov.ruleengine.base.RuleDefinition;

@RestController
@RequestMapping(value = "/rule")
public class RuleController {

  private final RuleUpdateService ruleUpdateService;
  private final RuleRetriveService ruleRetriveService;

  public RuleController(
      RuleUpdateService ruleUpdateService, RuleRetriveService ruleRetriveService) {
    this.ruleUpdateService = ruleUpdateService;
    this.ruleRetriveService = ruleRetriveService;
  }

  @PostMapping("/")
  public void submitRule(@Valid @RequestBody RuleDefinition rule) {
    ruleUpdateService.updateRule(rule);
  }

  @GetMapping("/")
  public List<RuleDefinition> listRoles() {
    return ruleRetriveService.findAll();
  }

  @GetMapping("/{id}")
  public RuleDefinition getRole(@PathVariable String id) {
    return ruleRetriveService.findById(id).orElseThrow();
  }
}
