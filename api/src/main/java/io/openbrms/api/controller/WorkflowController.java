package io.openbrms.api.controller;

import io.openbrms.api.dto.WorkflowRequestDto;
import io.openbrms.api.service.WorkflowService;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.reactive.RestPath;
import io.openbrms.api.dto.WorkflowResponseDto;
import io.openbrms.domain.PageView;

@Path("/workflow")
public class WorkflowController {

    private final WorkflowService workflowService;

    public WorkflowController(WorkflowService workflowService) {
        this.workflowService = workflowService;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{workflowId}")
    public void submitWorkflow(@RestPath String workflowId, @Valid WorkflowRequestDto dto) {
        workflowService.submitWorkflow(workflowId, dto);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{workflowId}")
    public WorkflowResponseDto getWorkflow(@RestPath String workflowId) {
        return workflowService.getWorkflow(workflowId);
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{workflowId}")
    public void deleteWorkflow(@RestPath String workflowId) {
        workflowService.deleteWorkflow(workflowId);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public PageView<WorkflowResponseDto> listWorkflows() {
        return workflowService.listWorkflows();
    }
}
