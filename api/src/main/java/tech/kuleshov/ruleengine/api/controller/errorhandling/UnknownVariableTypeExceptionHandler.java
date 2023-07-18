package tech.kuleshov.ruleengine.api.controller.errorhandling;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import tech.kuleshov.ruleengine.api.dto.ErrorCode;
import tech.kuleshov.ruleengine.api.dto.ErrorDto;
import tech.kuleshov.ruleengine.api.dto.ErrorResponseDto;
import tech.kuleshov.ruleengine.domain.UnknownVariableTypeException;

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