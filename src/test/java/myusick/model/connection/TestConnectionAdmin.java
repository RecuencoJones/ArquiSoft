package myusick.model.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Sandra on 30/03/2015.
 */
public class TestConnectionAdmin {
    /*
	 * Atributos de la clase
	 */
    private final static String DRIVER_CLASS_NAME = "com.mysql.jdbc.Driver";
    private final static String DRIVER_URL =
            "jdbc:mysql://localhost:3306/myusickdb";
    private final static String USER = "root";
    private final static String PASSWORD = "toor";


    static {

        try {
            Class.forName(DRIVER_CLASS_NAME);
        } catch (ClassNotFoundException e) {
            e.printStackTrace(System.err);
        }

    }

    private TestConnectionAdmin() {}
    /**
     * Conecta con la base de datos de datos meteorologicos
     * @return a Connection with the database
     * @throws java.sql.SQLException an exception
     */
    public final static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DRIVER_URL, USER, PASSWORD);
    }
}
