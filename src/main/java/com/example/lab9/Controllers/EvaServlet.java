package com.example.lab9.Controllers;

import com.example.lab9.Beans.*;
import com.example.lab9.Daos.CursoDao;
import com.example.lab9.Daos.EvaluacionDao;
import com.example.lab9.Daos.UsuarioDao;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "EvaServlet", value = "/EvaServlet")
public class EvaServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action") == null ? "lista" : request.getParameter("action");
        HttpSession httpSession = request.getSession();
        Usuario usuarioLogueado = (Usuario) httpSession.getAttribute("usuarioLogueado");

        RequestDispatcher view;
        EvaluacionDao evaluacionDao = new EvaluacionDao();

        switch (action){
            case "lista":
                request.setAttribute("listaCursos", evaluacionDao.listarEvaluaciones(usuarioLogueado));
                view = request.getRequestDispatcher("Docente/ListaEvaluaciones.jsp");
                view.forward(request, response);
                break;

            case "agregar":
                view = request.getRequestDispatcher("Docente/AgregarEvaluacion.jsp");
                view.forward(request, response);
                break;

            case "editar":
                if (request.getParameter("id") != null) {
                    String evaIdString = request.getParameter("id");
                    int evaId = 0;
                    try {
                        evaId = Integer.parseInt(evaIdString);
                    } catch (NumberFormatException ex) {
                        response.sendRedirect("EvaServlet");
                    }

                    Evaluaciones eva = evaluacionDao.obtenerEva(evaId);

                    if (eva != null) {
                        request.setAttribute("evaluacion", eva);

                        view = request.getRequestDispatcher("Docente/EditarEvaluacion.jsp");
                        view.forward(request, response);
                    } else {
                        response.sendRedirect("EvaServlet");
                    }

                } else {
                    response.sendRedirect("EvaServlet");
                }
                break;

            case "borrar":
                if (request.getParameter("id") != null) {
                    String evaIdString = request.getParameter("id");
                    int evaId = 0;
                    try {
                        evaId = Integer.parseInt(evaIdString);
                    } catch (NumberFormatException ex) {
                        response.sendRedirect("EvaServlet?err=Error al borrar la evaluacion");
                    }

                    Evaluaciones eva = evaluacionDao.obtenerEva(evaId);

                    if (eva != null) {
                        try{
                            evaluacionDao.borrarEva(evaId);
                            response.sendRedirect("EvaServlet?msg=Evaluacion borrada exitosamente");
                        } catch (SQLException e) {
                            response.sendRedirect("EvaServlet?err=Error al borrar la evaluaion");
                        }

                    }
                }else {
                    response.sendRedirect("EvaServlet?err=Error al borrar la evaluaion");
                }

                break;
            default:
                response.sendRedirect("EvaServlet");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession();
        Usuario usuarioLogueado = (Usuario) httpSession.getAttribute("usuarioLogueado");
        String action = request.getParameter("action") == null ? "guardar" : request.getParameter("action");

        EvaluacionDao evaluacionDao = new EvaluacionDao();
        Evaluaciones evaluaciones = new Evaluaciones();
        switch (action){
            case "guardar":
                int idCurso = evaluacionDao.obtenerCursoUsuarioLogueado(usuarioLogueado);
                int idEva = evaluacionDao.obtenerProxIdEva();

                Curso curso = new Curso();
                curso.setCursoId(idCurso);
                evaluaciones.setCurso(curso);

                String nombreAlum = request.getParameter("nombreAlum");
                String codigoAlum = request.getParameter("codigoAlum");
                String correoAlum  = request.getParameter("correoAlum" );
                String nota  = request.getParameter("nota" );

                evaluaciones.setNombreEstudiante(nombreAlum);
                evaluaciones.setCodigoEstudiante(codigoAlum);
                evaluaciones.setCorreoEstudiante(correoAlum);
                evaluaciones.setNota(Integer.parseInt(nota));

                evaluacionDao.guardarEva(evaluaciones);
                response.sendRedirect("EvaServlet");
                break;

            case "actualizar":
                String evaIdStr = request.getParameter("evaId");
                String nombreStr = request.getParameter("nombreAlum");
                String codigoAlumStr = request.getParameter("codigoAlum");
                String correoAlumStr  = request.getParameter("correoAlum" );
                String notaStr  = request.getParameter("nota" );

                Evaluaciones evaluaciones1 = new Evaluaciones();
                evaluaciones1.setEvaluacionesId(Integer.parseInt(evaIdStr));
                evaluaciones1.setNombreEstudiante(nombreStr);
                evaluaciones1.setCodigoEstudiante(codigoAlumStr);
                evaluaciones1.setCorreoEstudiante(correoAlumStr);
                evaluaciones1.setNota(Integer.parseInt(notaStr));

                evaluacionDao.actualizarEva(evaluaciones1);
                response.sendRedirect("CursosServlet");
                break;
        }
    }
}

