package myusick.model.test;

import myusick.persistence.connection.ConnectionAdmin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by Sandra on 30/03/2015.
 */
public class ConnectionTest {
    public static void main (String [] args){
        try{
            Connection con = ConnectionAdmin.getConnection();
            con.setAutoCommit(false);
            String queryString = "INSERT INTO Aptitud (nombre) VALUES(?)";
            PreparedStatement ps = null;
            ps = con.prepareStatement(queryString);

            ps.setString(1, "abrir los ojos en el agua");
            ResultSet resultSet = null;
            int insertedRows = ps.executeUpdate();
            if (insertedRows == 1) {
                /*Ahora que no ha dado error, hacemos el commit*/
                con.commit();
            }
            else{
                /* Error, hacemos rollback*/
                con.rollback();
            }


        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
