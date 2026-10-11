package com.github.marquessouza.suportetecnico.domain.exception;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException() {
        super("A user with this email already exists");
    }
}
