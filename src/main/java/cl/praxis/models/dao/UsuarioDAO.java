package cl.praxis.models.dao;

import java.sql.SQLException;

import cl.praxis.models.dto.UsuarioDTO;

public interface UsuarioDAO {
	
	void registrarUsuario(UsuarioDTO usuario) throws SQLException;
    UsuarioDTO obtenerUsuarioPorCorreo(String correo) throws SQLException;

}
