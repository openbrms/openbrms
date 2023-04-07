package tech.kuleshov.ruleengine.api.dao;

import java.util.*;
import org.springframework.data.repository.CrudRepository;
import tech.kuleshov.ruleengine.api.entity.Rule;

public interface RuleRepository extends CrudRepository<Rule, String> {
    List<Rule> findAllByWorkflowId(String workflowId);

    Optional<Rule> findByWorkflowIdAndId(String workflowId, String ruleId);
}
