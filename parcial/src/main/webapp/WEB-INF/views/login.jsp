<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<title>Iniciar sesión</title>
</head>
<body>
<h2>Gestión de consultas médicas</h2>
<% if (request.getParameter("error") != null) { %>
<p style="color:red">Usuario o contraseña incorrectos</p>
<% } %>
<% if (request.getParameter("logout") != null) { %>
<p style="color:green">Sesión cerrada correctamente</p>
<% } %>
<form action="/login" method="post">
<input type="text" name="username" placeholder="Usuario" required><br>
<input type="password" name="password" placeholder="Contraseña" required><br>
<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
<button type="submit">Ingresar</button>
</form>
</body>
</html>