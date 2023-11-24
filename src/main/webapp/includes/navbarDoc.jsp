<%@ page import="com.example.lab9.Beans.Usuario" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<% String currentPage = request.getParameter("currentPage"); %>
<jsp:useBean id="usuarioLogueado" scope="session" type="Usuario" class="com.example.lab9.Beans.Usuario" />

<nav class="navbar navbar-expand-md navbar-light bg-light">
    <div class="container-fluid">
        <a class="navbar-brand" href="<%=request.getContextPath()%>">Gestión</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse justify-content-end" id="navbarSupportedContent">
            <ul class="navbar-nav">
                <!-- opciones del docente -->
                <li class="nav-item">
                    <a class="nav-link <%=currentPage.equals("eva") ? "active" : ""%>"
                       href="<%=request.getContextPath()%>/EvaServlet">
                        Lista evaluaciones
                    </a>
                </li>

                <!-- colocar nombre usuario registrado -->
                <li class="nav-item">
                    <% if(usuarioLogueado.getUsuarioId() != 0){ %>
                    <a class="nav-link disabled"><%=usuarioLogueado.getNombre()%></a>
                    <% } %>
                </li>

                <!-- usuario cerrar sesion -->
                <% if(usuarioLogueado.getUsuarioId() != 0){ %>
                <li class="nav-item">
                    <a class="nav-link" style="text-decoration: underline;color: #0d6efd;" href="<%=request.getContextPath()%>/LoginServlet?a=lo">(Cerrar sesión)</a>
                </li>
                <% } %>
            </ul>
        </div>
    </div>
</nav>
