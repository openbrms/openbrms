package tech.kuleshov.ruleengine.api.controller.errorhandling;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import tech.kuleshov.ruleengine.api.dto.ErrorCode;
import tech.kuleshov.ruleengine.api.dto.ErrorDto;
import tech.kuleshov.ruleengine.api.dto.ErrorResponseDto;
import tech.kuleshov.ruleengine.base.RuleParseException;

import java.util.List;

@Provider
public class RuleParseExceptionHandler implements ExceptionMapper<RuleParseException> {

    @Override
    public Response toResponse(RuleParseException e) {
        ErrorResponseDto error = ErrorResponseDto.builder()
                .errors(
                        List.of(
                                ErrorDto.builder()
                                        .errorCode(ErrorCode.INTERNAL_ERROR)
                                        .message("Rule parse exception")
                                        .field(
                                                String.format(
                                                        "workflowId=%s, ruleId=%s",
                                                        e.getWorkflowId(), e.getRuleId()))
                                        .build()))
                .build();
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(error).build();
    }
}