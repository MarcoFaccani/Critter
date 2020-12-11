package com.udacity.jdnd.course3.critter.pet.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "pet not found")
public class PetNotFoundException extends RuntimeException {

    public PetNotFoundException() {
        super();
    }

    public PetNotFoundException(String message) {
        super(message);
    }

}
