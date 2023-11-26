<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="docente" type="com.example.lab9.Beans.Usuario" scope="request"/>
<html>
<head>
    <title>Editar docente</title>
    <jsp:include page="../includes/headCss.jsp"/>
</head>
<body>
<jsp:include page="../includes/navbar.jsp">
    <jsp:param name="currentPage" value="doc"/>
</jsp:include>
<div class='container'>
    <div class="row mb-4">
        <div class="col-md-3"></div>
        <div class="col-md-6">
            <h1 class='mb-3'>Editar docente</h1>
            <hr>
            <form method="POST" action="<%=request.getContextPath()%>/DocentesServlet?action=actualizar">
                <input type="hidden" name="docenteId" value="<%= docente.getUsuarioId()%>"/>
                <div class="mb-3">
                    <label class="form-label" >Nombre del docente</label>
                    <input type="text" class="form-control form-control-sm" name="nombreDocente"
                           value="<%= docente.getNombre()%>" required>
                </div>

                <a href="<%= request.getContextPath()%>/DocentesServlet" class="btn btn-danger">Cancelar</a>
                <input type="submit" value="Guardar" class="btn btn-primary"/>
            </form>
        </div>
        <div class="col-md-3"></div>
    </div>
</div>
</body>
</html>
