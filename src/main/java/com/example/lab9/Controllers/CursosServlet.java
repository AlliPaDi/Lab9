package com.example.lab9.Controllers;

import com.example.lab9.Beans.Curso;
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

        RequestDispatcher view;
        CursoDao cursoDao = new CursoDao();
        UsuarioDao usuarioDao = new UsuarioDao();

        switch (action){
            case "lista":
                request.setAttribute("listaCursos", cursoDao.listarCursos());
                view = request.getRequestDispatcher("Decano/ListaCursos.jsp");
                view.forward(request, response);
                break;

            case "agregar":
                request.setAttribute("listaDocentes", usuarioDao.listarDocentes());
                view = request.getRequestDispatcher("Decano/AgregarCursos.jsp");
                view.forward(request, response);
                break;

            case "editar":

                break;

            case "borrar":
                /*if (request.getParameter("id") != null) {
                    String cursoIdString = request.getParameter("id");
                    int cursoId = 0;
                    try {
                        cursoId = Integer.parseInt(cursoIdString);
                    } catch (NumberFormatException ex) {
                        response.sendRedirect("CursosServlet?err=Error al borrar el empleado");
                    }

                    Curso curso = cursoDao.obtenerCurso(cursoId);

                    if (curso != null) {
                        try {
                            cursoDao.borrarCurso(cursoId);
                            response.sendRedirect("CursoServlet?msg=Curso borrado exitosamente");
                        } catch (SQLException e) {
                            response.sendRedirect("CursoServlet?err=Error al borrar el empleado");
                        }
                    }
                } else {
                    response.sendRedirect("CursoServlet?err=Error al borrar el empleado");
                }*/
                break;
            default:
                response.sendRedirect("CursoServlet");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action") == null ? "guardar" : request.getParameter("action");
        CursoDao cursoDao = new CursoDao();

        switch (action){
            case "guardar":

                //tomamos los valores pedidos **revisar el id
                String codigo = request.getParameter("codigo");
                String nombreCurso = request.getParameter("nombreCurso");
                String nombreDocente = request.getParameter("nombreDocente");

                /*Curso curso = new Curso();
                curso.setCodigo(codigo);
                curso.setNombreCurso(nombreCurso);

                Usuario docente = new Usuario();
                docente.setNombre();*/

                break;
            case "actualizar":
                break;
        }

    }
}

