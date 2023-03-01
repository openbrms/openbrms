package tech.kuleshov.ruleengine.api.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import tech.kuleshov.ruleengine.api.dao.RuleRepository;
import tech.kuleshov.ruleengine.base.RuleDefinition;

@Service
public class RuleRetriveService {

  private final RuleRepository ruleRepository;

  public RuleRetriveService(RuleRepository ruleRepository) {
    this.ruleRepository = ruleRepository;
  }

  public List<RuleDefinition> findAll() {
    return ruleRepository.findAll();
  }

  public Optional<RuleDefinition> findById(String id) {
    return ruleRepository.findById(id);
  }
}
