package tech.kuleshov.ruleengine.api.controller;

import java.util.List;
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

    @PostMapping("/{workflowId}/{ruleId}")
    public EvalResultDto eval(
            @PathVariable String workflowId,
            @PathVariable String ruleId,
            @RequestBody Map<String, Object> params) {
        return ruleEngineService.eval(workflowId, ruleId, params);
    }

    @PostMapping("/{workflowId}")
    public List<EvalResultDto> eval(
            @PathVariable String workflowId, @RequestBody Map<String, Object> params) {
        return ruleEngineService.eval(workflowId, params);
    }
}
