package com.repaso.repaso.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HashController {

    private final PasswordEncoder encoder;

    public HashController(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    @GetMapping("/hash/{clave}")
    public String hash(@PathVariable String clave) {
        return encoder.encode(clave);
    }
}