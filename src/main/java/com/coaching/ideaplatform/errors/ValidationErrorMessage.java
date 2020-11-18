package com.coaching.ideaplatform.errors;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ValidationErrorMessage {
    private Integer statusCode;
    private List<ErrorFieldMessage> errorFieldMessages;
}
