package com.usco.parcial.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.usco.parcial.entity.Consulta;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {
    List<Consulta> findByMedicoUsuarioUsername(String username);
    List<Consulta> findByPacienteUsername(String username);
    void deleteByMedicoId(Long medicoId);
}