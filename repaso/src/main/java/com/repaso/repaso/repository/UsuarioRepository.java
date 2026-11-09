package com.repaso.repaso.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.repaso.repaso.entity.Usuarioo;

public interface UsuarioRepository extends JpaRepository<Usuarioo, Integer> {
    Optional<Usuarioo> findByUsername(String username);
}