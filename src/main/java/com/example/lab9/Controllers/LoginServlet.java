package com.example.lab9.Controllers;

import com.example.lab9.Beans.Rol;
import com.example.lab9.Beans.Usuario;
import com.example.lab9.Daos.UsuarioDao;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "LoginServlet", value = {"/LoginServlet",""})
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("Login.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        UsuarioDao usuarioDao = new UsuarioDao();

        if(usuarioDao.validarUsuarioPasswordHashed(email,password)){
            Usuario usuario = usuarioDao.obtenerUsuario(email);
            HttpSession httpSession = request.getSession();
            httpSession.setAttribute("usuarioLogueado",usuario);

            String nombreRol = usuario.getRol().getNombreRol();

            switch (nombreRol) {
                case "Decano":
                    response.sendRedirect(request.getContextPath() + "/DecanoServlet");
                    break;
                case "Docente":
                    response.sendRedirect(request.getContextPath() + "/DocenteServlet");
                    break;
            }

        }else{
            request.setAttribute("err","Email o password incorrectos");
            request.getRequestDispatcher("Login.jsp").forward(request,response);
        }
    }
}

