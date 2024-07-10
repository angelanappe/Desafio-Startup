package cl.praxis.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cl.praxis.models.db.Db;
import cl.praxis.models.dto.UsuarioDTO;
import cl.praxis.models.dao.UsuarioDAO;
import cl.praxis.models.dao.UsuarioDAOImpl;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String correo = request.getParameter("correo");
        String password = request.getParameter("password");

        try {
            UsuarioDTO usuario = null;

            try (Connection connection = Db.getConnect()) {
                UsuarioDAO usuarioDAO = new UsuarioDAOImpl(connection);
                usuario = usuarioDAO.obtenerUsuarioPorCorreo(correo);

                if (usuario != null && usuario.getPassword().equals(password)) {
                	HttpSession session = request.getSession();
                	session.setAttribute("usuario", usuario);
                    response.sendRedirect("home.jsp");
                    
                } else {
                    request.setAttribute("mensaje", "Correo o contraseña incorrectos.");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                }
                
            } catch (SQLException e) {
            	
                throw new ServletException("Error al autenticar el usuario", e);
            }
            
        } catch (Exception e) {
        	
            throw new ServletException("Error al autenticar el usuario", e);
        }
    }
}