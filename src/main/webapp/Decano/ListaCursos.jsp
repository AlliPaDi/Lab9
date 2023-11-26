<%@page import="java.util.ArrayList" %>
<%@ page import="com.example.lab9.Beans.Facultad" %>
<%@ page import="com.example.lab9.Beans.Curso" %>
<%@ page import="com.example.lab9.Beans.CursoHasDocente" %>
<%@ page import="com.example.lab9.Beans.Usuario" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="listaCursos" type="java.util.ArrayList<com.example.lab9.Beans.CursoHasDocente>" scope="request"/>
<jsp:useBean id="usuarioLogueado" class="com.example.lab9.Beans.Usuario" type="Usuario" scope="session"/>
<html>
<head>
    <title>Cursos</title>
    <jsp:include page="../includes/headCss.jsp"/>
</head>
<body>
<jsp:include page="../includes/navbar.jsp">
    <jsp:param name="currentPage" value="cursos"/>
</jsp:include>
<div class="container">

    <div class="row mb-5 mt-4">
        <div class="col-md-7">
            <h1>Lista de cursos</h1>
        </div>
        <div class="col-md-5 col-lg-4 ms-auto my-auto text-md-end">
            <a href="<%= request.getContextPath()%>/CursosServlet?action=agregar" class="btn btn-primary">Agregar
                nuevo curso</a>
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
            <th>Código</th>
            <th>Curso</th>
            <th>Docente</th>
            <th>Facultad</th>
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
            for (CursoHasDocente cd : listaCursos) {
        %>
        <tr>
            <td><%= i%>
            </td>
            <td><%= cd.getCurso().getCodigo()%>
            </td>
            <td><%= cd.getCurso().getNombreCurso()%>
            </td>
            <td><%= cd.getDocente().getNombre()%>
            </td>
            <td><%= cd.getCurso().getFacultad().getNombreFacu()%>
            </td>
            <td><%= cd.getCurso().getFechaRegistro()%>
            </td>
            <td><%= cd.getCurso().getFechaEdicion()%>
            </td>

            <% if (usuarioLogueado != null && usuarioLogueado.getUsuarioId() > 0) {%>
            <td>
                <a href="<%=request.getContextPath()%>/CursosServlet?action=editar&id=<%= cd.getCurso().getCursoId()%>"
                   type="button" class="btn btn-primary">
                    <i class="bi bi-pencil-square"></i>
                </a>
            </td>
            <td>
                <a onclick="return confirm('¿Estas seguro de borrar?');"
                   href="<%=request.getContextPath()%>/CursosServlet?action=borrar&id=<%= cd.getCurso().getCursoId()%>"
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
