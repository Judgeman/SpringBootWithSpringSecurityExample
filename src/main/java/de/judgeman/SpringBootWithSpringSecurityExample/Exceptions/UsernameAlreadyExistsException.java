package de.judgeman.SpringBootWithSpringSecurityExample.Exceptions;

public class UsernameAlreadyExistsException extends Exception {

    public UsernameAlreadyExistsException(String message) {
        super(message);
    }
}