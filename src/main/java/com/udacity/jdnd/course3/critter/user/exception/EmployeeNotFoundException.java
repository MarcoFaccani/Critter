package com.udacity.jdnd.course3.critter.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Employee not found")
public class EmployeeNotFoundException extends RuntimeException {

    public EmployeeNotFoundException() {}

    public  EmployeeNotFoundException(String message) {
        super(message);
    }
}
