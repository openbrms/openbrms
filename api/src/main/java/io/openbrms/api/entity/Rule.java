package io.openbrms.api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import io.openbrms.base.VariableDefinition;

import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "rule")
public class Rule {

    @Id
    private String id;

    @Column(name = "workflow_id")
    private String workflowId;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    @Builder.Default
    private Map<String, VariableDefinition> variables = new HashMap<>();

    @Column(name = "rule_when", columnDefinition = "text")
    private String when;

    @Column(name = "rule_then", columnDefinition = "text")
    private String then;

    @Builder.Default
    private boolean required = true;
}
