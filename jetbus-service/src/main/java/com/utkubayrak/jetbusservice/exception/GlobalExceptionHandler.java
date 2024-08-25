package com.utkubayrak.jetbusservice.exception;

import com.utkubayrak.jetbusservice.dto.response.GenericResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(JetbusException.class)
    public GenericResponse handleBlogHubException(JetbusException userException){
        return GenericResponse.failed(userException.getMessage());
    }
}
