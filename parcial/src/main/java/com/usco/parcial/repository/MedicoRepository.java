package com.usco.parcial.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.usco.parcial.entity.Medico;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
}