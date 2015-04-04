package myusick.persistence.DAO;

import myusick.model.DTO.LoginDTO;
import myusick.model.DTO.Publications;
import myusick.model.DTO.Publisher;
import myusick.persistence.VO.Grupo;
import myusick.persistence.VO.Persona;
import myusick.persistence.connection.ConnectionAdmin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Cuenta de clase on 02/04/2015.
 */
public class PersonaDAO {

    private Connection con;

    public void setConnection(Connection con){
        this.con = con;
    }

    public Persona getDataProfile(int uuid){
        try{
            String queryString = "select nombre,descripcion,avatar from persona where publicante_uuid = ?";
            PreparedStatement preparedStatement = con.prepareStatement(queryString);
            preparedStatement.setInt(1, uuid);
            ResultSet resultSet = preparedStatement.executeQuery();
            int i = 0; Persona p;
            if(resultSet.next())
                return new Persona(uuid,resultSet.getString("nombre"),null,null,0,null,null,0,
                        resultSet.getString("descripcion"),null,resultSet.getString("avatar"));
            else return null;
        }catch (Exception e) {
            e.printStackTrace(System.err);
            return null;
        }
    }
    public boolean esUnaPersona(int uuid){
        try {
            String queryString = "select tipoPublicante from publicante where uuid=?";
            PreparedStatement preparedStatement = con.prepareStatement(queryString);
            preparedStatement.setInt(1, uuid);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next())
                return resultSet.getBoolean(1)==false;
            else return false;
        }catch(SQLException e){return false;}
    }

    public Publisher[] getGroupsByMember(int member){
        try{
            String queryString = "select Publicante_UUID,nombre from Grupo where publicante_uuid " +
                    "in (select uuid_g from es_integrante where uuid_p=?)";
            PreparedStatement preparedStatement = con.prepareStatement(queryString);
            preparedStatement.setInt(1, member);
            ResultSet resultSet = preparedStatement.executeQuery();
            int i = 0; Publisher[] result = new Publisher[10];
            while(resultSet.next()){
                result[i] = new Publisher(resultSet.getInt(1),resultSet.getString(2));
                i++;
            }
            return result;
        }catch (Exception e) {
            e.printStackTrace(System.err);
            return null;
        }
    }

    public LoginDTO getLoginData(String email, String password){
        LoginDTO l = new LoginDTO();
        l.setUser(email);
        l.setPassword(password);
        try {
            String query="select publicante_uuid from persona where email=? and password=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1,"foo@bar.com");
            ps.setString(2,"1234");
            ResultSet rs = ps.executeQuery();
            int i = 1;
            if(rs.next()){
                l.setUserId(rs.getInt(1));
            }else{
                /* El usuario no existe */
                return null;
            }
            l.setGroups(getGroupsByMember(l.getUserId()));
            return l;
        } catch (SQLException e) {
            e.printStackTrace();
            return new LoginDTO();
        }
    }
}
