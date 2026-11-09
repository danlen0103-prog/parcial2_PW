<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<title>Administrador</title>
<style>
table{border-collapse:collapse;margin:10px 0}
td,th{border:1px solid #999;padding:4px 8px}
input,select{margin:2px}
</style>
</head>
<body>
<h2>Panel del administrador</h2>
<form action="/logout" method="post">
<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
<button type="submit">Cerrar sesión</button>
</form>
<a href="/swagger-ui.html" target="_blank">Documentación Swagger</a>

<h3>Médicos</h3>
<input type="hidden" id="mId">
<input id="mNombre" placeholder="Nombre">
<input id="mEspecialidad" placeholder="Especialidad">
<input id="mUsername" placeholder="Usuario">
<input id="mPassword" type="password" placeholder="Contraseña">
<button onclick="guardarMedico()">Guardar</button>
<button onclick="limpiarMedico()">Limpiar</button>
<table>
<thead><tr><th>Nombre</th><th>Especialidad</th><th>Usuario</th><th>Acciones</th></tr></thead>
<tbody id="tablaMedicos"></tbody>
</table>

<h3>Consultas</h3>
<input type="hidden" id="cId">
<select id="cPaciente"></select>
<input id="cNombre" placeholder="Nombre del paciente" maxlength="40">
<input id="cMotivo" placeholder="Motivo de la consulta" maxlength="100">
<input id="cConsultorio" type="number" placeholder="N° consultorio">
<input id="cInicio" type="time">
<input id="cFin" type="time">
<select id="cMedico"></select>
<button onclick="guardarConsulta()">Guardar</button>
<button onclick="limpiarConsulta()">Limpiar</button>
<table>
<thead><tr><th>Paciente</th><th>Motivo</th><th>Consultorio</th><th>Horario</th><th>Médico</th><th>Acciones</th></tr></thead>
<tbody id="tablaConsultas"></tbody>
</table>

<script>
var medicos = [];
var consultas = [];

function esc(t) {
  return String(t == null ? '' : t).replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;').replace(/"/g, '&quot;');
}

function hora(t) {
  return String(t).substring(0, 5);
}

function api(url, method, body) {
  return fetch(url, {
    method: method,
    headers: { 'Content-Type': 'application/json' },
    body: body ? JSON.stringify(body) : undefined
  }).then(function (r) {
    return r.text().then(function (t) {
      var data = t ? JSON.parse(t) : null;
      if (!r.ok) {
        throw new Error((data && data.message) ? data.message : 'Error ' + r.status);
      }
      return data;
    });
  });
}

function cargarMedicos() {
  return api('/api/admin/medicos', 'GET').then(function (data) {
    medicos = data;
    var filas = '';
    var opciones = '';
    medicos.forEach(function (m, i) {
      filas += '<tr><td>' + esc(m.nombre) + '</td><td>' + esc(m.especialidad) + '</td><td>' + esc(m.username) + '</td><td>' +
        '<button onclick="editarMedico(' + i + ')">Editar</button> ' +
        '<button onclick="eliminarMedico(' + m.id + ')">Eliminar</button></td></tr>';
      opciones += '<option value="' + m.id + '">' + esc(m.nombre) + '</option>';
    });
    document.getElementById('tablaMedicos').innerHTML = filas;
    document.getElementById('cMedico').innerHTML = opciones;
  });
}

function guardarMedico() {
  var id = document.getElementById('mId').value;
  var cuerpo = {
    nombre: document.getElementById('mNombre').value,
    especialidad: document.getElementById('mEspecialidad').value,
    username: document.getElementById('mUsername').value,
    password: document.getElementById('mPassword').value
  };
  api(id ? '/api/admin/medicos/' + id : '/api/admin/medicos', id ? 'PUT' : 'POST', cuerpo)
    .then(function () { limpiarMedico(); return cargarMedicos(); })
    .then(cargarConsultas)
    .catch(function (e) { alert(e.message); });
}

function editarMedico(i) {
  var m = medicos[i];
  document.getElementById('mId').value = m.id;
  document.getElementById('mNombre').value = m.nombre;
  document.getElementById('mEspecialidad').value = m.especialidad;
  document.getElementById('mUsername').value = m.username;
  document.getElementById('mUsername').readOnly = true;
  document.getElementById('mPassword').value = '';
}

function limpiarMedico() {
  ['mId', 'mNombre', 'mEspecialidad', 'mUsername', 'mPassword'].forEach(function (id) {
    document.getElementById(id).value = '';
  });
  document.getElementById('mUsername').readOnly = false;
}

function eliminarMedico(id) {
  if (!confirm('Se eliminará el médico y sus consultas. ¿Continuar?')) return;
  api('/api/admin/medicos/' + id, 'DELETE')
    .then(cargarMedicos)
    .then(cargarConsultas)
    .catch(function (e) { alert(e.message); });
}

function cargarPacientes() {
  return api('/api/admin/pacientes', 'GET').then(function (data) {
    var opciones = '';
    data.forEach(function (p) {
      opciones += '<option value="' + p.id + '">' + esc(p.nombre) + ' (' + esc(p.username) + ')</option>';
    });
    document.getElementById('cPaciente').innerHTML = opciones;
  });
}

function cargarConsultas() {
  return api('/api/admin/consultas', 'GET').then(function (data) {
    consultas = data;
    var filas = '';
    consultas.forEach(function (c, i) {
      filas += '<tr><td>' + esc(c.nombrePaciente) + '</td><td>' + esc(c.motivo) + '</td><td>' + esc(c.numeroConsultorio) +
        '</td><td>' + hora(c.horaInicio) + ' - ' + hora(c.horaFin) + '</td><td>' + esc(c.medicoNombre) + '</td><td>' +
        '<button onclick="editarConsulta(' + i + ')">Editar</button> ' +
        '<button onclick="eliminarConsulta(' + c.id + ')">Eliminar</button></td></tr>';
    });
    document.getElementById('tablaConsultas').innerHTML = filas;
  });
}

function guardarConsulta() {
  var id = document.getElementById('cId').value;
  var cuerpo = {
    nombrePaciente: document.getElementById('cNombre').value,
    motivo: document.getElementById('cMotivo').value,
    numeroConsultorio: Number(document.getElementById('cConsultorio').value),
    horaInicio: document.getElementById('cInicio').value,
    horaFin: document.getElementById('cFin').value,
    medicoId: Number(document.getElementById('cMedico').value),
    pacienteId: Number(document.getElementById('cPaciente').value)
  };
  api(id ? '/api/admin/consultas/' + id : '/api/admin/consultas', id ? 'PUT' : 'POST', cuerpo)
    .then(function () { limpiarConsulta(); return cargarConsultas(); })
    .catch(function (e) { alert(e.message); });
}

function editarConsulta(i) {
  var c = consultas[i];
  document.getElementById('cId').value = c.id;
  document.getElementById('cNombre').value = c.nombrePaciente;
  document.getElementById('cMotivo').value = c.motivo;
  document.getElementById('cConsultorio').value = c.numeroConsultorio;
  document.getElementById('cInicio').value = hora(c.horaInicio);
  document.getElementById('cFin').value = hora(c.horaFin);
  document.getElementById('cMedico').value = c.medicoId;
  document.getElementById('cPaciente').value = c.pacienteId;
}

function limpiarConsulta() {
  ['cId', 'cNombre', 'cMotivo', 'cConsultorio', 'cInicio', 'cFin'].forEach(function (id) {
    document.getElementById(id).value = '';
  });
}

function eliminarConsulta(id) {
  if (!confirm('¿Eliminar la consulta?')) return;
  api('/api/admin/consultas/' + id, 'DELETE')
    .then(cargarConsultas)
    .catch(function (e) { alert(e.message); });
}

cargarMedicos().then(cargarPacientes).then(cargarConsultas).catch(function (e) { alert(e.message); });
</script>
</body>
</html>