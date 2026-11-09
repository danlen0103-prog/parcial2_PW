package com.repaso.repaso.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.repaso.repaso.entity.Rol;

public interface RolRepository extends JpaRepository<Rol, Integer> {
}