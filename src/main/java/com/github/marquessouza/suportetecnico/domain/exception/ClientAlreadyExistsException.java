package com.github.marquessouza.suportetecnico.domain.exception;

public class ClientAlreadyExistsException extends RuntimeException{

    public ClientAlreadyExistsException(){
        super("A client with this CPF is already registered");
    }
}
