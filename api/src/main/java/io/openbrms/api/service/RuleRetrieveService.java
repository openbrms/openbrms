package io.openbrms.api.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import jakarta.inject.Singleton;
import io.openbrms.api.dao.RuleRepository;
import io.openbrms.api.entity.Rule;
import io.openbrms.base.RuleDefinition;

@Singleton
public class RuleRetrieveService {

    private final RuleRepository ruleRepository;

    public RuleRetrieveService(RuleRepository ruleRepository) {
        this.ruleRepository = ruleRepository;
    }

    public List<RuleDefinition> listRulesByWorkflowId(String workflowId) {
        List<Rule> rule = ruleRepository.findAllByWorkflowId(workflowId);
        return rule.stream()
                .map(
                        m -> RuleDefinition.builder()
                                .id(m.getId())
                                .workflowId(m.getWorkflowId())
                                .when(m.getWhen())
                                .then(m.getThen())
                                .variables(m.getVariables())
                                .required(m.isRequired())
                                .build())
                .collect(Collectors.toList());
    }

    public Optional<RuleDefinition> findRuleDefinitionById(String workflowId, String ruleId) {
        return ruleRepository
                .findByWorkflowIdAndId(workflowId, ruleId)
                .map(
                        m -> RuleDefinition.builder()
                                .id(m.getId())
                                .workflowId(m.getWorkflowId())
                                .when(m.getWhen())
                                .then(m.getThen())
                                .variables(m.getVariables())
                                .required(m.isRequired())
                                .build());
    }
}
