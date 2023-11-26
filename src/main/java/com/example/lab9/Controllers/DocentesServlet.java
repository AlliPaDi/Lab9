package com.example.lab9.Controllers;

import com.example.lab9.Beans.Curso;
import com.example.lab9.Beans.CursoHasDocente;
import com.example.lab9.Beans.Facultad;
import com.example.lab9.Beans.Usuario;
import com.example.lab9.Daos.CursoDao;
import com.example.lab9.Daos.CursoHasDocenteDao;
import com.example.lab9.Daos.UsuarioDao;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "DocentesServlet", value = "/DocentesServlet")
public class DocentesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action") == null ? "listaDoc" : request.getParameter("action");
        HttpSession httpSession = request.getSession();
        Usuario usuarioLogueado = (Usuario) httpSession.getAttribute("usuarioLogueado");

        RequestDispatcher view;
        CursoDao cursoDao = new CursoDao();
        UsuarioDao usuarioDao = new UsuarioDao();
        CursoHasDocenteDao cursoHasDocenteDao = new CursoHasDocenteDao();

        switch (action){
            case "listaDoc":
                request.setAttribute("listaDocentes", cursoHasDocenteDao.listarDocentes(usuarioLogueado));
                view = request.getRequestDispatcher("Decano/ListaDocentes.jsp");
                view.forward(request, response);
                break;

            case "agregar":
                break;

            case "editar":

                break;

            case "borrar":
                if (request.getParameter("id") != null) {
                    String docIdString = request.getParameter("id");
                    int docId = 0;
                    try {
                        docId = Integer.parseInt(docIdString);
                    } catch (NumberFormatException ex) {
                        response.sendRedirect("DocentesServlet?err=Error al borrar el docente");
                    }

                    Usuario doc = usuarioDao.obtenerDocente(docId);

                    if (doc != null) {
                        cursoDao.borrarCurso(docId);
                        response.sendRedirect("DocentesServlet?msg=Docente borrado exitosamente");
                    }
                }else {
                    response.sendRedirect("DocentesServlet?err=Error al borrar el docente");
                }

                break;
            default:
                response.sendRedirect("DocentesServlet");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action") == null ? "guardar" : request.getParameter("action");
        CursoDao cursoDao = new CursoDao();
        CursoHasDocente cursoHasDocente = new CursoHasDocente();
        switch (action){
            case "guardar":

                break;
            case "actualizar":

                break;
        }

    }

}

