package com.tanishq.RecipeApp.config;

import com.tanishq.RecipeApp.enums.ErrorCode;
import com.tanishq.RecipeApp.exceptions.ErrorResponse;
import com.tanishq.RecipeApp.exceptions.RecipeNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RecipeNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(RecipeNotFoundException ex) {
        return new ResponseEntity<ErrorResponse>(new ErrorResponse(ErrorCode.RECIPE_NOT_FOUND, ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        // Collect validation errors
        StringBuilder errorMessage = new StringBuilder();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            errorMessage.append(error.getDefaultMessage()).append("; ");
        });

        return this.handleInvalidArgumentExceptionWithMessage(errorMessage.toString());
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ErrorResponse> handleInvalidMethodArgument(HandlerMethodValidationException ex) {
        StringBuilder errorMessage = new StringBuilder();

        ex.getParameterValidationResults().forEach((parameterValidationResult) -> {
            parameterValidationResult.getResolvableErrors().forEach(messageSourceResolvable -> {
                errorMessage
                        .append(parameterValidationResult.getMethodParameter().getParameterName())
                        .append(": ")
                        .append(messageSourceResolvable.getDefaultMessage())
                        .append("\n");
            });
        });
        return this.handleInvalidArgumentExceptionWithMessage(errorMessage.toString());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR, ex.getMessage());
        System.out.println(ex.getClass());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ErrorResponse> handleInvalidArgumentExceptionWithMessage(String message) {
        return new ResponseEntity<ErrorResponse>(new ErrorResponse(ErrorCode.INVALID_ARGUMENT, message), HttpStatus.BAD_REQUEST);
    }
}
