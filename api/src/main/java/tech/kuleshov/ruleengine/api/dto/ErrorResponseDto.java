package tech.kuleshov.ruleengine.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponseDto {

    @Builder.Default
    private String id = UUID.randomUUID().toString();
    private List<ErrorDto> errors;
}
