package myusick.model.test;

import myusick.model.connection.ConnectionAdmin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Sandra on 30/03/2015.
 */
public class ConnectionTest {
    public static void main (String [] args){
        try{

            Connection c = ConnectionAdmin.getConnection();
            String query="select publicante_uuid from persona where email=? and password=?";
            PreparedStatement ps = c.prepareStatement(query);
            ps.setString(1,"foo@bar.com");
            ps.setString(2,"1234");
            ResultSet rs = ps.executeQuery();
            int i=1;
            while(rs.next()){
                System.out.println(rs.getInt(1));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }

    }
}
