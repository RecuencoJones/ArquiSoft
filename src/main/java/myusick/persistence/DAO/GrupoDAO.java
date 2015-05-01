package myusick.persistence.DAO;

import myusick.model.dto.GroupDTO;
import myusick.model.dto.PublisherDTO;
import myusick.model.dto.RegisterDTO;
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

    public void setConnection(Connection con) {
        this.con = con;
    }

    public ArrayList<PublisherDTO> getMembersGroup(int uuid){
        ArrayList<PublisherDTO> result = new ArrayList<>();
        try {
            String queryString = "select Publicante_UUID,nombre from Persona where publicante_uuid " +
                    "in (select uuid_p from es_integrante where uuid_g=?)";
            PreparedStatement preparedStatement = con.prepareStatement(queryString);
            preparedStatement.setInt(1, uuid);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result.add(new PublisherDTO(resultSet.getInt(1), resultSet.getString(2)));
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace(System.err);
            return null;
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace(System.err);
            return result;
        }
    }

    public boolean esUnGrupo(int uuid) {
        try {
            String queryString = "select tipoPublicante from publicante where uuid=?";
            PreparedStatement preparedStatement = con.prepareStatement(queryString);
            preparedStatement.setInt(1, uuid);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                return resultSet.getBoolean(1) == true;
            else return false;
        } catch (SQLException e) {
            return false;
        }
    }

    public Grupo getDataProfile(int uuid) {
        try {
            String queryString = "select nombre,descripcion,anyoFundacion,avatar from grupo where publicante_uuid = ?";
            PreparedStatement preparedStatement = con.prepareStatement(queryString);
            preparedStatement.setInt(1, uuid);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next())
                return new Grupo(uuid, resultSet.getString("nombre"), resultSet.getLong("anyoFundacion"),
                    resultSet.getString("descripcion"), resultSet.getString("avatar"));
            else return null;
        } catch (Exception e) {
            e.printStackTrace(System.err);
            return null;
        }
    }

    public boolean registerGroup(GroupDTO gd) {
        try {
            PublicanteDAO pdao = new PublicanteDAO();
            pdao.setConnection(ConnectionAdmin.getConnection());
            int uuid = pdao.insertarPublicante(true);
            if (uuid != -1) {
                String query = "insert into grupo (Publicante_UUID,nombre,anyofundacion,descripcion) values (?,?,?,?)";
                PreparedStatement ps = con.prepareStatement(query);
                ps.setInt(1, uuid);
                ps.setString(2, gd.getName());
                ps.setInt(3, Integer.parseInt(gd.getYear()));
                ps.setString(4, gd.getDescription());
                int insertedRows = ps.executeUpdate();
                if (insertedRows == 1) {
                /* insertamos al usuario en la tabla de union con el grupo */
                    System.out.println("grupo "+uuid+" persona "+gd.getCreator());
                    query = "insert into es_integrante (UUID_G,UUID_P) values (?,?)";
                    ps = con.prepareStatement(query);
                    ps.setInt(1, uuid);
                    ps.setInt(2,gd.getCreator());
                    insertedRows = ps.executeUpdate();
                    pdao.closeConnection();
                    if(insertedRows == 1){
                        return true;
                    }else return false;
                } else {
                    pdao.closeConnection();
                    return false;
                }
            } else {
                pdao.closeConnection();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
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
