package com.usco.parcial.dto;

import java.time.LocalTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ConsultaRequest(
        @NotBlank @Size(max = 40) String nombrePaciente,
        @NotBlank @Size(max = 100) String motivo,
        @NotNull Integer numeroConsultorio,
        @NotNull LocalTime horaInicio,
        @NotNull LocalTime horaFin,
        @NotNull Long medicoId,
        @NotNull Long pacienteId) {
}