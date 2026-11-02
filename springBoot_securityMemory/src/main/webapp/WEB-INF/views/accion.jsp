<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${titulo}</title>
<style>body { font-family: Arial, sans-serif; margin: 40px; }</style>
</head>
<body>
  <h1>${titulo}</h1>
  <p>${mensaje}</p>
  <a href="${pageContext.request.contextPath}/">Volver al inicio</a>
</body>
</html>