package myusick.persistence.DAO;

import java.sql.*;

/**
 * Created by Cuenta de clase on 02/04/2015.
 */
public class PublicanteDAO {

    private Connection con;

    public void setConnection(Connection con){
        this.con = con;
    }
    public int insertarPublicante(boolean tipo){
        try {
            String query="insert into publicante (tipoPublicante) values (?)";
            PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setBoolean(1,tipo);
            int insertedRows = ps.executeUpdate();
            if(insertedRows == 1){
                ResultSet keys = ps.getGeneratedKeys();
                keys.next();
                int uuid = keys.getInt(1);
                return uuid;
            }else return -1;

        } catch (SQLException e) {
            return -1;
        }
        
    }
}
