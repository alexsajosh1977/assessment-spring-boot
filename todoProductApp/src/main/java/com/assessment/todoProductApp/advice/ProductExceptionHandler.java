package com.assessment.todoProductApp.advice;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.assessment.todoProductApp.exceptions.ProductNotFoundException;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class ProductExceptionHandler {

	@ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
		log.info("Generic Error", ex);
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorCode("GENERIC_ERROR");
        errorResponse.setMessage(ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.SERVICE_UNAVAILABLE);
    }
	
	@ExceptionHandler(value = EmptyResultDataAccessException.class)
    public ResponseEntity<ErrorResponse>  handleEmptyResultDataAccessException(EmptyResultDataAccessException ex) {
		log.info("Empty resultset ", ex);
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorCode("No_PRODUCT_FOUND");
        errorResponse.setMessage("Please check the request parameters! "+ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
	
	@ExceptionHandler(value = ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(ProductNotFoundException ex) {
		log.info(ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorCode("PRODUCT_NOTFOUND");
        errorResponse.setMessage(ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleException(MethodArgumentNotValidException ex) {
		log.info(ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorCode("MISSING_REQUIRED_PARAMETER");        
        ex.getBindingResult().getFieldErrors().forEach(error -> {
        	 errorResponse.setMessage(error.getDefaultMessage());
        });
       
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
