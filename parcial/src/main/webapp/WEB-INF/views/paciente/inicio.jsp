<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<title>Paciente</title>
<style>
table{border-collapse:collapse;margin:10px 0}
td,th{border:1px solid #999;padding:4px 8px}
</style>
</head>
<body>
<h2>Mis citas</h2>
<form action="/logout" method="post">
<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
<button type="submit">Cerrar sesión</button>
</form>
<table>
<thead><tr><th>Paciente</th><th>Motivo</th><th>Consultorio</th><th>Médico</th><th>Inicio</th><th>Fin</th><th>Acción</th></tr></thead>
<tbody id="tabla"></tbody>
</table>
<script>
function esc(t) {
  return String(t == null ? '' : t).replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;').replace(/"/g, '&quot;');
}

function hora(t) {
  return String(t).substring(0, 5);
}

function cargar() {
  fetch('/api/paciente/consultas').then(function (r) {
    if (!r.ok) throw new Error('Error ' + r.status);
    return r.json();
  }).then(function (data) {
    var filas = '';
    data.forEach(function (c) {
      filas += '<tr><td>' + esc(c.nombrePaciente) + '</td><td>' + esc(c.motivo) + '</td><td>' + esc(c.numeroConsultorio) +
        '</td><td>' + esc(c.medicoNombre) + '</td>' +
        '<td><input type="time" id="ini' + c.id + '" value="' + hora(c.horaInicio) + '"></td>' +
        '<td><input type="time" id="fin' + c.id + '" value="' + hora(c.horaFin) + '"></td>' +
        '<td><button onclick="actualizar(' + c.id + ')">Actualizar horario</button></td></tr>';
    });
    document.getElementById('tabla').innerHTML = filas;
  }).catch(function (e) { alert(e.message); });
}

function actualizar(id) {
  fetch('/api/paciente/consultas/' + id + '/horario', {
    method: 'PUT',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({
      horaInicio: document.getElementById('ini' + id).value,
      horaFin: document.getElementById('fin' + id).value
    })
  }).then(function (r) {
    return r.text().then(function (t) {
      if (!r.ok) {
        var d = t ? JSON.parse(t) : null;
        throw new Error(d && d.message ? d.message : 'Error ' + r.status);
      }
      alert('Horario actualizado');
      cargar();
    });
  }).catch(function (e) { alert(e.message); });
}

cargar();
</script>
</body>
</html>