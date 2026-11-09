<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login</title>
</head>
<body>
    <h2>Iniciar sesión</h2>

    <c:if test="${param.error != null}">
        <p style="color:red">Usuario o clave incorrectos</p>
    </c:if>
    <c:if test="${param.logout != null}">
        <p style="color:green">Sesión cerrada</p>
    </c:if>

    <form action="${pageContext.request.contextPath}/login" method="post">
        <input type="text" name="username" placeholder="Usuario">
        <input type="password" name="password" placeholder="Clave">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
        <button type="submit">Entrar</button>
    </form>
</body>
</html>