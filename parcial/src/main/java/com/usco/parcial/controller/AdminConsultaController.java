package com.usco.parcial.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.usco.parcial.dto.ConsultaRequest;
import com.usco.parcial.dto.ConsultaResponse;
import com.usco.parcial.dto.PacienteResponse;
import com.usco.parcial.service.ConsultaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Tag(name = "Administrador - Consultas", description = "Gestión de consultas y consultorios. Solo rol ADMINISTRADOR")
public class AdminConsultaController {

    private final ConsultaService consultaService;

    @GetMapping("/consultas")
    @Operation(summary = "Listar todas las consultas")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de consultas"),
        @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content),
        @ApiResponse(responseCode = "403", description = "Sin permisos para esta acción", content = @Content)
    })
    public List<ConsultaResponse> listar() {
        return consultaService.listarTodas();
    }

    @PostMapping("/consultas")
    @Operation(summary = "Registrar consulta", description = "Nombre del paciente máx. 40, motivo máx. 100, consultorio numérico, horario y médico")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Consulta registrada"),
        @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content),
        @ApiResponse(responseCode = "403", description = "Sin permisos para esta acción", content = @Content)
    })
    public ConsultaResponse crear(@Valid @RequestBody ConsultaRequest request) {
        return consultaService.crear(request);
    }

    @PutMapping("/consultas/{id}")
    @Operation(summary = "Editar consulta")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Consulta actualizada"),
        @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content),
        @ApiResponse(responseCode = "403", description = "Sin permisos para esta acción", content = @Content)
    })
    public ConsultaResponse actualizar(@PathVariable Long id, @Valid @RequestBody ConsultaRequest request) {
        return consultaService.actualizar(id, request);
    }

    @DeleteMapping("/consultas/{id}")
    @Operation(summary = "Eliminar consulta")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Consulta eliminada"),
        @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content),
        @ApiResponse(responseCode = "403", description = "Sin permisos para esta acción", content = @Content)
    })
    public void eliminar(@PathVariable Long id) {
        consultaService.eliminar(id);
    }

    @GetMapping("/pacientes")
    @Operation(summary = "Listar pacientes", description = "Usuarios con rol PACIENTE para asignar a una consulta")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de pacientes"),
        @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content),
        @ApiResponse(responseCode = "403", description = "Sin permisos para esta acción", content = @Content)
    })
    public List<PacienteResponse> pacientes() {
        return consultaService.listarPacientes();
    }
}