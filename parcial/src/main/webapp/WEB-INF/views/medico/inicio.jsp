<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<title>Médico</title>
<style>
table{border-collapse:collapse;margin:10px 0}
td,th{border:1px solid #999;padding:4px 8px}
</style>
</head>
<body>
<h2>Mis consultas asignadas</h2>
<form action="/logout" method="post">
<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
<button type="submit">Cerrar sesión</button>
</form>
<table>
<thead><tr><th>Paciente</th><th>Motivo</th><th>Consultorio</th><th>Horario</th></tr></thead>
<tbody id="tabla"></tbody>
</table>
<script>
function esc(t) {
  return String(t == null ? '' : t).replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;').replace(/"/g, '&quot;');
}

function hora(t) {
  return String(t).substring(0, 5);
}

fetch('/api/medico/consultas').then(function (r) {
  if (!r.ok) throw new Error('Error ' + r.status);
  return r.json();
}).then(function (data) {
  var filas = '';
  data.forEach(function (c) {
    filas += '<tr><td>' + esc(c.nombrePaciente) + '</td><td>' + esc(c.motivo) + '</td><td>' + esc(c.numeroConsultorio) +
      '</td><td>' + hora(c.horaInicio) + ' - ' + hora(c.horaFin) + '</td></tr>';
  });
  document.getElementById('tabla').innerHTML = filas;
}).catch(function (e) { alert(e.message); });
</script>
</body>
</html>