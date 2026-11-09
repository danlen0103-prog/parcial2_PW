package com.usco.parcial.dto;

import jakarta.validation.constraints.NotBlank;

public record MedicoRequest(
        @NotBlank String nombre,
        @NotBlank String especialidad,
        @NotBlank String username,
        String password) {
}