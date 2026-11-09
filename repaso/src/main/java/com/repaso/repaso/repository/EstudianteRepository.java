package com.repaso.repaso.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.repaso.repaso.entity.estudiante;

public interface EstudianteRepository extends JpaRepository<estudiante, Long> {
}