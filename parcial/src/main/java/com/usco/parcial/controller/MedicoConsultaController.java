package com.usco.parcial.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usco.parcial.dto.ConsultaResponse;
import com.usco.parcial.service.ConsultaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/medico")
@RequiredArgsConstructor
@Tag(name = "Médico", description = "Solo lectura de sus consultas. Rol MEDICO")
public class MedicoConsultaController {

    private final ConsultaService consultaService;

    @GetMapping("/consultas")
    @Operation(summary = "Ver mis consultas", description = "Devuelve únicamente las consultas asignadas al médico autenticado")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Consultas del médico"),
        @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content),
        @ApiResponse(responseCode = "403", description = "Sin permisos para esta acción", content = @Content)
    })
    public List<ConsultaResponse> misConsultas(Principal principal) {
        return consultaService.listarDeMedico(principal.getName());
    }
}