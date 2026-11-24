package com.github.marquessouza.suportetecnico.domain.exception;

public class ClientNotFoundException extends RuntimeException {
    public ClientNotFoundException(){
        super("Client not found");
    }
}
