package myusick.persistence.DAO;

import myusick.model.DTO.Publications;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by Cuenta de clase on 02/04/2015.
 */
public class PublicacionDAO {

    private Connection con;

    public void setConnection(Connection con){
        this.con = con;
    }

    public Publications[] getPublicacionesById(int uuid){
        try{
            String queryString = "select idPublicacion,contenido,fecha from Publicacion where Publicante_UUID=?";
            PreparedStatement preparedStatement = con.prepareStatement(queryString);
            preparedStatement.setInt(1, uuid);
            ResultSet resultSet = preparedStatement.executeQuery();
            int i = 0; Publications[] result = new Publications[10];
            while(resultSet.next()){
                result[i] = new Publications(resultSet.getInt(1),resultSet.getString(2),resultSet.getInt(3));
                i++;
            }
            return result;
        }catch (Exception e) {
            e.printStackTrace(System.err);
            return null;
        }
    }
}
