package cl.praxis.models.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import cl.praxis.models.db.Db;
import cl.praxis.models.dto.UsuarioDTO;

public class UsuarioDAOImpl implements UsuarioDAO {
	
	private Connection connection;

    public UsuarioDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void registrarUsuario(UsuarioDTO usuario) throws SQLException {
        String sql = "INSERT INTO usuarios (correo, nick, nombre, password, peso, created_at, updated_at) VALUES (?, ?, ?, ?, ?, NOW(), NOW())";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, usuario.getCorreo());
            statement.setString(2, usuario.getNick());
            statement.setString(3, usuario.getNombre());
            statement.setString(4, usuario.getPassword());
            statement.setInt(5, usuario.getPeso());
            statement.executeUpdate();
        }
    }

    @Override
    public UsuarioDTO obtenerUsuarioPorCorreo(String correo) throws SQLException {
        String sql = "SELECT id, correo, nick, nombre, password, peso, created_at, updated_at FROM usuarios WHERE correo = ?";
        UsuarioDTO usuario = null;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, correo);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                usuario = new UsuarioDTO();
                usuario.setId(resultSet.getInt("id"));
                usuario.setCorreo(resultSet.getString("correo"));
                usuario.setNick(resultSet.getString("nick"));
                usuario.setNombre(resultSet.getString("nombre"));
                usuario.setPassword(resultSet.getString("password"));
                usuario.setPeso(resultSet.getInt("peso"));
                usuario.setCreatedAt(resultSet.getTimestamp("created_at"));
                usuario.setUpdatedAt(resultSet.getTimestamp("updated_at"));
            }
        }
        return usuario;
    }

}
