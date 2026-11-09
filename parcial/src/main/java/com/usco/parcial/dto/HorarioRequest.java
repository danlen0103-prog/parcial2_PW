package com.usco.parcial.dto;

import java.time.LocalTime;

import jakarta.validation.constraints.NotNull;

public record HorarioRequest(@NotNull LocalTime horaInicio, @NotNull LocalTime horaFin) {
}