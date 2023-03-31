package tech.kuleshov.ruleengine.api;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import tech.kuleshov.ruleengine.api.dao.RuleRepository;
import tech.kuleshov.ruleengine.api.dao.WorkflowRepository;

public class TestConfiguration {

  @Bean
  public RuleRepository ruleRepository() {
    return Mockito.mock(RuleRepository.class);
  }

  @Bean
  public WorkflowRepository workflowRepository() {
    return Mockito.mock(WorkflowRepository.class);
  }
}
