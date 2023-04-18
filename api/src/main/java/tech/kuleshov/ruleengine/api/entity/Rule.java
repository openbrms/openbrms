package tech.kuleshov.ruleengine.api.entity;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import tech.kuleshov.ruleengine.base.VariableDefinition;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class Rule {

    @Id
    private String id;

    @Column(name = "workflow_id")
    private String workflowId;

    @Type(type = "jsonb")
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
