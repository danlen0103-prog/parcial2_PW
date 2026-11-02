<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>403 - Acceso denegado</title>
<style>
  body { font-family: Arial, sans-serif; text-align: center; padding-top: 100px; background: #f7f7f7; }
  h1 { font-size: 90px; color: #c8102e; margin: 0; }
</style>
</head>
<body>
  <h1>403</h1>
  <h2>Acceso denegado</h2>
  <p>No tienes permisos para entrar a esta p&aacute;gina.</p>
  <a href="${pageContext.request.contextPath}/">Volver al inicio</a>
</body>
</html>