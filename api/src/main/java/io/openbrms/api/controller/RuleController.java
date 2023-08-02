package io.openbrms.api.controller;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import lombok.extern.slf4j.Slf4j;
import org.jboss.resteasy.reactive.RestPath;
import io.openbrms.api.exception.NotFoundException;
import io.openbrms.api.service.RuleRetrieveService;
import io.openbrms.api.service.RuleUpdateService;
import io.openbrms.base.RuleDefinition;

@Slf4j
@Path(value = "/rule")
public class RuleController {

    private final RuleUpdateService ruleUpdateService;
    private final RuleRetrieveService ruleRetrieveService;

    public RuleController(
            RuleUpdateService ruleUpdateService, RuleRetrieveService ruleRetrieveService) {
        this.ruleUpdateService = ruleUpdateService;
        this.ruleRetrieveService = ruleRetrieveService;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{workflowId}")
    public void submitRule(@Valid RuleDefinition rule) {
        log.info("rule updated: {}/{}", rule.getWorkflowId(), rule.getId());
        ruleUpdateService.updateRule(rule);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{workflowId}")
    public List<RuleDefinition> listRules(@RestPath String workflowId) {
        return ruleRetrieveService.listRulesByWorkflowId(workflowId);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{workflowId}/{ruleId}")
    public RuleDefinition getRule(@RestPath String workflowId, @RestPath String ruleId) {
        return ruleRetrieveService
                .findRuleDefinitionById(workflowId, ruleId)
                .orElseThrow(NotFoundException::new);
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{workflowId}/{ruleId}")
    public void deleteRule(@RestPath String workflowId, @RestPath String ruleId) {
        ruleUpdateService.deleteRule(workflowId, ruleId);
    }

}
