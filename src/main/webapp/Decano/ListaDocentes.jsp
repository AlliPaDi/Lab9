<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="java.util.ArrayList" %>
<%@ page import="com.example.lab9.Beans.Usuario" %>
<%@ page import="com.example.lab9.Beans.CursoHasDocente" %>
<jsp:useBean id="listaDocentes" type="java.util.ArrayList<com.example.lab9.Beans.CursoHasDocente>" scope="request"/>
<jsp:useBean id="usuarioLogueado" class="com.example.lab9.Beans.Usuario" type="Usuario" scope="session"/>
<html>
<head>
    <title>Docentes</title>
    <jsp:include page="../includes/headCss.jsp"/>
</head>
<body>
<jsp:include page="../includes/navbar.jsp">
    <jsp:param name="currentPage" value="doc"/>
</jsp:include>
<div class="container">

    <div class="row mb-5 mt-4">
        <div class="col-md-7">
            <h1>Lista de docentes</h1>
        </div>
        <div class="col-md-5 col-lg-4 ms-auto my-auto text-md-end">
            <a href="<%= request.getContextPath()%>/DocentesServlet?action=agregar" class="btn btn-primary">Agregar
                nuevo docente</a>
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
            <th>Correo</th>
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
            for (CursoHasDocente cd : listaDocentes) {
        %>
        <tr>
            <td><%= i%>
            </td>
            <td><%= cd.getDocente().getNombre()%>
            </td>
            <td><%= cd.getDocente().getCorreo()%>
            </td>
            <td><%= cd.getCurso().getNombreCurso() == null ? "--Sin curso--" : cd.getCurso().getNombreCurso()%>
            </td>
            <td><%= cd.getDocente().getFechaRegistro()%>
            </td>
            <td><%= cd.getDocente().getFechaEdicion()%>
            </td>

            <% if (usuarioLogueado != null && usuarioLogueado.getUsuarioId() > 0) {%>
            <td>
                <a href="<%=request.getContextPath()%>/DocentesServlet?action=editar&id=<%= cd.getDocente().getUsuarioId()%>"
                   type="button" class="btn btn-primary">
                    <i class="bi bi-pencil-square"></i>
                </a>
            </td>
            <td>
                <a onclick="return confirm('¿Estas seguro de borrar?');"
                   href="<%=request.getContextPath()%>/DocentesServlet?action=borrar&id=<%= cd.getDocente().getUsuarioId()%>"
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
