package com.repaso.repaso.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.repaso.repaso.entity.Usuarioo;
import com.repaso.repaso.repository.UsuarioRepository;

@Service
public class UsuarioDetailsService implements UserDetailsService {

    private final UsuarioRepository repo;

    public UsuarioDetailsService(UsuarioRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuarioo u = repo.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("No existe: " + username));

        List<GrantedAuthority> permisos = u.getRoles().stream()
            .map(r -> new SimpleGrantedAuthority("ROLE_" + r.getNombre()))
            .collect(Collectors.toList());

        return new User(u.getUsername(), u.getPassword(), permisos);
    }
}