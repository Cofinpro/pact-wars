package de.cofinpro.planets.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NotOnPlanetException extends RuntimeException {

    public NotOnPlanetException() {
    }

    public NotOnPlanetException(String message) {
        super(message);
    }

}
