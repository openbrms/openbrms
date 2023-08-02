package io.openbrms.api.samples;

import io.openbrms.base.RuleDefinition;
import io.openbrms.base.VariableDefinition;
import io.openbrms.domain.VariableType;

import java.util.Map;

public class RuleTestSamples {

    public static RuleDefinition ruleTest1() {
        return RuleDefinition.builder()
                .workflowId("test")
                .id("rule1-test")
                .required(true)
                .when("true")
                .then("{:sum a+b}")
                .variables(Map.of("a", VariableDefinition.builder().type(VariableType.NUMERIC).build(), "b",
                        VariableDefinition.builder().type(VariableType.NUMERIC).build()))
                .build();
    }
}
