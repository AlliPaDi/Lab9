<%@ page import="com.example.lab9.Beans.CursoHasDocente" %>
<%@ page import="com.example.lab9.Beans.Curso" %>
<%@ page import="com.example.lab9.Beans.Usuario" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="listaCursos" type="java.util.ArrayList<com.example.lab9.Beans.CursoHasDocente>" scope="request"/>

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

    <div class="col-md-3"></div>
  </div>
</div>
</body>
</html>
