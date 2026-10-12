package com.github.marquessouza.suportetecnico.application.port;

public interface PasswordHash {

    String hash(String rawPassword);

    boolean matches( String rawPassword, String passwordHas);
}
