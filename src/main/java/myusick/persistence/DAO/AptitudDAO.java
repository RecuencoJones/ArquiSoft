package myusick.persistence.DAO;

import myusick.persistence.VO.Aptitud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Cuenta de clase on 02/04/2015.
 */
public class AptitudDAO {

    private Connection con;

    public void setConnection(Connection con){
        this.con = con;
    }

    public String[] getAptitudesByPersona(int uuid){
        String[] aptitudes = new String[10];
        try {
            String queryString = "select nombre from aptitud where idAptitud in (select idAptitud " +
                    "from tiene_aptitud where uuid_p=?)";
            PreparedStatement preparedStatement = con.prepareStatement(queryString);
            preparedStatement.setInt(1, uuid);
            ResultSet resultSet = preparedStatement.executeQuery();
            int i = 0;
            while(resultSet.next()){
                aptitudes[i] = resultSet.getString(i);
            }
        }catch(Exception e){}

        return aptitudes;
    }
}
