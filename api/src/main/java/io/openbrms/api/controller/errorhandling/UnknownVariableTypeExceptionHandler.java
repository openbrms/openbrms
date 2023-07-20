package io.openbrms.api.controller.errorhandling;

import io.openbrms.api.dto.ErrorCode;
import io.openbrms.api.dto.ErrorDto;
import io.openbrms.api.dto.ErrorResponseDto;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import io.openbrms.domain.UnknownVariableTypeException;

import java.util.List;

@Provider
public class UnknownVariableTypeExceptionHandler implements ExceptionMapper<UnknownVariableTypeException> {

    @Override
    public Response toResponse(UnknownVariableTypeException e) {
        ErrorResponseDto error = ErrorResponseDto.builder()
                .errors(
                        List.of(
                                ErrorDto.builder()
                                        .errorCode(ErrorCode.INTERNAL_ERROR)
                                        .message("Unknown variable type (casting error)")
                                        .field(e.getType())
                                        .build()))
                .build();
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(error).build();
    }
}