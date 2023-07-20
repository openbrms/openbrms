package io.openbrms.base;

import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RuleDefinition {

    private String workflowId;
    private String id;
    @Builder.Default
    private Map<String, VariableDefinition> variables = new HashMap<>();
    private String when;
    private String then;
    @Builder.Default
    private boolean required = true;
}
