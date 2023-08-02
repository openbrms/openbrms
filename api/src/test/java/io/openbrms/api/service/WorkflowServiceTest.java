package io.openbrms.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.openbrms.api.dao.WorkflowRepository;
import io.openbrms.api.dto.WorkflowRequestDto;
import io.openbrms.api.dto.WorkflowResponseDto;
import io.openbrms.api.entity.Workflow;

import java.util.ArrayList;
import java.util.Optional;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class WorkflowServiceTest {

    @Mock
    WorkflowRepository workflowRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Method under test: {@link WorkflowService#listWorkflows()}
     */
    @Test
    void testListWorkflows() {
        PanacheQuery all = Mockito.mock(PanacheQuery.class);
        Mockito.doReturn(new ArrayList<>()).when(all).list();
        Mockito.doReturn(all).when(workflowRepository).findAll();
        WorkflowService workflowService = new WorkflowService(workflowRepository);
        workflowService.listWorkflows();
        verify(workflowRepository).findAll();
    }

    /**
     * Method under test:
     * {@link WorkflowService#submitWorkflow(String, WorkflowRequestDto)}
     */
    @Test
    void testSubmitWorkflow() {
        Mockito.doReturn(Optional.empty()).when(workflowRepository).findById(Mockito.anyString());
        WorkflowService workflowService = new WorkflowService(workflowRepository);
        workflowService.submitWorkflow("test", new WorkflowRequestDto("test-name"));
        verify(workflowRepository).persist(Mockito.<Workflow>any());
    }

    /**
     * Method under test:
     * {@link WorkflowService#submitWorkflow(String, WorkflowRequestDto)}
     */
    @Test
    void testSubmitWorkflow2() {
        Workflow workflow = Workflow.builder().id("test-rule1").name("test-name").build();
        Mockito.doReturn(Optional.of(workflow)).when(workflowRepository).findById(Mockito.anyString());
        WorkflowService workflowService = new WorkflowService(workflowRepository);
        workflowService.submitWorkflow("test", new WorkflowRequestDto("test-name"));
        verify(workflowRepository).persist(Mockito.<Workflow>any());
    }

    /**
     * Method under test: {@link WorkflowService#getWorkflow(String)}
     */
    @Test
    void testGetWorkflow() {
        Workflow workflow = Workflow.builder().id("test-rule1").name("test-name").build();
        when(workflowRepository.findById(Mockito.<String>any())).thenReturn(Optional.of(workflow));
        WorkflowResponseDto actualWorkflow = (new WorkflowService(workflowRepository)).getWorkflow("42");
        assertEquals("test-rule1", actualWorkflow.getId());
        assertEquals("test-name", actualWorkflow.getName());
        verify(workflowRepository).findById(Mockito.<String>any());
    }

    /**
     * Method under test: {@link WorkflowService#deleteWorkflow(String)}
     */
    @Test
    void testDeleteWorkflow() {
        doNothing().when(workflowRepository).deleteById(Mockito.<String>any());
        (new WorkflowService(workflowRepository)).deleteWorkflow("test");
        verify(workflowRepository).deleteById(Mockito.<String>any());
    }
}
