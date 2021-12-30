package com.ahirajustice.retail.middleware;

import com.ahirajustice.retail.exceptions.ApplicationDomainException;
import com.ahirajustice.retail.exceptions.NotFoundException;
import com.ahirajustice.retail.exceptions.SystemErrorException;
import com.ahirajustice.retail.viewmodels.error.ErrorResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(Exception ex, WebRequest request) {
        if (ex instanceof ApplicationDomainException) {
            return handleApplicationDomainException((ApplicationDomainException) ex);
        } else if (ex instanceof NoHandlerFoundException) {
            NotFoundException result = new NotFoundException("Route does not exist");
            return new ResponseEntity<ErrorResponse>(result.toErrorResponse(),
                    HttpStatus.valueOf(result.getStatusCode()));
        } else {
            SystemErrorException result = new SystemErrorException();
            return new ResponseEntity<ErrorResponse>(result.toErrorResponse(),
                    HttpStatus.valueOf(result.getStatusCode()));
        }
    }

    private ResponseEntity<ErrorResponse> handleApplicationDomainException(ApplicationDomainException ex) {
        return new ResponseEntity<ErrorResponse>(ex.toErrorResponse(), HttpStatus.valueOf(ex.getStatusCode()));
    }

}
