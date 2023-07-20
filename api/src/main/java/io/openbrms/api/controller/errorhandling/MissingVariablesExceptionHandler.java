package io.openbrms.api.controller.errorhandling;

import io.openbrms.api.dto.ErrorCode;
import io.openbrms.api.dto.ErrorDto;
import io.openbrms.api.dto.ErrorResponseDto;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import io.openbrms.base.exception.MissingVariablesException;

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