package io.openbrms.api.service;

import jakarta.inject.Singleton;
import io.openbrms.api.dao.RuleRepository;
import io.openbrms.api.entity.Rule;
import io.openbrms.base.RuleDefinition;
import jakarta.transaction.Transactional;

@Singleton
public class RuleUpdateService {

    private final RuleRepository ruleRepository;

    public RuleUpdateService(RuleRepository ruleRepository) {
        this.ruleRepository = ruleRepository;
    }

    @Transactional
    public void updateRule(RuleDefinition rd) {
        // todo: checks

        Rule rule = ruleRepository.findByWorkflowIdAndId(rd.getWorkflowId(), rd.getId()).orElse(Rule.builder()
                .id(rd.getId())
                .workflowId(rd.getWorkflowId())
                .build());

        rule.setWhen(rd.getWhen());
        rule.setThen(rd.getThen());
        rule.setRequired(rd.isRequired());
        rule.setVariables(rd.getVariables());

        ruleRepository.persist(rule);

    }

    @Transactional
    public void deleteRule(String workflowId, String ruleId) {
        ruleRepository.deleteByWorkflowIdAndId(workflowId, ruleId);
    }
}
