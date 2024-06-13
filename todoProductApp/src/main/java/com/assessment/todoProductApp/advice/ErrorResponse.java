package com.assessment.todoProductApp.advice;

import lombok.Data;

@Data
class ErrorResponse {
    private String errorCode;
    private String message;    
}