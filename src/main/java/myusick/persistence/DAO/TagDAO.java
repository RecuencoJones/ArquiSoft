package myusick.persistence.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by Cuenta de clase on 02/04/2015.
 */
public class TagDAO {

    private Connection con;

    public void setConnection(Connection con){
        this.con = con;
    }

    public String[] getTagsByPersona(int uuid){
        try{
            String queryString = "select nombreTag from tag where idTag in (select idTag " +
                    "from persona_tiene_tag where UUID_P=?)";
            PreparedStatement preparedStatement = con.prepareStatement(queryString);
            preparedStatement.setInt(1, uuid);
            ResultSet resultSet = preparedStatement.executeQuery();
            int i = 0; String[] result = new String[20];
            while(resultSet.next()){
                result[i] = resultSet.getString(i); i++;
            }
            return result;
        }catch (Exception e) {
            e.printStackTrace(System.err);
            return null;
        }
    }
}
