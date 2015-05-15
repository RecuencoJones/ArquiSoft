package myusick.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import myusick.controller.dto.SkillTagDTO;
import myusick.model.connection.PoolManager;

import java.sql.*;

/**
 * Created by Cuenta de clase on 02/04/2015.
 */
public class TagDAO {

    public ArrayList<String> getTagsByPersona(int uuid) {
        PoolManager pool = PoolManager.getInstance();
        Connection con = pool.getConnection();
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
            pool.returnConnection(con);
            return result;
        } catch (Exception e) {
            try{
                con.rollback();
            }catch(SQLException e2){e2.printStackTrace();}
            e.printStackTrace(System.err);
            pool.returnConnection(con);
            return null;
        }
    }

    public ArrayList<String> getTagsByGrupo(int uuid) {
        PoolManager pool = PoolManager.getInstance();
        Connection con = pool.getConnection();
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
            pool.returnConnection(con);
            return result;
        } catch (Exception e) {
            try{
                con.rollback();
            }catch(SQLException e2){e2.printStackTrace();}
            e.printStackTrace(System.err);
            pool.returnConnection(con);
            return null;
        }
    }

    public boolean registrarTag(SkillTagDTO td) {
        PoolManager pool = PoolManager.getInstance();
        Connection con = pool.getConnection();
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
                    pool.returnConnection(con);
                    return asociarTag(uuid, td.getPublicante());

                } else{
                    con.rollback();
                    pool.returnConnection(con);
                    return false;
                }
            } else if (existeTag == -2) {
                /*Error de BD*/
                con.rollback();
                pool.returnConnection(con);
                return false;
            } else {
                /*el tag existe*/
                con.rollback();
                pool.returnConnection(con);
                return asociarTag(existeTag, td.getPublicante());
            }
        } catch (SQLException e) {
            try{
                con.rollback();
            }catch(SQLException e2){e2.printStackTrace();}
            e.printStackTrace();
            pool.returnConnection(con);
            return false;
        }
    }

    public int getIdByNombre(String nombre){
        PoolManager pool = PoolManager.getInstance();
        Connection con = pool.getConnection();
        try{
            String query = "select idTag from tag where nombreTag = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, nombre);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                con.commit();
                int resultado = rs.getInt(1);
                pool.returnConnection(con);
                return resultado;
            }
            else{
                con.rollback();
                pool.returnConnection(con);
                return -1;
            }
        }catch(SQLException e){
            try{
                e.printStackTrace();
                con.rollback();
            }catch(SQLException e2){e2.printStackTrace();}
            e.printStackTrace();
            pool.returnConnection(con);
            return -2;
        }
    }

    private boolean asociarTag(int idTag, int idPublicante) {
        PoolManager pool = PoolManager.getInstance();
        Connection con = pool.getConnection();
        try {
            GrupoDAO gdao = new GrupoDAO();
            PersonaDAO pdao = new PersonaDAO();
            String query = null;
            if (gdao.esUnGrupo(idPublicante)) {
                query = "insert into grupo_tiene_tag (uuid_g,idTag) values (?,?)";
            } else if (pdao.esUnaPersona(idPublicante)) {
                query = "insert into persona_tiene_tag (uuid_p,idTag) values (?,?)";
            } else {
                /* error, el publicante no existe*/
                con.rollback();
                pool.returnConnection(con);
                return false;
            }
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, idPublicante);
            ps.setInt(2, idTag);
            int insertedRows = ps.executeUpdate();
            if (insertedRows == 1) {
                con.commit();
                pool.returnConnection(con);
                return true;
            } else {
                con.rollback();
                pool.returnConnection(con);
                return false;
            }
        } catch (SQLException e) {
            try{
                con.rollback();
            }catch(SQLException e2){e2.printStackTrace();}
            e.printStackTrace();
            pool.returnConnection(con);
            return false;
        }
    }
}
