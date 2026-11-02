<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Inicio</title>
<style>
  body { font-family: Arial, sans-serif; margin: 40px; }
  .rol { display: inline-block; background: #c8102e; color: white; padding: 3px 10px; border-radius: 12px; margin-right: 5px; }
  a { display: block; margin: 8px 0; }
  button { margin-top: 20px; padding: 8px 16px; }
</style>
</head>
<body>
  <h1>Bienvenido, ${username}</h1>
  <p>Tus roles:
    <c:forEach var="r" items="${roles}">
      <span class="rol">${r}</span>
    </c:forEach>
  </p>

  <h3>Opciones</h3>
  <a href="${pageContext.request.contextPath}/new">Ir a /new (ADMIN, CREATOR)</a>
  <a href="${pageContext.request.contextPath}/edit/1">Ir a /edit/1 (ADMIN, EDITOR)</a>
  <a href="${pageContext.request.contextPath}/delete/1">Ir a /delete/1 (ADMIN)</a>

  <form method="post" action="${pageContext.request.contextPath}/logout">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    <button type="submit">Cerrar sesi&oacute;n</button>
  </form>
</body>
</html>