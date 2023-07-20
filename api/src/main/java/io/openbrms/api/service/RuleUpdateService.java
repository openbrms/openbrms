package io.openbrms.api.service;

import jakarta.inject.Singleton;
import io.openbrms.api.dao.RuleRepository;
import io.openbrms.api.entity.Rule;
import io.openbrms.base.RuleDefinition;

@Singleton
public class RuleUpdateService {

    private final RuleRepository ruleRepository;

    public RuleUpdateService(RuleRepository ruleRepository) {
        this.ruleRepository = ruleRepository;
    }

    public void updateRule(RuleDefinition rd) {
        // todo: checks

        Rule rule = Rule.builder()
                .id(rd.getId())
                .workflowId(rd.getWorkflowId())
                .when(rd.getWhen())
                .then(rd.getThen())
                .required(rd.isRequired())
                .variables(rd.getVariables())
                .build();

        ruleRepository.save(rule);
    }
}
