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
import java.sql.SQLException;

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
                view = request.getRequestDispatcher("Decano/AgregarDocente.jsp");
                view.forward(request, response);
                break;

            case "editar":
                if (request.getParameter("id") != null) {
                    String docIdString = request.getParameter("id");
                    int docId = 0;
                    try {
                        docId = Integer.parseInt(docIdString);
                    } catch (NumberFormatException ex) {
                        response.sendRedirect("DocentesServlet");
                    }

                    Usuario doc = usuarioDao.obtenerDocente(docId);

                    if (doc != null) {
                        request.setAttribute("docente", doc);

                        view = request.getRequestDispatcher("Decano/EditarDocente.jsp");
                        view.forward(request, response);
                    } else {
                        response.sendRedirect("DocentesServlet");
                    }

                } else {
                    response.sendRedirect("DocentesServlet");
                }
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
                        try {
                            usuarioDao.borrarDocente(docId);
                            response.sendRedirect("DocentesServlet");
                        }catch (SQLException e){
                            response.sendRedirect("DocentesServlet?err=Error al borrar el docente");
                        }

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
        UsuarioDao usuarioDao = new UsuarioDao();
        switch (action){
            case "guardar":
                int docenteId = usuarioDao.obtenerProxIdDocente();

                String nombreDoc = request.getParameter("nombreDoc");
                String correoDoc = request.getParameter("correoDoc");
                String password = request.getParameter("password");

                Usuario docente = new Usuario();
                docente.setUsuarioId(docenteId);
                docente.setNombre(nombreDoc);
                docente.setCorreo(correoDoc);
                docente.setPassword(password);

                usuarioDao.guardarDocente(docente);
                response.sendRedirect("DocentesServlet");

                break;
            case "actualizar":
                String docenteIdStr = request.getParameter("docenteId");
                String docenteNombreStr = request.getParameter("nombreDocente");

                Usuario docente1 = new Usuario();
                docente1.setUsuarioId(Integer.parseInt(docenteIdStr));
                docente1.setNombre(docenteNombreStr);

                usuarioDao.actualizarDocente(docente1);
                response.sendRedirect("DocentesServlet");
                break;
        }

    }

}

