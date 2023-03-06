package tech.kuleshov.ruleengine.api.controller;

import org.springframework.web.bind.annotation.*;
import tech.kuleshov.ruleengine.api.dto.WorkflowRequestDto;
import tech.kuleshov.ruleengine.api.dto.WorkflowResponseDto;
import tech.kuleshov.ruleengine.api.service.WorkflowService;
import tech.kuleshov.ruleengine.domain.PageView;

@RestController
@RequestMapping("/workflow")
public class WorkflowController {

  private final WorkflowService workflowService;

  public WorkflowController(WorkflowService workflowService) {
    this.workflowService = workflowService;
  }

  @PostMapping("/{workflowId}")
  public void submitWorkflow(@PathVariable String workflowId, @RequestBody WorkflowRequestDto dto) {
    workflowService.submitWorkflow(workflowId, dto);
  }

  @GetMapping("/{workflowId}")
  public WorkflowResponseDto getWorkflow(@PathVariable String workflowId) {
    return workflowService.getWorkflow(workflowId);
  }

  @DeleteMapping("/{workflowId}")
  public void deleteWorkflow(@PathVariable String workflowId) {
    workflowService.deleteWorkflow(workflowId);
  }

  @GetMapping
  public PageView<WorkflowResponseDto> listWorkflows() {
    return workflowService.listWorkflows();
  }
}
