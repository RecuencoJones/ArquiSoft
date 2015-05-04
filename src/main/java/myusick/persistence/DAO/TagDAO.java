package myusick.persistence.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import myusick.model.dto.TagDTO;
import myusick.persistence.connection.ConnectionAdmin;

import java.sql.*;

/**
 * Created by Cuenta de clase on 02/04/2015.
 */
public class TagDAO {

    private Connection con;

    public void setConnection(Connection con) {
        try{
            this.con = con;
            this.con.setAutoCommit(false);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public ArrayList<String> getTagsByPersona(int uuid) {
        ArrayList<String> result = new ArrayList<>();
        try {
            String queryString = "select nombreTag from tag where idTag in (select idTag " +
                    "from persona_tiene_tag where UUID_P=?)";
            PreparedStatement preparedStatement = con.prepareStatement(queryString);
            preparedStatement.setInt(1, uuid);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result.add(resultSet.getString(1));
            }
            con.commit();
            return result;
        } catch (Exception e) {
            try{
                con.rollback();
            }catch(SQLException e2){e2.printStackTrace();}
            e.printStackTrace(System.err);
            return null;
        }
    }

    public ArrayList<String> getTagsByGrupo(int uuid) {
        ArrayList<String> result = new ArrayList<>();
        try {
            String queryString = "select nombreTag from tag where idTag in (select idTag " +
                    "from grupo_tiene_tag where UUID_G=?)";
            PreparedStatement preparedStatement = con.prepareStatement(queryString);
            preparedStatement.setInt(1, uuid);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result.add(resultSet.getString(1));
            }
            con.commit();
            return result;
        } catch (Exception e) {
            try{
                con.rollback();
            }catch(SQLException e2){e2.printStackTrace();}
            e.printStackTrace(System.err);
            return null;
        }
    }

    public boolean registrarTag(TagDTO td) {
        try {
            int existeTag = getIdByNombre(td.getNombre());
            if (existeTag == -1) {
                /*El tag no existe */
                String query = "insert into tag (nombreTag) values (?)";
                PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, td.getNombre());
                int insertedRows = ps.executeUpdate();
                if (insertedRows == 1) {
                    ResultSet keys = ps.getGeneratedKeys();
                    keys.next();
                    int uuid = keys.getInt(1);
                    /* anadimos ahora a la tabla de asociacion con el publicante */
                    con.commit();
                    return asociarTag(uuid, td.getPublicante());

                } else{
                    con.rollback();
                    return false;
                }
            } else if (existeTag == -2) {
                /*Error de BD*/
                con.rollback();
                return false;
            } else {
                /*el tag existe*/
                con.rollback();
                return asociarTag(existeTag, td.getPublicante());
            }
        } catch (SQLException e) {
            try{
                con.rollback();
            }catch(SQLException e2){e2.printStackTrace();}
            e.printStackTrace();
            return false;
        }
    }

    public int getIdByNombre(String nombre){
        try{
            String query = "select idTag from tag where nombreTag = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, nombre);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                con.commit();
                return rs.getInt(1);
            }
            else{
                con.rollback();
                return -1;
            }
        }catch(SQLException e){
            try{
                con.rollback();
            }catch(SQLException e2){e2.printStackTrace();}
            e.printStackTrace();
            return -2;
        }
    }

    private boolean asociarTag(int idTag, int idPublicante) {
        try {
            GrupoDAO gdao = new GrupoDAO();
            PersonaDAO pdao = new PersonaDAO();
            gdao.setConnection(ConnectionAdmin.getConnection());
            pdao.setConnection(ConnectionAdmin.getConnection());
            String query = null;
            if (gdao.esUnGrupo(idPublicante)) {
                query = "insert into grupo_tiene_tag (uuid_g,idTag) values (?,?)";
            } else if (pdao.esUnaPersona(idPublicante)) {
                query = "insert into persona_tiene_tag (uuid_p,idTag) values (?,?)";
            } else {
                /* error, el publicante no existe*/
                con.rollback();
                gdao.closeConnection();
                pdao.closeConnection();
                return false;
            }
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, idPublicante);
            ps.setInt(2, idTag);
            int insertedRows = ps.executeUpdate();
            if (insertedRows == 1) {
                con.commit();
                gdao.closeConnection();
                pdao.closeConnection();
                return true;
            } else {
                con.rollback();
                gdao.closeConnection();
                pdao.closeConnection();
                return false;
            }
        } catch (SQLException e) {
            try{
                con.rollback();
            }catch(SQLException e2){e2.printStackTrace();}
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
