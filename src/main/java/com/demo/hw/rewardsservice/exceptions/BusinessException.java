package com.demo.hw.rewardsservice.exceptions;

import lombok.Data;

@Data
public class BusinessException extends RuntimeException {
    private String errorMessage;

    public BusinessException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }
}
