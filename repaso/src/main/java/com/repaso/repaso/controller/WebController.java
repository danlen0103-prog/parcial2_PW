package com.repaso.repaso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebController {

    @GetMapping("/login")
    public String login() {
        return "login";          // abre /WEB-INF/views/login.jsp
    }

    @GetMapping("/inicio")
    public String inicio() {
        return "inicio";         // /WEB-INF/views/inicio.jsp
    }

    @RequestMapping("/403")
    public String error403() {
        return "error403";       // /WEB-INF/views/error403.jsp
    }
}