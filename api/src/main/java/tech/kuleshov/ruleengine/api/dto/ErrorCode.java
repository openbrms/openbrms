package tech.kuleshov.ruleengine.api.dto;

public enum ErrorCode {
  HTTP_METHOD_NOT_SUPPORTED,
  HTTP_MEDIA_TYPE_NOT_SUPPORTED,
  VALIDATION_ERROR,
  MISSING_VARIABLE,
  INTERNAL_ERROR,
  NOT_FOUND,
  PAYLOAD_ERROR
}
