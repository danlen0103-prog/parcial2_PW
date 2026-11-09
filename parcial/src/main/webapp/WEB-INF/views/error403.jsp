<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<title>Acceso restringido</title>
</head>
<body>
<h1>Acceso restringido</h1>
<p>No cuenta con los permisos necesarios para realizar esta acción.</p>
<a href="javascript:history.back()">Volver</a>
<form action="/logout" method="post">
<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
<button type="submit">Cerrar sesión</button>
</form>
</body>
</html>