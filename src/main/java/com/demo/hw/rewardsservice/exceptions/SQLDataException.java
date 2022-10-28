package com.demo.hw.rewardsservice.exceptions;

import lombok.Data;

@Data
public class SQLDataException extends RuntimeException {

    private String errorMessage;

    public SQLDataException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }
}
