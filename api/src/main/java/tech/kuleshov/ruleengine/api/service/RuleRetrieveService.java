package tech.kuleshov.ruleengine.api.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import tech.kuleshov.ruleengine.api.dao.RuleRepository;
import tech.kuleshov.ruleengine.base.RuleDefinition;

@Service
public class RuleRetrieveService {

  private final RuleRepository ruleRepository;

  public RuleRetrieveService(RuleRepository ruleRepository) {
    this.ruleRepository = ruleRepository;
  }

  public List<RuleDefinition> findAllByWorkflowId(String workflowId) {
    return ruleRepository.findAllByWorkflowId(workflowId);
  }

  public Optional<RuleDefinition> findRuleDefinitionById(String workflowId, String ruleId) {
    return ruleRepository.findByWorkflowIdAndId(workflowId, ruleId);
  }
}
