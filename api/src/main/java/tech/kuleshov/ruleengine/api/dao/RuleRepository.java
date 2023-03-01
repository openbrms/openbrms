package tech.kuleshov.ruleengine.api.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;
import tech.kuleshov.ruleengine.base.RuleDefinition;

@Repository
public class RuleRepository {

  private static final Map<String, RuleDefinition> rules = new ConcurrentHashMap<>();

  public List<RuleDefinition> findAll() {
    return new ArrayList<>(rules.values());
  }

  public Optional<RuleDefinition> findById(String id) {
    return Optional.ofNullable(rules.get(id));
  }

  public void save(RuleDefinition rule) {
    rules.put(rule.getId(), rule);
  }
}
