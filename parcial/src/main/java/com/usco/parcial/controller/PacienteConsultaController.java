package com.usco.parcial.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.usco.parcial.dto.ConsultaResponse;
import com.usco.parcial.dto.HorarioRequest;
import com.usco.parcial.service.ConsultaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/paciente")
@RequiredArgsConstructor
@Tag(name = "Paciente", description = "Consulta y actualización de horario de sus citas. Rol PACIENTE")
public class PacienteConsultaController {

    private final ConsultaService consultaService;

    @GetMapping("/consultas")
    @Operation(summary = "Ver mis citas", description = "Devuelve únicamente las citas asignadas al paciente autenticado")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Citas del paciente"),
        @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content),
        @ApiResponse(responseCode = "403", description = "Sin permisos para esta acción", content = @Content)
    })
    public List<ConsultaResponse> misConsultas(Principal principal) {
        return consultaService.listarDePaciente(principal.getName());
    }

    @PutMapping("/consultas/{id}/horario")
    @Operation(summary = "Actualizar horario de mi cita", description = "Solo permite modificar citas asignadas al paciente autenticado")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Horario actualizado"),
        @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content),
        @ApiResponse(responseCode = "403", description = "La cita no está asignada a este paciente o no tiene permisos", content = @Content)
    })
    public ConsultaResponse actualizarHorario(@PathVariable Long id, @Valid @RequestBody HorarioRequest request,
            Principal principal) {
        return consultaService.actualizarHorario(principal.getName(), id, request);
    }
}