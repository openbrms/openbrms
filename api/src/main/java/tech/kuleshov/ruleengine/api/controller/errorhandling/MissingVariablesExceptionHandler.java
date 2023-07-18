package tech.kuleshov.ruleengine.api.controller.errorhandling;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import tech.kuleshov.ruleengine.api.dto.ErrorCode;
import tech.kuleshov.ruleengine.api.dto.ErrorDto;
import tech.kuleshov.ruleengine.api.dto.ErrorResponseDto;
import tech.kuleshov.ruleengine.base.exception.MissingVariablesException;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Provider
public class MissingVariablesExceptionHandler implements ExceptionMapper<MissingVariablesException> {

    @Override
    public Response toResponse(MissingVariablesException e) {
        List<ErrorDto> errors = e.getMissingVariables().stream()
                .map(var -> ErrorDto.builder().errorCode(ErrorCode.MISSING_VARIABLE).field(var).build())
                .collect(toList());

        ErrorResponseDto error = ErrorResponseDto.builder().errors(errors).build();
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(error).build();
    }
}