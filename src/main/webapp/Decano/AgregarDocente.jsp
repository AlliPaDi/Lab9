<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Agregar docente</title>
    <jsp:include page="../includes/headCss.jsp"/>
</head>
<body>
<jsp:include page="../includes/navbar.jsp">
    <jsp:param name="currentPage" value="doc"/>
</jsp:include>
</body>
<div class='container'>
    <div class="row mb-4">
        <div class="col"></div>
        <div class="col-md-6">
            <h1 class='mb-3'>Nuevo docente</h1>
            <hr>
            <form method="POST" action="<%=request.getContextPath()%>/DocentesServlet">
                <!-- nombre docente -->
                <div class="mb-3">
                    <label class="form-label">Nombre del docente</label>
                    <input type="text" class="form-control form-control-sm"  name="nombreDoc" required>
                </div>
                <!-- correo docente -->
                <div class="mb-3">
                    <label class="form-label">Correo del docente</label>
                    <input type="email" class="form-control form-control-sm"  name="correoDoc" required>
                </div>

                <!-- contraseña docente -->
                <div class="mb-3">
                    <label class="form-label">Contraseña</label>
                    <input type="password" class="form-control form-control-sm"  name="password" required>
                </div>

                <a href="<%= request.getContextPath()%>/DocentesServlet" class="btn btn-danger">Cancelar</a>
                <input type="submit" value="Guardar" class="btn btn-primary"/>
            </form>
        </div>
        <div class="col"></div>
    </div>
</div>
</html>

