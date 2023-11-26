package com.example.lab9.Controllers;

import com.example.lab9.Beans.Curso;
import com.example.lab9.Beans.CursoHasDocente;
import com.example.lab9.Beans.Facultad;
import com.example.lab9.Beans.Usuario;
import com.example.lab9.Daos.CursoDao;
import com.example.lab9.Daos.UsuarioDao;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "CursosServlet", value = "/CursosServlet")
public class CursosServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action") == null ? "lista" : request.getParameter("action");
        HttpSession httpSession = request.getSession();
        Usuario usuarioLogueado = (Usuario) httpSession.getAttribute("usuarioLogueado");

        RequestDispatcher view;
        CursoDao cursoDao = new CursoDao();
        UsuarioDao usuarioDao = new UsuarioDao();

        switch (action){
            case "lista":
                request.setAttribute("listaCursos", cursoDao.listarCursos(usuarioLogueado));
                view = request.getRequestDispatcher("Decano/ListaCursos.jsp");
                view.forward(request, response);
                break;

            case "agregar":
                request.setAttribute("listaDocentes", usuarioDao.listarDocentes());
                view = request.getRequestDispatcher("Decano/AgregarCursos.jsp");
                view.forward(request, response);
                break;

            case "editar":
                if (request.getParameter("id") != null) {
                    String cursoIdString = request.getParameter("id");
                    int cursoId = 0;
                    try {
                        cursoId = Integer.parseInt(cursoIdString);
                    } catch (NumberFormatException ex) {
                        response.sendRedirect("CursosServlet");
                    }

                    Curso cur = cursoDao.obtenerCurso(cursoId);

                    if (cur != null) {
                        request.setAttribute("curso", cur);

                        view = request.getRequestDispatcher("Decano/EditarCursos.jsp");
                        view.forward(request, response);
                    } else {
                        response.sendRedirect("CursosServlet");
                    }

                } else {
                    response.sendRedirect("CursosServlet");
                }
                break;

            case "borrar":
                if (request.getParameter("id") != null) {
                    String cursoIdString = request.getParameter("id");
                    int cursoId = 0;
                    try {
                        cursoId = Integer.parseInt(cursoIdString);
                    } catch (NumberFormatException ex) {
                        response.sendRedirect("CursosServlet?err=Error al borrar el curso");
                    }

                    Curso cur = cursoDao.obtenerCurso(cursoId);

                    if (cur != null) {
                        cursoDao.borrarCurso(cursoId);
                        response.sendRedirect("CursosServlet?msg=Curso borrado exitosamente");
                    }
                }else {
                    response.sendRedirect("CursosServlet?err=Error al borrar el curso");
                }

                break;
            default:
                response.sendRedirect("CursoServlet");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession();
        Usuario usuarioLogueado = (Usuario) httpSession.getAttribute("usuarioLogueado");

        String action = request.getParameter("action") == null ? "guardar" : request.getParameter("action");
        CursoDao cursoDao = new CursoDao();
        CursoHasDocente cursoHasDocente = new CursoHasDocente();
        switch (action){
            case "guardar":
                int idFacu = cursoDao.obtenerFacuUsuarioLogueado(usuarioLogueado);
                int idCurso = cursoDao.obtenerProxIdCurso();

                String codigo = request.getParameter("codigo");
                String nombreCurso = request.getParameter("nombreCurso");
                String docenteId = request.getParameter("docenteId");

                Curso curso = new Curso();
                curso.setCursoId(idCurso);
                curso.setCodigo(codigo);
                curso.setNombreCurso(nombreCurso);

                Facultad facultad = new Facultad();
                facultad.setFacultadId(idFacu);
                curso.setFacultad(facultad);

                cursoHasDocente.setCurso(curso);

                Usuario docente = new Usuario();
                docente.setUsuarioId(Integer.parseInt(docenteId));
                cursoHasDocente.setDocente(docente);

                cursoDao.guardarCurso(cursoHasDocente);
                response.sendRedirect("CursosServlet");
                break;
            case "actualizar":
                String cursoIdStr = request.getParameter("cursoId");
                String cursoNombreStr = request.getParameter("nombreCurso");

                Curso curso1 = new Curso();
                curso1.setCursoId(Integer.parseInt(cursoIdStr));
                curso1.setNombreCurso(cursoNombreStr);

                cursoDao.actualizarCurso(curso1);
                response.sendRedirect("CursosServlet");
                break;
        }

    }
}

