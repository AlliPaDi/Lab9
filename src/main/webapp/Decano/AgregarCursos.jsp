<%@ page import="com.example.lab9.Beans.Usuario" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="listaDocentes" type="java.util.ArrayList<com.example.lab9.Beans.Usuario>" scope="request"/>
<html>
<head>
    <title>Agregar curso</title>
    <jsp:include page="../includes/headCss.jsp"/>
</head>
<body>
<jsp:include page="../includes/navbar.jsp">
    <jsp:param name="currentPage" value="cursos"/>
</jsp:include>
</body>
<div class='container'>
    <div class="row mb-4">
        <div class="col"></div>
        <div class="col-md-6">
            <h1 class='mb-3'>Nuevo curso</h1>
            <hr>
            <form method="POST" action="<%=request.getContextPath()%>/CursosServlet">
                <!-- codigo curso -->
                <div class="mb-3">
                    <label class="form-label" for="codigo">Código del curso</label>
                    <input type="text" class="form-control form-control-sm" id="codigo" name="codigo" required>
                </div>
                <!-- nombre curso -->
                <div class="mb-3">
                    <label class="form-label" for="nombreCurso">Nombre del curso</label>
                    <input type="text" class="form-control form-control-sm" id="nombreCurso" name="nombreCurso" required>
                </div>

                <!-- seleccionar docente registrado -->
                <div class="mb-3">
                    <label class="form-label" for="docente">Docente del curso</label>
                    <select name="docenteId" id="docente" class="form-select" required>
                        <% for (Usuario docente : listaDocentes) {%>
                        <option value="<%=docente.getUsuarioId()%>"><%=docente.getNombre()%>
                        </option>
                        <% }%>
                    </select>
                </div>

                <a href="<%= request.getContextPath()%>/CursosServlet" class="btn btn-danger">Cancelar</a>
                <input type="submit" value="Guardar" class="btn btn-primary"/>
            </form>
        </div>
        <div class="col"></div>
    </div>
</div>
</html>
