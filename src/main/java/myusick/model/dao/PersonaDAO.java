package myusick.model.dao;

import myusick.controller.dto.*;
import myusick.model.vo.Persona;
import myusick.model.connection.ConnectionAdmin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cuenta de clase on 02/04/2015.
 */
public class PersonaDAO {

    private Connection con;

    public void setConnection(Connection con){
        try{
            this.con = con;
            this.con.setAutoCommit(false);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public Persona getDataProfile(int uuid){
        try{
            String queryString = "select nombre,descripcion,avatar from persona where publicante_uuid = ?";
            PreparedStatement preparedStatement = con.prepareStatement(queryString);
            preparedStatement.setInt(1, uuid);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next())
                return new Persona(uuid,resultSet.getString("nombre"),null,null,0,null,null,0,
                        resultSet.getString("descripcion"),null,resultSet.getString("avatar"));
            else return null;
        }catch (Exception e) {
            try{
                con.rollback();
            }catch(SQLException e2){e2.printStackTrace();}
            e.printStackTrace();
            return null;
        }
    }
    public boolean esUnaPersona(int uuid){
        try {
            String queryString = "select tipoPublicante from publicante where uuid=?";
            PreparedStatement preparedStatement = con.prepareStatement(queryString);
            preparedStatement.setInt(1, uuid);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                con.commit();
                return resultSet.getBoolean(1)==false;
            }
            else{
                con.rollback();
                return false;
            }
        }catch(SQLException e){
            try{
                con.rollback();
            }catch(SQLException e2){e2.printStackTrace();}
            e.printStackTrace();
            return false;
        }
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
            con.commit();
            return result;
        }catch (Exception e) {
            try{
                con.rollback();
            }catch(SQLException e2){e2.printStackTrace();}
            e.printStackTrace();
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
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            int i = 1;
            if(rs.next()){
                l.setUserId(rs.getInt(1));
                l.setGroups(getGroupsByMember(l.getUserId()));
                con.commit();
                return l;
            }else{
                /* El usuario no existe */
                con.rollback();
                return null;
            }
        } catch (SQLException e) {
            try{
                con.rollback();
            }catch(SQLException e2){e2.printStackTrace();}
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
                    pdao.closeConnection();
                    con.commit();
                    return uuid;
                }else{
                    pdao.closeConnection();
                    con.rollback();
                    return -1;
                }
            } else {
                pdao.closeConnection();
                con.rollback();
                return -1;
            }
        } catch (Exception e) {
            try{
                con.rollback();
            }catch(SQLException e2){e2.printStackTrace();}
            e.printStackTrace();
            return -1;
        }        
    }

    public List<ShortProfileDTO> buscarPorNombre(String nombre){
        List<ShortProfileDTO> resultado = new ArrayList<>();
        try{
            String query = "select Publicante_uuid, nombre, avatar from persona where nombre like ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, "%"+nombre+"%");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int uuid = rs.getInt(1);
                ShortProfileDTO perfil = new ShortProfileDTO(uuid,rs.getString("nombre"),rs.getString("avatar"),false);
                resultado.add(perfil);
            }
            return resultado;
        }catch (Exception e) {
            try{
                con.rollback();
            }catch(SQLException e2){e2.printStackTrace();}
            e.printStackTrace();
            return null;
        }
    }

    public List<ShortProfileDTO> buscarPorTag(String tag){
        List<ShortProfileDTO> resultado = new ArrayList<>();
        try{
            String query = "select nombre,avatar, Publicante_UUID from persona where publicante_uuid in(" +
                    "  select UUID_P from persona_tiene_tag where idTag in (" +
                    "    select idTag from tag where nombreTag=?" +
                    "))";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, tag);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int uuid = rs.getInt("Publicante_UUID");
                ShortProfileDTO perfil = new ShortProfileDTO(uuid,rs.getString("nombre"),rs.getString("avatar"),false);
                resultado.add(perfil);
            }
            return resultado;
        }catch (Exception e) {
            try{
                con.rollback();
            }catch(SQLException e2){e2.printStackTrace();}
            e.printStackTrace();
            return null;
        }
    }

    public List<ShortProfileDTO> buscarPorAptitud(String aptitud){
        List<ShortProfileDTO> resultado = new ArrayList<>();
        try{
            String query = "select nombre, avatar, Publicante_UUID from persona where publicante_uuid in(" +
                    "  select UUID_P from tiene_aptitud where idAptitud in (" +
                    "    select idAptitud from aptitud where nombre=?" +
                    "))";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, aptitud);
            ResultSet rs = ps.executeQuery();
            AptitudDAO adao = new AptitudDAO(); adao.setConnection(ConnectionAdmin.getConnection());
            TagDAO tdao = new TagDAO(); tdao.setConnection(ConnectionAdmin.getConnection());
            PublicacionDAO pdao = new PublicacionDAO(); pdao.setConnection(ConnectionAdmin.getConnection());
            while(rs.next()){
                int uuid = rs.getInt("Publicante_UUID");
                ShortProfileDTO perfil = new ShortProfileDTO(uuid,rs.getString("nombre"),rs.getString("avatar"),false);
                resultado.add(perfil);
            }
            adao.closeConnection();
            tdao.closeConnection();
            pdao.closeConnection();
            return resultado;
        }catch (Exception e) {
            try{
                con.rollback();
            }catch(SQLException e2){e2.printStackTrace();}
            e.printStackTrace();
            return null;
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
