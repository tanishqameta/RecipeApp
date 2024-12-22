package com.tanishq.RecipeApp.exceptions;

import com.tanishq.RecipeApp.enums.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private ErrorCode errorCode;
    private String errorMessage;
}
