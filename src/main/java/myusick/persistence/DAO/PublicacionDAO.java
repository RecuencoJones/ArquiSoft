package myusick.persistence.DAO;

import myusick.model.dto.PostDTO;
import myusick.model.dto.PublicationsDTO;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Cuenta de clase on 02/04/2015.
 */
public class PublicacionDAO {

    private Connection con;

    public void setConnection(Connection con){
        this.con = con;
    }

    public ArrayList<PublicationsDTO> getPublicacionesById(int uuid){
        ArrayList<PublicationsDTO> result = new ArrayList<>();
        try{
            String queryString = "select idPublicacion,contenido,fecha from Publicacion where Publicante_UUID=?";
            PreparedStatement preparedStatement = con.prepareStatement(queryString);
            preparedStatement.setInt(1, uuid);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                result.add(new PublicationsDTO(resultSet.getInt(1),resultSet.getString(2),resultSet.getLong(3)));
            }
            return result;
        }catch (Exception e) {
            e.printStackTrace(System.err);
            return null;
        }
    }

    public int insertarPublicacion(long fecha, String contenido, int publicante_uuid){
        try {
            String query="insert into publicacion (fecha, contenido, publicante_uuid) values (?,?,?)";
            PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1,fecha);
            ps.setString(2,contenido);
            ps.setInt(3,publicante_uuid);
            int insertedRows = ps.executeUpdate();
            if(insertedRows == 1){
                ResultSet keys = ps.getGeneratedKeys();
                keys.next();
                int idpub = keys.getInt(1);
                return idpub;
            }else return -1;

        } catch (SQLException e) {
            return -1;
        }

    }

    public boolean closeConnection(){
        try {
            con.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}