<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Agregar evaluaciones</title>
    <jsp:include page="../includes/headCss.jsp"/>
</head>
<body>
<jsp:include page="../includes/navbarDoc.jsp">
    <jsp:param name="currentPage" value="eva"/>
</jsp:include>

<div class='container'>
    <div class="row mb-4">
        <div class="col"></div>
        <div class="col-md-6">
            <h1 class='mb-3'>Nueva evaluación</h1>
            <hr>
            <form method="POST" action="<%=request.getContextPath()%>/EvaServlet">

                <div class="mb-3">
                    <label class="form-label" >Nombre del alumno</label>
                    <input type="text" class="form-control form-control-sm"  name="nombreAlum" required>
                </div>

                <div class="mb-3">
                    <label class="form-label" >Código del alumno</label>
                    <input type="text" class="form-control form-control-sm"  name="codigoAlum" required>
                </div>
                <div class="mb-3">
                    <label class="form-label" >Correo del alumno</label>
                    <input type="email" class="form-control form-control-sm"  name="correoAlum" required>
                </div>

                <div class="mb-3">
                    <label class="form-label" >Nota del alumno</label>
                    <input type="text" class="form-control form-control-sm"  name="nota" required>
                </div>


                <a href="<%= request.getContextPath()%>/EvaServlet" class="btn btn-danger">Cancelar</a>
                <input type="submit" value="Guardar" class="btn btn-primary"/>
            </form>
        </div>
        <div class="col"></div>
    </div>
</div>
</body>
</html>
