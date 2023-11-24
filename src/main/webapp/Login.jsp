<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Inicio sesión</title>
    <link rel="icon" href="images/logo.png"/>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <link rel="stylesheet" href="css/styleLogin.css">
</head>
<body>
<div class="box">
    <form method="post" action="<%=request.getContextPath()%>/LoginServlet">
        <h2>Inicia sesión</h2>
        <% if (request.getAttribute("err") != null) {%>
        <div class="alert alert-danger mb-1" role="alert"><%=request.getAttribute("err")%>
        </div>
        <% } %>
        <div class="inputBox">
            <input type="email" name="email" required>
            <span>Correo</span>
            <i></i>
        </div>
        <div class="inputBox">
            <input type="password" name="password" required>
            <span>Contraseña</span>
            <i></i>
        </div>
        <button type="submit">Ingresar</button>
    </form>
</div>
</body>
</html>

