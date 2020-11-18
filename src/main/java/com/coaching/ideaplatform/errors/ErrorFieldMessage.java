package com.coaching.ideaplatform.errors;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorFieldMessage {
    private String fieldName;
    private String message;
}
