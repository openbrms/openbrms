package io.openbrms.api.controller;

import java.util.List;
import java.util.Map;

import jakarta.validation.Valid;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.reactive.RestPath;
import io.openbrms.api.service.RuleEngineService;
import io.openbrms.domain.EvalResultDto;

@Path("/eval")
public class EvaluateController {

    private final RuleEngineService ruleEngineService;

    public EvaluateController(RuleEngineService ruleEngineService) {
        this.ruleEngineService = ruleEngineService;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{workflowId}/{ruleId}")
    public EvalResultDto eval(@RestPath String workflowId,
            @RestPath String ruleId,
            @Valid Map<String, Object> params) {
        return ruleEngineService.eval(workflowId, ruleId, params);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{workflowId}")
    public List<EvalResultDto> evalWorkflow(
            @RestPath String workflowId, @Valid Map<String, Object> params) {
        return ruleEngineService.eval(workflowId, params);
    }
}
