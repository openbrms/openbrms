package tech.kuleshov.ruleengine.api.dao;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;
import tech.kuleshov.ruleengine.api.entity.Workflow;

@Component
public class WorkflowRepository {

  private static final Map<String, Workflow> workflows = new ConcurrentHashMap<>();

  public List<Workflow> findAll() {
    return new ArrayList<>(workflows.values());
  }

  public void save(Workflow workflow) {
    workflows.put(workflow.getId(), workflow);
  }

  public Optional<Workflow> findById(String workflowId) {
    return Optional.ofNullable(workflows.get(workflowId));
  }

  public void deleteById(String workflowId) {
    workflows.remove(workflowId);
  }
}
