package com.usco.parcial.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.usco.parcial.dto.MedicoRequest;
import com.usco.parcial.dto.MedicoResponse;
import com.usco.parcial.service.MedicoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/medicos")
@RequiredArgsConstructor
@Tag(name = "Administrador - Médicos", description = "Gestión de médicos. Solo rol ADMINISTRADOR")
public class AdminMedicoController {

    private final MedicoService medicoService;

    @GetMapping
    @Operation(summary = "Listar médicos", description = "Devuelve todos los médicos registrados")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de médicos"),
        @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content),
        @ApiResponse(responseCode = "403", description = "Sin permisos para esta acción", content = @Content)
    })
    public List<MedicoResponse> listar() {
        return medicoService.listar();
    }

    @PostMapping
    @Operation(summary = "Crear médico", description = "Crea un médico y su usuario de acceso con rol MEDICO")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Médico creado"),
        @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content),
        @ApiResponse(responseCode = "403", description = "Sin permisos para esta acción", content = @Content)
    })
    public MedicoResponse crear(@Valid @RequestBody MedicoRequest request) {
        return medicoService.crear(request);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Editar médico", description = "Actualiza nombre, especialidad y opcionalmente la contraseña")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Médico actualizado"),
        @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content),
        @ApiResponse(responseCode = "403", description = "Sin permisos para esta acción", content = @Content)
    })
    public MedicoResponse actualizar(@PathVariable Long id, @Valid @RequestBody MedicoRequest request) {
        return medicoService.actualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar médico", description = "Elimina el médico, su usuario y sus consultas")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Médico eliminado"),
        @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content),
        @ApiResponse(responseCode = "403", description = "Sin permisos para esta acción", content = @Content)
    })
    public void eliminar(@PathVariable Long id) {
        medicoService.eliminar(id);
    }
}