package tech.kuleshov.ruleengine.api.dao;

import java.util.*;
import org.springframework.data.repository.CrudRepository;
import tech.kuleshov.ruleengine.api.entity.Workflow;

public interface WorkflowRepository extends CrudRepository<Workflow, String> {
  List<Workflow> findAll();
}
