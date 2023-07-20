package io.openbrms.api.controller.errorhandling;

import io.openbrms.api.dto.ErrorCode;
import io.openbrms.api.dto.ErrorDto;
import io.openbrms.api.dto.ErrorResponseDto;
import io.openbrms.api.exception.NotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.List;

@Provider
public class NotFoundExceptionHandler implements ExceptionMapper<NotFoundException> {

    @Override
    public Response toResponse(NotFoundException e) {
        ErrorResponseDto error = ErrorResponseDto.builder()
                .errors(
                        List.of(
                                ErrorDto.builder()
                                        .errorCode(ErrorCode.NOT_FOUND)
                                        .message("Resource not found")
                                        .build()))
                .build();
        return Response.status(Response.Status.NOT_FOUND)
                .entity(error).build();
    }
}