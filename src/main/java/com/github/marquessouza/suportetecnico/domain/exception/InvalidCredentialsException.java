package com.github.marquessouza.suportetecnico.domain.exception;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException()
    {
        super("Invalid email or password");
    }
}
