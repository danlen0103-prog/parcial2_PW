package com.usco.parcial.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.usco.parcial.dto.MedicoRequest;
import com.usco.parcial.dto.MedicoResponse;
import com.usco.parcial.entity.Medico;
import com.usco.parcial.entity.Rol;
import com.usco.parcial.entity.Usuario;
import com.usco.parcial.repository.ConsultaRepository;
import com.usco.parcial.repository.MedicoRepository;
import com.usco.parcial.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MedicoService {

    private final MedicoRepository medicoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ConsultaRepository consultaRepository;
    private final PasswordEncoder passwordEncoder;

    public List<MedicoResponse> listar() {
        return medicoRepository.findAll().stream().map(this::aRespuesta).toList();
    }

    @Transactional
    public MedicoResponse crear(MedicoRequest r) {
        if (r.password() == null || r.password().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La contraseña es obligatoria");
        }
        if (usuarioRepository.existsByUsername(r.username())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El usuario ya existe");
        }
        Usuario usuario = usuarioRepository.save(
                new Usuario(null, r.username(), passwordEncoder.encode(r.password()), r.nombre(), Rol.MEDICO));
        Medico medico = medicoRepository.save(new Medico(null, r.nombre(), r.especialidad(), usuario));
        return aRespuesta(medico);
    }

    @Transactional
    public MedicoResponse actualizar(Long id, MedicoRequest r) {
        Medico medico = buscar(id);
        medico.setNombre(r.nombre());
        medico.setEspecialidad(r.especialidad());
        Usuario usuario = medico.getUsuario();
        usuario.setNombre(r.nombre());
        if (r.password() != null && !r.password().isBlank()) {
            usuario.setPassword(passwordEncoder.encode(r.password()));
        }
        return aRespuesta(medico);
    }

    @Transactional
    public void eliminar(Long id) {
        Medico medico = buscar(id);
        consultaRepository.deleteByMedicoId(id);
        medicoRepository.delete(medico);
        usuarioRepository.delete(medico.getUsuario());
    }

    private Medico buscar(Long id) {
        return medicoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Médico no encontrado"));
    }

    private MedicoResponse aRespuesta(Medico m) {
        return new MedicoResponse(m.getId(), m.getNombre(), m.getEspecialidad(), m.getUsuario().getUsername());
    }
}