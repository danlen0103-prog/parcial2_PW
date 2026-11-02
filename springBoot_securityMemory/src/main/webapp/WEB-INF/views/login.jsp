<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
<style>
  body { font-family: Arial, sans-serif; background: #eef1f5; display: flex; justify-content: center; padding-top: 80px; }
  .card { background: white; padding: 30px; border-radius: 10px; width: 300px; box-shadow: 0 2px 8px rgba(0,0,0,.15); }
  input { width: 100%; padding: 8px; margin: 8px 0 14px 0; box-sizing: border-box; }
  button { width: 100%; padding: 10px; background: #c8102e; color: white; border: none; border-radius: 5px; cursor: pointer; }
  .error { color: #c8102e; margin-bottom: 10px; }
  .ok { color: green; margin-bottom: 10px; }
</style>
</head>
<body>
<div class="card">
  <h2>Iniciar sesi&oacute;n</h2>

  <c:if test="${param.error != null}">
    <div class="error">Usuario o contrase&ntilde;a incorrectos.</div>
  </c:if>
  <c:if test="${param.logout != null}">
    <div class="ok">Sesi&oacute;n cerrada correctamente.</div>
  </c:if>

  <form method="post" action="${pageContext.request.contextPath}/login">
    <label>Usuario</label>
    <input type="text" name="username" required>
    <label>Contrase&ntilde;a</label>
    <input type="password" name="password" required>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    <button type="submit">Entrar</button>
  </form>
</div>
</body>
</html>