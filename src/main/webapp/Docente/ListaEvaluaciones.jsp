<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="java.util.ArrayList" %>
<%@ page import="com.example.lab9.Beans.Usuario" %>
<%@ page import="com.example.lab9.Beans.Evaluaciones" %>
<jsp:useBean id="listaEvaluaciones" type="java.util.ArrayList<com.example.lab9.Beans.Evaluaciones>" scope="request"/>
<jsp:useBean id="usuarioLogueado" class="com.example.lab9.Beans.Usuario" type="Usuario" scope="session"/>
<html>
<head>
    <title>Evaluaciones</title>
    <jsp:include page="../includes/headCss.jsp"/>
</head>
<body>
<jsp:include page="../includes/navbarDoc.jsp">
    <jsp:param name="currentPage" value="eva"/>
</jsp:include>
<div class="container">

    <div class="row mb-5 mt-4">
        <div class="col-md-7">
            <h1>Lista de evaluaciones</h1>
        </div>
        <div class="col-md-5 col-lg-4 ms-auto my-auto text-md-end">
            <a href="<%= request.getContextPath()%>/EvaServlet?action=agregar" class="btn btn-primary">Agregar
                nueva evaluacion</a>
        </div>
    </div>

    <% if (request.getParameter("msg") != null) {%>
    <div class="alert alert-success" role="alert"><%=request.getParameter("msg")%>
    </div>
    <% } %>
    <% if (request.getParameter("err") != null) {%>
    <div class="alert alert-danger" role="alert"><%=request.getParameter("err")%>
    </div>
    <% } %>

    <table class="table">
        <thead>
        <tr>
            <th>#</th>
            <th>Nombre</th>
            <th>Código</th>
            <th>Correo</th>
            <th>Nota</th>
            <th>Curso</th>
            <th>Registro</th>
            <th>Edición</th>
            <% if (usuarioLogueado != null && usuarioLogueado.getUsuarioId() > 0) {%>
            <th></th>
            <th></th>
            <% } %>
        </tr>
        </thead>

        <tbody>
        <%
            int i = 1;
            for (Evaluaciones eva : listaEvaluaciones) {
        %>
        <tr>
            <td><%= i%>
            </td>
            <td><%=eva.getNombreEstudiante()%>
            </td>
            <td><%=eva.getCodigoEstudiante()%>
            </td>
            <td><%=eva.getCorreoEstudiante()%>
            </td>
            <td><%= eva.getNota()%>
            </td>
            <td><%= eva.getCurso().getNombreCurso()%>
            </td>
            <td><%=eva.getFechaRegistro()%>
            </td>
            <td><%=eva.getFechaEdicion()%>
            </td>

            <% if (usuarioLogueado != null && usuarioLogueado.getUsuarioId() > 0) {%>
            <td>
                <a href="<%=request.getContextPath()%>/EvaServlet?action=editar&id=<%= eva.getEvaluacionesId()%>"
                   type="button" class="btn btn-primary">
                    <i class="bi bi-pencil-square"></i>
                </a>
            </td>
            <td>
                <a onclick="return confirm('¿Estas seguro de borrar?');"
                   href="<%=request.getContextPath()%>/EvaServlet?action=borrar&id=<%= eva.getEvaluacionesId()%>"
                   type="button" class="btn btn-danger">
                    <i class="bi bi-trash"></i>
                </a>
            </td>
            <% } %>
        </tr>
        <%
                i++;
            }
        %>
        </tbody>
    </table>
</div>
</body>
</html>
