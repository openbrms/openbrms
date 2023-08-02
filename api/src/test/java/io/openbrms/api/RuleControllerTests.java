package io.openbrms.api;

import io.openbrms.api.dto.WorkflowRequestDto;
import io.openbrms.api.samples.RuleTestSamples;
import io.openbrms.api.service.RuleRetrieveService;
import io.openbrms.api.service.RuleUpdateService;
import io.openbrms.api.service.WorkflowService;
import io.openbrms.base.RuleDefinition;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.apache.http.util.Asserts;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@QuarkusTest
class RuleControllerTests {

    @Inject
    WorkflowService workflowService;
    @Inject
    RuleRetrieveService ruleRetrieveService;
    @Inject
    RuleUpdateService ruleUpdateService;

    @Test
    public void listRulesTest() {
        workflowService.submitWorkflow("test", WorkflowRequestDto.builder().name("test").build());
        ruleUpdateService.updateRule(RuleTestSamples.ruleTest1());

        given()
                .when().get("/rule/test")
                .then()
                .statusCode(200)
                .body("[0].id", is("rule1-test"));
    }

    @Test
    public void submitRuleTest() {
        workflowService.submitWorkflow("test", WorkflowRequestDto.builder().name("test").build());

        given()
                .when()
                .header("Content-type", "application/json")
                .body(RuleTestSamples.ruleTest1())
                .post("/rule/test")
                .then()
                .statusCode(204);

        Asserts.check(ruleRetrieveService.findRuleDefinitionById("test", "rule1-test").isPresent(),
                "submit rule error");
    }

    @Test
    public void getRuleByIdTest() {
        workflowService.submitWorkflow("test", WorkflowRequestDto.builder().name("test").build());
        ruleUpdateService.updateRule(RuleTestSamples.ruleTest1());

        given()
                .when().get("/rule/test/rule1-test")
                .then()
                .statusCode(200)
                .body("id", is("rule1-test"));
    }

    @Test
    public void deleteRuleTest() {
        workflowService.submitWorkflow("test", WorkflowRequestDto.builder().name("test").build());
        ruleUpdateService.updateRule(RuleTestSamples.ruleTest1());

        given()
                .when().delete("/rule/test/rule1-test")
                .then()
                .statusCode(204);

        List<RuleDefinition> rules = ruleRetrieveService.listRulesByWorkflowId("test");
        Asserts.check(rules.size() == 0, "err rule removing");
    }

}
