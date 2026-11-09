package com.usco.parcial.service;

import java.time.LocalTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.usco.parcial.dto.ConsultaRequest;
import com.usco.parcial.dto.ConsultaResponse;
import com.usco.parcial.dto.HorarioRequest;
import com.usco.parcial.dto.PacienteResponse;
import com.usco.parcial.entity.Consulta;
import com.usco.parcial.entity.Medico;
import com.usco.parcial.entity.Rol;
import com.usco.parcial.entity.Usuario;
import com.usco.parcial.repository.ConsultaRepository;
import com.usco.parcial.repository.MedicoRepository;
import com.usco.parcial.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConsultaService {

    private final ConsultaRepository consultaRepository;
    private final MedicoRepository medicoRepository;
    private final UsuarioRepository usuarioRepository;

    public List<ConsultaResponse> listarTodas() {
        return consultaRepository.findAll().stream().map(this::aRespuesta).toList();
    }

    public List<ConsultaResponse> listarDeMedico(String username) {
        return consultaRepository.findByMedicoUsuarioUsername(username).stream().map(this::aRespuesta).toList();
    }

    public List<ConsultaResponse> listarDePaciente(String username) {
        return consultaRepository.findByPacienteUsername(username).stream().map(this::aRespuesta).toList();
    }

    public List<PacienteResponse> listarPacientes() {
        return usuarioRepository.findByRol(Rol.PACIENTE).stream()
                .map(u -> new PacienteResponse(u.getId(), u.getNombre(), u.getUsername()))
                .toList();
    }

    public ConsultaResponse crear(ConsultaRequest r) {
        return aRespuesta(consultaRepository.save(aEntidad(new Consulta(), r)));
    }

    public ConsultaResponse actualizar(Long id, ConsultaRequest r) {
        return aRespuesta(consultaRepository.save(aEntidad(buscar(id), r)));
    }

    public void eliminar(Long id) {
        consultaRepository.delete(buscar(id));
    }

    public ConsultaResponse actualizarHorario(String username, Long id, HorarioRequest r) {
        Consulta c = buscar(id);
        if (!c.getPaciente().getUsername().equals(username)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Solo puede modificar las citas asignadas a usted");
        }
        validarHorario(r.horaInicio(), r.horaFin());
        c.setHoraInicio(r.horaInicio());
        c.setHoraFin(r.horaFin());
        return aRespuesta(consultaRepository.save(c));
    }

    private Consulta buscar(Long id) {
        return consultaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Consulta no encontrada"));
    }

    private Consulta aEntidad(Consulta c, ConsultaRequest r) {
        validarHorario(r.horaInicio(), r.horaFin());
        Medico medico = medicoRepository.findById(r.medicoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Médico no encontrado"));
        Usuario paciente = usuarioRepository.findById(r.pacienteId())
                .filter(u -> u.getRol() == Rol.PACIENTE)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente no encontrado"));
        c.setNombrePaciente(r.nombrePaciente());
        c.setMotivo(r.motivo());
        c.setNumeroConsultorio(r.numeroConsultorio());
        c.setHoraInicio(r.horaInicio());
        c.setHoraFin(r.horaFin());
        c.setMedico(medico);
        c.setPaciente(paciente);
        return c;
    }

    private void validarHorario(LocalTime inicio, LocalTime fin) {
        if (!fin.isAfter(inicio)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La hora de fin debe ser posterior a la de inicio");
        }
    }

    private ConsultaResponse aRespuesta(Consulta c) {
        return new ConsultaResponse(c.getId(), c.getNombrePaciente(), c.getMotivo(), c.getNumeroConsultorio(),
                c.getHoraInicio(), c.getHoraFin(), c.getMedico().getId(), c.getMedico().getNombre(),
                c.getPaciente().getId());
    }
}