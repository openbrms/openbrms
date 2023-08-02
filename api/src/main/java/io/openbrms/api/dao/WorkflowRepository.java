package io.openbrms.api.dao;

import io.openbrms.api.entity.Workflow;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
public class WorkflowRepository implements PanacheRepository<Workflow> {

    public Optional<Workflow> findById(String workflowId) {
        return find("id", workflowId).firstResultOptional();
    }

    public void deleteById(String workflowId) {
        delete("id", workflowId);
    }
}
