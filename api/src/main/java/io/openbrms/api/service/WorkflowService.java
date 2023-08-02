package io.openbrms.api.service;

import java.util.*;
import java.util.stream.Collectors;

import io.openbrms.api.entity.Workflow;
import io.openbrms.api.exception.NotFoundException;
import jakarta.inject.Singleton;
import io.openbrms.api.dao.WorkflowRepository;
import io.openbrms.api.dto.WorkflowRequestDto;
import io.openbrms.api.dto.WorkflowResponseDto;
import io.openbrms.domain.PageView;
import jakarta.transaction.Transactional;

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

    @Transactional
    public void submitWorkflow(String workflowId, WorkflowRequestDto dto) {
        Workflow workflow = workflowRepository.findById(workflowId).orElse(Workflow.builder().id(workflowId).build());
        workflow.setName(dto.getName());
        workflowRepository.persist(workflow);
    }

    public WorkflowResponseDto getWorkflow(String workflowId) {
        return workflowRepository
                .findById(workflowId)
                .map(this::mapWorkflowToResponseDto)
                .orElseThrow(NotFoundException::new);
    }

    @Transactional
    public void deleteWorkflow(String workflowId) {
        workflowRepository.deleteById(workflowId);
    }

    private WorkflowResponseDto mapWorkflowToResponseDto(Workflow m) {
        return WorkflowResponseDto.builder().id(m.getId()).name(m.getName()).build();
    }
}
