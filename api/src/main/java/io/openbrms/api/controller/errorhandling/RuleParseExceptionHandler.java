package io.openbrms.api.controller.errorhandling;

import io.openbrms.api.dto.ErrorCode;
import io.openbrms.api.dto.ErrorDto;
import io.openbrms.api.dto.ErrorResponseDto;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import io.openbrms.base.RuleParseException;

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