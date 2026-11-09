package com.usco.parcial.dto;

import java.time.LocalTime;

public record ConsultaResponse(
        Long id,
        String nombrePaciente,
        String motivo,
        Integer numeroConsultorio,
        LocalTime horaInicio,
        LocalTime horaFin,
        Long medicoId,
        String medicoNombre,
        Long pacienteId) {
}