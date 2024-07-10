package cl.praxis.controllers;

import java.io.IOException;
import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cl.praxis.models.dao.UsuarioDAOImpl;
import cl.praxis.models.db.Db;
import cl.praxis.models.dto.UsuarioDTO;
import cl.praxis.models.dao.UsuarioDAO;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("registro.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String correo = request.getParameter("correo");
        String nick = request.getParameter("nick");
        String nombre = request.getParameter("nombre");
        String password = request.getParameter("password");
        int peso = Integer.parseInt(request.getParameter("peso"));

        try {
            UsuarioDTO usuario = new UsuarioDTO();
            usuario.setCorreo(correo);
            usuario.setNick(nick);
            usuario.setNombre(nombre);
            usuario.setPassword(password);
            usuario.setPeso(peso);

            try (Connection connection = Db.getConnect()) {
                UsuarioDAO usuarioDAO = new UsuarioDAOImpl(connection);
                UsuarioDTO existente = usuarioDAO.obtenerUsuarioPorCorreo(correo);

                if (existente != null) {
                    request.setAttribute("mensaje", "El correo ya está registrado.");
                    request.getRequestDispatcher("registro.jsp").forward(request, response);
                } else {
                    usuarioDAO.registrarUsuario(usuario);
                    response.sendRedirect("home.jsp");
                }
            } catch (SQLException e) {
                throw new ServletException("Error al registrar el usuario", e);
            }
        } catch (Exception e) {
            throw new ServletException("Error al registrar el usuario", e);
        }
    }
}
