package com.repaso.repaso.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.repaso.repaso.entity.estudiante;
import com.repaso.repaso.repository.EstudianteRepository;

@RestController
@RequestMapping("/estudiantes")
public class EstudianteController {

    @Autowired
    private EstudianteRepository repo;

    @GetMapping
    public List<estudiante> listar() {
        return repo.findAll();
    }

    @PostMapping
    public estudiante crear(@RequestBody estudiante e) {
        return repo.save(e);
    }
}