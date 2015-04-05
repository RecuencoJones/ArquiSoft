package myusick.persistence.DAO;

import myusick.model.dto.LoginDTO;
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

    public ArrayList<PublisherDTO> getGroupsByMember(int member){
        ArrayList<PublisherDTO> result = new ArrayList<>();
        try{
            String queryString = "select Publicante_UUID,nombre from Grupo where publicante_uuid " +
                    "in (select uuid_g from es_integrante where uuid_p=?)";
            PreparedStatement preparedStatement = con.prepareStatement(queryString);
            preparedStatement.setInt(1, member);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                result.add(new PublisherDTO(resultSet.getInt(1),resultSet.getString(2)));
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
            ps.setString(1,email);
            ps.setString(2,password);
            ResultSet rs = ps.executeQuery();
            int i = 1;
            if(rs.next()){
                l.setUserId(rs.getInt(1));
                l.setGroups(getGroupsByMember(l.getUserId()));
                return l;
            }else{
                /* El usuario no existe */
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return new LoginDTO();
        }
    }
    
    public int registerUser(RegisterDTO rd) {
        try {
            PublicanteDAO pdao = new PublicanteDAO();
            pdao.setConnection(ConnectionAdmin.getConnection());
            int uuid = pdao.insertarPublicante(false);
            if (uuid != -1) {
                String query = "insert into persona (Publicante_UUID,nombre,apellidos,email,password," +
                        "fechaNacimiento,ciudad,pais,telefono) values (?,?,?,?,?,?,?,?,?)";
                PreparedStatement ps = con.prepareStatement(query);
                ps.setInt(1, uuid);
                ps.setString(2, rd.getName());
                ps.setString(3, rd.getLastname());
                ps.setString(4, rd.getEmail());
                ps.setString(5, rd.getPassword());
                ps.setLong(6,Long.parseLong(rd.getBirthdate()));
                ps.setString(7, rd.getCity());
                ps.setString(8, rd.getCountry());
                ps.setString(9, rd.getPhone());
                int insertedRows = ps.executeUpdate();
                if (insertedRows == 1) {
                    return uuid;
                }else{
                    return -1;
                }
            } else {
                return -1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }        
    }
}
