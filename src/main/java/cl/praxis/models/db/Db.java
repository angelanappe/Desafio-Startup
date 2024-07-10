package cl.praxis.models.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Db {

	private static Connection connect = null;

    private Db() {
        
    	try {
            Class.forName("org.postgresql.Driver");
            connect = DriverManager.getConnection("jdbc:postgresql://localhost:5432/startUp_db", "", "");
            System.out.println(connect.isValid(5000) ? "Conectado a la base de datos." : "Conexión fallida.");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error al conectar con base de datos: " + e.getMessage());
        }
    }

    public static Connection getConnect() throws SQLException  {
        if (connect == null || connect.isClosed()) {
            new Db();
        }
        return connect;
    }
}

