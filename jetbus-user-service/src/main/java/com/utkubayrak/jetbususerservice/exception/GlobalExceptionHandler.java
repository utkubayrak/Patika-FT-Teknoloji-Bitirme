package com.utkubayrak.jetbususerservice.exception;

import com.utkubayrak.jetbususerservice.dto.response.GenericResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserException.class)
    public GenericResponse handleBlogHubException(UserException userException){
        return GenericResponse.failed(userException.getMessage());
    }

}
