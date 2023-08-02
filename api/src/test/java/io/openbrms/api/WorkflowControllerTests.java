package io.openbrms.api;

import io.openbrms.api.dto.WorkflowRequestDto;
import io.openbrms.api.service.WorkflowService;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.apache.http.util.Asserts;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@QuarkusTest
class WorkflowControllerTests {

    @Inject
    WorkflowService workflowService;

    @Test
    public void listWorkflowsTest() {
        workflowService.submitWorkflow("test", WorkflowRequestDto.builder().name("is_ok").build());

        given()
                .when().get("/workflow")
                .then()
                .statusCode(200)
                .body("data[0].id", is("test"), "data[0].name", is("is_ok"));

    }

    @Test
    public void submitWorkflowTest() {
        given()
                .when()
                .header("Content-type", "application/json")
                .body(WorkflowRequestDto.builder().name("submitted").build())
                .post("/workflow/test")
                .then()
                .statusCode(204);

        Asserts.check(workflowService.getWorkflow("test").getName().equals("submitted"), "submit workflow error");
    }

    @Test
    public void getWorkflowByIdTest() {
        workflowService.submitWorkflow("test", WorkflowRequestDto.builder().name("is_ok").build());

        given()
                .when().get("/workflow/test")
                .then()
                .statusCode(200)
                .body("id", is("test"), "name", is("is_ok"));
    }

    @Test
    public void deleteWorkflowTest() {
        workflowService.submitWorkflow("test", WorkflowRequestDto.builder().name("is_ok").build());

        given()
                .when().delete("/workflow/test")
                .then()
                .statusCode(204);

    }

}
