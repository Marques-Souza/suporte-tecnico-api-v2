package com.github.marquessouza.suportetecnico.application.port;

import com.github.marquessouza.suportetecnico.domain.model.User;

public interface TokenGenerator {


    String generateToken(User user);
}
