package tech.kuleshov.ruleengine.api.dao;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;
import tech.kuleshov.ruleengine.base.RuleDefinition;

@Repository
public class RuleRepository {

  private static final Map<String, Map<String, RuleDefinition>> rules = new ConcurrentHashMap<>();

  public void save(String workflowId, RuleDefinition rule) {
    if (!rules.containsKey(workflowId)) {
      rules.put(workflowId, new ConcurrentHashMap<>());
    }

    Map<String, RuleDefinition> workflowMap = rules.get(workflowId);
    workflowMap.put(rule.getId(), rule);
  }

  public List<RuleDefinition> findAllByWorkflowId(String workflowId) {
    return new ArrayList<>(rules.getOrDefault(workflowId, new HashMap<>()).values());
  }

  public Optional<RuleDefinition> findByWorkflowIdAndId(String workflowId, String ruleId) {
    Map<String, RuleDefinition> workflowMap = rules.getOrDefault(workflowId, new HashMap<>());
    return Optional.ofNullable(workflowMap.get(ruleId));
  }
}
