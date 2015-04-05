package myusick.persistence.DAO;

import myusick.persistence.VO.Aptitud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Cuenta de clase on 02/04/2015.
 */
public class AptitudDAO {

    private Connection con;

    public void setConnection(Connection con){
        this.con = con;
    }

    public ArrayList<String> getAptitudesByPersona(int uuid){
        ArrayList<String> result = new ArrayList<>();
        try {
            String queryString = "select nombre from aptitud where idAptitud in (select idAptitud " +
                    "from tiene_aptitud where uuid_p=?)";
            PreparedStatement preparedStatement = con.prepareStatement(queryString);
            preparedStatement.setInt(1, uuid);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                result.add(resultSet.getString(1));
            }
            return result;
        }catch(Exception e){
            e.printStackTrace(System.err);
            return null;
        }
    }
}
