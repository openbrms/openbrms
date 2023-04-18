package tech.kuleshov.ruleengine.api.controller;

import static java.util.stream.Collectors.toList;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import tech.kuleshov.ruleengine.api.dto.ErrorCode;
import tech.kuleshov.ruleengine.api.dto.ErrorDto;
import tech.kuleshov.ruleengine.api.dto.ErrorResponseDto;
import tech.kuleshov.ruleengine.api.exception.NotFoundException;
import tech.kuleshov.ruleengine.base.RuleParseException;
import tech.kuleshov.ruleengine.base.exception.MissingVariablesException;
import tech.kuleshov.ruleengine.domain.UnknownVariableTypeException;

@Slf4j
@ControllerAdvice
public class ExceptionHandlingAdvice {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDto> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException exception, HttpServletRequest request) {
        ErrorResponseDto error = ErrorResponseDto.builder()
                .errors(
                        List.of(
                                ErrorDto.builder()
                                        .errorCode(ErrorCode.PAYLOAD_ERROR)
                                        .message("Can't read payload")
                                        .build()))
                .build();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponseDto> handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException exception, HttpServletRequest request) {
        ErrorResponseDto error = ErrorResponseDto.builder()
                .errors(
                        List.of(
                                ErrorDto.builder()
                                        .errorCode(ErrorCode.HTTP_METHOD_NOT_SUPPORTED)
                                        .message("Http request method not supported")
                                        .build()))
                .build();
        HttpStatus status = HttpStatus.METHOD_NOT_ALLOWED;
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErrorResponseDto> handleHttpMediaTypeNotSupportedException(
            HttpMediaTypeNotSupportedException exception, HttpServletRequest request) {
        ErrorResponseDto error = ErrorResponseDto.builder()
                .errors(
                        List.of(
                                ErrorDto.builder()
                                        .errorCode(ErrorCode.HTTP_MEDIA_TYPE_NOT_SUPPORTED)
                                        .message("Http media type not supported")
                                        .build()))
                .build();
        HttpStatus status = HttpStatus.UNSUPPORTED_MEDIA_TYPE;
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exception, HttpServletRequest request) {

        List<ErrorDto> errors = exception.getAllErrors().stream()
                .map(
                        error -> ErrorDto.builder()
                                .errorCode(ErrorCode.VALIDATION_ERROR)
                                .message(error.getDefaultMessage())
                                .field(error.getObjectName())
                                .build())
                .collect(toList());

        ErrorResponseDto error = ErrorResponseDto.builder().errors(errors).build();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(MissingVariablesException.class)
    public ResponseEntity<ErrorResponseDto> handleMissingVariablesException(
            MissingVariablesException exception, HttpServletRequest request) {

        List<ErrorDto> errors = exception.getMissingVariables().stream()
                .map(var -> ErrorDto.builder().errorCode(ErrorCode.MISSING_VARIABLE).field(var).build())
                .collect(toList());

        ErrorResponseDto error = ErrorResponseDto.builder().errors(errors).build();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(UnknownVariableTypeException.class)
    public ResponseEntity<ErrorResponseDto> handleUnknownVariableTypeException(
            UnknownVariableTypeException exception, HttpServletRequest request) {
        ErrorResponseDto error = ErrorResponseDto.builder()
                .errors(
                        List.of(
                                ErrorDto.builder()
                                        .errorCode(ErrorCode.INTERNAL_ERROR)
                                        .message("Unknown variable type (casting error)")
                                        .field(exception.getType())
                                        .build()))
                .build();
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(RuleParseException.class)
    public ResponseEntity<ErrorResponseDto> handleRuleParseException(
            RuleParseException exception, HttpServletRequest request) {
        ErrorResponseDto error = ErrorResponseDto.builder()
                .errors(
                        List.of(
                                ErrorDto.builder()
                                        .errorCode(ErrorCode.INTERNAL_ERROR)
                                        .message("Rule parse exception")
                                        .field(
                                                String.format(
                                                        "workflowId=%s, ruleId=%s",
                                                        exception.getWorkflowId(), exception.getRuleId()))
                                        .build()))
                .build();
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleNotFound(
            NotFoundException exception, HttpServletRequest request) {
        ErrorResponseDto error = ErrorResponseDto.builder()
                .errors(
                        List.of(
                                ErrorDto.builder()
                                        .errorCode(ErrorCode.NOT_FOUND)
                                        .message("Resource not found")
                                        .build()))
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}
