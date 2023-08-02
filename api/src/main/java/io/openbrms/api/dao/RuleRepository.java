package io.openbrms.api.dao;

import java.util.*;
import io.openbrms.api.entity.Rule;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RuleRepository implements PanacheRepository<Rule> {
    public List<Rule> findAllByWorkflowId(String workflowId) {
        return find("workflowId", workflowId).list();
    }

    public Optional<Rule> findByWorkflowIdAndId(String workflowId, String ruleId) {
        return find("workflowId=:workflowId and id=:ruleId", Map.of("workflowId", workflowId, "ruleId", ruleId))
                .firstResultOptional();
    }

    public void deleteByWorkflowIdAndId(String workflowId, String ruleId) {
        delete("workflowId=:workflowId and id=:ruleId", Map.of("workflowId", workflowId, "ruleId", ruleId));
    }
}
