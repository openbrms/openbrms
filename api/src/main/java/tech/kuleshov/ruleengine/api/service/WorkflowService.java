package tech.kuleshov.ruleengine.api.service;

import java.util.*;
import java.util.stream.Collectors;

import jakarta.inject.Singleton;
import tech.kuleshov.ruleengine.api.dao.WorkflowRepository;
import tech.kuleshov.ruleengine.api.dto.WorkflowRequestDto;
import tech.kuleshov.ruleengine.api.dto.WorkflowResponseDto;
import tech.kuleshov.ruleengine.api.entity.Workflow;
import tech.kuleshov.ruleengine.api.exception.NotFoundException;
import tech.kuleshov.ruleengine.domain.PageView;

@Singleton
public class WorkflowService {
    private final WorkflowRepository workflowRepository;

    public WorkflowService(WorkflowRepository workflowRepository) {
        this.workflowRepository = workflowRepository;
    }

    public PageView<WorkflowResponseDto> listWorkflows() {
        List<WorkflowResponseDto> list = workflowRepository.findAll().stream()
                .map(this::mapWorkflowToResponseDto)
                .collect(Collectors.toList());

        return PageView.<WorkflowResponseDto>builder()
                .total(list.size())
                .currentPage(1)
                .pages(1)
                .data(list)
                .build();
    }

    public void submitWorkflow(String workflowId, WorkflowRequestDto dto) {
        Workflow workflow = Workflow.builder().id(workflowId).name(dto.getName()).build();
        workflowRepository.save(workflow);
    }

    public WorkflowResponseDto getWorkflow(String workflowId) {
        return workflowRepository
                .findById(workflowId)
                .map(this::mapWorkflowToResponseDto)
                .orElseThrow(NotFoundException::new);
    }

    public void deleteWorkflow(String workflowId) {
        workflowRepository.deleteById(workflowId);
    }

    private WorkflowResponseDto mapWorkflowToResponseDto(Workflow m) {
        return WorkflowResponseDto.builder().id(m.getId()).name(m.getName()).build();
    }
}
