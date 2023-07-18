package tech.kuleshov.ruleengine.api.controller.errorhandling;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import tech.kuleshov.ruleengine.api.dto.ErrorCode;
import tech.kuleshov.ruleengine.api.dto.ErrorDto;
import tech.kuleshov.ruleengine.api.dto.ErrorResponseDto;
import tech.kuleshov.ruleengine.api.exception.NotFoundException;

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