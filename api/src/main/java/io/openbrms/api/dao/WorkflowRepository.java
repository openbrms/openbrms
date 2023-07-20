package io.openbrms.api.dao;

import java.util.*;

import io.openbrms.api.entity.Workflow;
import org.springframework.data.repository.CrudRepository;

public interface WorkflowRepository extends CrudRepository<Workflow, String> {
    List<Workflow> findAll();
}
