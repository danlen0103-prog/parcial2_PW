<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head><title>Inicio</title></head>
<body>
  <h2>Bienvenida, <%= request.getUserPrincipal().getName() %></h2>
  <ul>
    <li><a href="/admin/inicio">Zona admin</a></li>
    <li><a href="/docente/inicio">Zona docente</a></li>
    <li><a href="/new">Página new</a></li>
  </ul>
  <form action="/logout" method="post">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    <button type="submit">Cerrar sesión</button>
  </form>
</body>
</html>