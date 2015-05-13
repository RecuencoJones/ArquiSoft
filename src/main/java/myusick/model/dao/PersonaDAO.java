package myusick.model.dao;

import myusick.controller.dto.*;
import myusick.model.connection.PoolManager;
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

    public Persona getDataProfile(int uuid){
        PoolManager pool = PoolManager.getInstance();
        Connection con = pool.getConnection();
        try{
            String queryString = "select nombre,descripcion,avatar from persona where publicante_uuid = ?";
            PreparedStatement preparedStatement = con.prepareStatement(queryString);
            preparedStatement.setInt(1, uuid);
            ResultSet resultSet = preparedStatement.executeQuery();
            pool.returnConnection(con);
            if(resultSet.next())
                return new Persona(uuid,resultSet.getString("nombre"),null,null,0,null,null,0,
                        resultSet.getString("descripcion"),null,resultSet.getString("avatar"));
            else return null;
        }catch (Exception e) {
            try{
                con.rollback();
            }catch(SQLException e2){e2.printStackTrace();}
            e.printStackTrace();
            pool.returnConnection(con);
            return null;
        }
    }
    public boolean esUnaPersona(int uuid){
        PoolManager pool = PoolManager.getInstance();
        Connection con = pool.getConnection();
        try {
            String queryString = "select tipoPublicante from publicante where uuid=?";
            PreparedStatement preparedStatement = con.prepareStatement(queryString);
            preparedStatement.setInt(1, uuid);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                con.commit();
                pool.returnConnection(con);
                return resultSet.getBoolean(1)==false;
            }
            else{
                con.rollback();
                pool.returnConnection(con);
                return false;
            }
        }catch(SQLException e){
            try{
                con.rollback();
            }catch(SQLException e2){e2.printStackTrace();}
            e.printStackTrace();
            pool.returnConnection(con);
            return false;
        }
    }

    public ArrayList<PublisherDTO> getGroupsByMember(int member){
        PoolManager pool = PoolManager.getInstance();
        Connection con = pool.getConnection();
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
            pool.returnConnection(con);
            return result;
        }catch (Exception e) {
            try{
                con.rollback();
            }catch(SQLException e2){e2.printStackTrace();}
            e.printStackTrace();
            pool.returnConnection(con);
            return null;
        }
    }

    public LoginDTO getLoginData(String email, String password){
        PoolManager pool = PoolManager.getInstance();
        Connection con = pool.getConnection();
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
                pool.returnConnection(con);
                return l;
            }else{
                /* El usuario no existe */
                con.rollback();
                pool.returnConnection(con);
                return null;
            }
        } catch (SQLException e) {
            try{
                con.rollback();
            }catch(SQLException e2){e2.printStackTrace();}
            e.printStackTrace();
            pool.returnConnection(con);
            return new LoginDTO();
        }
    }
    
    public int registerUser(RegisterDTO rd) {
        PoolManager pool = PoolManager.getInstance();
        Connection con = pool.getConnection();
        try {
            PublicanteDAO pdao = new PublicanteDAO();
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
                    con.commit();
                    pool.returnConnection(con);
                    return uuid;
                }else{
                    con.rollback();
                    pool.returnConnection(con);
                    return -1;
                }
            } else {
                con.rollback();
                pool.returnConnection(con);
                return -1;
            }
        } catch (Exception e) {
            try{
                con.rollback();
            }catch(SQLException e2){e2.printStackTrace();}
            e.printStackTrace();
            pool.returnConnection(con);
            return -1;
        }        
    }

    public List<ShortProfileDTO> buscarPorNombre(String nombre){
        PoolManager pool = PoolManager.getInstance();
        Connection con = pool.getConnection();
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
            pool.returnConnection(con);
            return resultado;
        }catch (Exception e) {
            try{
                con.rollback();
            }catch(SQLException e2){e2.printStackTrace();}
            e.printStackTrace();
            pool.returnConnection(con);
            return null;
        }
    }

    public List<ShortProfileDTO> buscarPorTag(String tag){
        PoolManager pool = PoolManager.getInstance();
        Connection con = pool.getConnection();
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
            pool.returnConnection(con);
            return resultado;
        }catch (Exception e) {
            try{
                con.rollback();
            }catch(SQLException e2){e2.printStackTrace();}
            e.printStackTrace();
            pool.returnConnection(con);
            return null;
        }
    }

    public List<ShortProfileDTO> buscarPorAptitud(String aptitud){
        PoolManager pool = PoolManager.getInstance();
        Connection con = pool.getConnection();
        List<ShortProfileDTO> resultado = new ArrayList<>();
        try{
            String query = "select nombre, avatar, Publicante_UUID from persona where publicante_uuid in(" +
                    "  select UUID_P from tiene_aptitud where idAptitud in (" +
                    "    select idAptitud from aptitud where nombre=?" +
                    "))";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, aptitud);
            ResultSet rs = ps.executeQuery();
            AptitudDAO adao = new AptitudDAO();
            TagDAO tdao = new TagDAO();
            PublicacionDAO pdao = new PublicacionDAO();
            while(rs.next()){
                int uuid = rs.getInt("Publicante_UUID");
                ShortProfileDTO perfil = new ShortProfileDTO(uuid,rs.getString("nombre"),rs.getString("avatar"),false);
                resultado.add(perfil);
            }
            pool.returnConnection(con);
            return resultado;
        }catch (Exception e) {
            try{
                con.rollback();
            }catch(SQLException e2){e2.printStackTrace();}
            e.printStackTrace();
            pool.returnConnection(con);
            return null;
        }
    }

}
