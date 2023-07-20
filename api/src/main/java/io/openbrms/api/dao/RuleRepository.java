package io.openbrms.api.dao;

import java.util.*;
import org.springframework.data.repository.CrudRepository;
import io.openbrms.api.entity.Rule;

public interface RuleRepository extends CrudRepository<Rule, String> {
    List<Rule> findAllByWorkflowId(String workflowId);

    Optional<Rule> findByWorkflowIdAndId(String workflowId, String ruleId);
}
