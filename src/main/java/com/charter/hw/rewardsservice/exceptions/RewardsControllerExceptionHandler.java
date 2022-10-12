package com.charter.hw.rewardsservice.exceptions;

import com.charter.hw.rewardsservice.model.Error;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RewardsControllerExceptionHandler {

    @ExceptionHandler(value = BusinessException.class)
    public ResponseEntity<Error> handleBusinessException(BusinessException e) {
        return ResponseEntity.badRequest().body(new Error("1001", e.getErrorMessage()));
    }

    @ExceptionHandler(value = SQLDataException.class)
    public ResponseEntity<Error> handleSqlDataException(SQLDataException e) {
        return ResponseEntity.internalServerError().body(new Error("1002", e.getErrorMessage()));
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Error> handleAnyException (Exception e) {
        return ResponseEntity.internalServerError().body(new Error("1000", e.getMessage()));
    }
}
