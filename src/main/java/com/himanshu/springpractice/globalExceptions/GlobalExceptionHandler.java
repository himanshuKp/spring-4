package com.himanshu.springpractice.globalExceptions;

import com.himanshu.springpractice.model.ErrorResponse;
import com.himanshu.springpractice.model.ResourceNotFoundException;
import org.jspecify.annotations.Nullable;
import org.springframework.http.*;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected @Nullable ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ProblemDetail errorResponse = ProblemDetail.forStatusAndDetail(
                status,
                ex.getMessage()
        );

        errorResponse.setTitle("Resource don't exists");
        errorResponse.setProperty("timestamp", LocalDateTime.now());

        return  new ResponseEntity<>(errorResponse, headers, status);
    }

    @Override
    protected @Nullable ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
                                                                                   HttpHeaders headers,
                                                                                   HttpStatusCode status,
                                                                                   WebRequest request) {
        ProblemDetail errorResponse = ProblemDetail.forStatusAndDetail(
                status,
                ex.getMessage()
        );
        errorResponse.setTitle("Resource not found");
        errorResponse.setProperty("timestamp",LocalDateTime.now());

        return new ResponseEntity<>(errorResponse, headers, status);
    }

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    public ResponseEntity<ErrorResponse> handlerResourceNotFound(ResourceNotFoundException exception){
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                exception.getMessage()
        );

        LocalDateTime currentDateTime = LocalDateTime.now();
        errorResponse.setTimestamp(currentDateTime);

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception exception){
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                exception.getMessage()
        );

        LocalDateTime currentDateTime = LocalDateTime.now();
        errorResponse.setTimestamp(currentDateTime);

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
