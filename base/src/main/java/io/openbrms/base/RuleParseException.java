package io.openbrms.base;

public class RuleParseException extends RuntimeException {
    private final String workflowId;
    private final String ruleId;

    public RuleParseException(String workflowId, String ruleId) {
        this.workflowId = workflowId;
        this.ruleId = ruleId;
    }

    public String getWorkflowId() {
        return workflowId;
    }

    public String getRuleId() {
        return ruleId;
    }
}
