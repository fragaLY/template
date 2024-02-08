package com.epam.trial.configuration.exception;

import org.springframework.http.HttpStatus;

public record ExceptionInformation(HttpStatus status, Integer code, String message) {

}