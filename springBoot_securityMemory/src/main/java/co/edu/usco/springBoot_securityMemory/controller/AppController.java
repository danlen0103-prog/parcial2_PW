package co.edu.usco.springBoot_securityMemory.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class AppController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/")
    public String inicio(Authentication auth, Model model) {
        List<String> roles = auth.getAuthorities().stream()
                .map(a -> a.getAuthority())
                .toList();
        model.addAttribute("username", auth.getName());
        model.addAttribute("roles", roles);
        return "inicio";
    }

    @GetMapping("/new")
    public String nuevo(Model model) {
        model.addAttribute("titulo", "Crear (new)");
        model.addAttribute("mensaje", "Acceso permitido. Esta p&aacute;gina est&aacute; disponible solo para ADMIN y CREATOR.");
        return "accion";
    }

    @GetMapping("/edit/{id}")
    public String editar(@PathVariable int id, Model model) {
        model.addAttribute("titulo", "Editar registro " + id);
        model.addAttribute("mensaje", "Acceso permitido. Esta p&aacute;gina est&aacute; disponible solo para ADMIN y EDITOR.");
        return "accion";
    }

    @GetMapping("/delete/{id}")
    public String eliminar(@PathVariable int id, Model model) {
        model.addAttribute("titulo", "Eliminar registro " + id);
        model.addAttribute("mensaje", "Acceso permitido. Esta p&aacute;gina est&aacute; disponible solo para ADMIN.");
        return "accion";
    }

    @GetMapping("/403")
    public String accesoDenegado() {
        return "403";
    }
}