package myusick.persistence.DAO;

import myusick.model.dto.PublisherDTO;
import myusick.persistence.VO.Grupo;
import myusick.persistence.VO.Persona;
import myusick.persistence.connection.ConnectionAdmin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Cuenta de clase on 02/04/2015.
 */
public class GrupoDAO {

    private Connection con;

    public void setConnection(Connection con){
        this.con = con;
    }

    public ArrayList<PublisherDTO> getMembersGroup(int uuid){
        ArrayList<PublisherDTO> result = new ArrayList<>();
        try{
            String queryString = "select Publicante_UUID,nombre from Persona where publicante_uuid " +
                    "in (select uuid_p from es_integrante where uuid_g=?)";
            PreparedStatement preparedStatement = con.prepareStatement(queryString);
            preparedStatement.setInt(1, uuid);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                result.add(new PublisherDTO(resultSet.getInt(1),resultSet.getString(2)));
            }
            return result;
        }catch (SQLException e) {
            e.printStackTrace(System.err);
            return null;
        }catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace(System.err);
            return result;
        }
    }

    public boolean esUnGrupo(int uuid){
        try {
            String queryString = "select tipoPublicante from publicante where uuid=?";
            PreparedStatement preparedStatement = con.prepareStatement(queryString);
            preparedStatement.setInt(1, uuid);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next())
                return resultSet.getBoolean(1)==true;
            else return false;
        }catch(SQLException e){return false;}
    }

    public Grupo getDataProfile(int uuid){
        try{
            String queryString = "select nombre,descripcion,anyofundacion,avatar from grupo where publicante_uuid = ?";
            PreparedStatement preparedStatement = con.prepareStatement(queryString);
            preparedStatement.setInt(1, uuid);
            ResultSet resultSet = preparedStatement.executeQuery();
            int i = 0;
            return new Grupo(uuid,resultSet.getString("nombre"),resultSet.getLong("anyofundacion"),
                    resultSet.getString("descripcion"),resultSet.getString("avatar"));
        }catch (Exception e) {
            e.printStackTrace(System.err);
            return null;
        }
    }


}
