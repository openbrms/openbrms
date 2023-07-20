package io.openbrms.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EvalResultDto {

    private String ruleId;
    private VariableType type;
    private Object value;
    private boolean fits;
}
