<%@ page import="com.example.lab9.Beans.CursoHasDocente" %>
<%@ page import="com.example.lab9.Beans.Curso" %>
<%@ page import="com.example.lab9.Beans.Usuario" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="curso" type="Curso" scope="request"/>
<html>
<head>
    <title>Editar curso</title>
  <jsp:include page="../includes/headCss.jsp"/>
</head>
<body>
<jsp:include page="../includes/navbar.jsp">
  <jsp:param name="currentPage" value="cursos"/>
</jsp:include>
<div class='container'>
  <div class="row mb-4">
    <div class="col-md-3"></div>
    <div class="col-md-6">
      <h1 class='mb-3'>Editar curso</h1>
      <hr>
      <form method="POST" action="<%=request.getContextPath()%>/CursosServlet?action=actualizar">
        <input type="hidden" name="cursoId" value="<%= curso.getCursoId()%>"/>
        <div class="mb-3">
          <label class="form-label" >Nombre del curso</label>
          <input type="text" class="form-control form-control-sm" name="nombreCurso"
                 value="<%= curso.getNombreCurso()%>" required>
        </div>

        <a href="<%= request.getContextPath()%>/CursosServlet" class="btn btn-danger">Cancelar</a>
        <input type="submit" value="Guardar" class="btn btn-primary"/>
      </form>
    </div>
    <div class="col-md-3"></div>
  </div>
</div>
</body>
</html>
