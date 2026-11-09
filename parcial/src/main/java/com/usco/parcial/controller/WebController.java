package com.usco.parcial.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/403")
    public String accesoDenegado() {
        return "error403";
    }

    @GetMapping("/admin/inicio")
    public String admin() {
        return "admin/inicio";
    }

    @GetMapping("/medico/inicio")
    public String medico() {
        return "medico/inicio";
    }

    @GetMapping("/paciente/inicio")
    public String paciente() {
        return "paciente/inicio";
    }
}