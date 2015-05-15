package myusick.model.dao;

import myusick.model.connection.PoolManager;

import java.sql.*;

/**
 * Created by Cuenta de clase on 02/04/2015.
 */
public class PublicanteDAO {

    public int insertarPublicante(boolean tipo){
        PoolManager pool = PoolManager.getInstance();
        Connection con = pool.getConnection();
        try {
            String query="insert into publicante (tipoPublicante) values (?)";
            PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setBoolean(1,tipo);
            int insertedRows = ps.executeUpdate();
            if(insertedRows == 1){
                ResultSet keys = ps.getGeneratedKeys();
                keys.next();
                int uuid = keys.getInt(1);
                con.commit();
                pool.returnConnection(con);
                return uuid;
            }else{
                con.rollback();
                pool.returnConnection(con);
                return -1;
            }

        } catch (SQLException e) {
            try{
                con.rollback();
            }catch(SQLException e2){e2.printStackTrace();}
            e.printStackTrace();
            pool.returnConnection(con);
            return -1;
        }
        
    }

    public String getAvatarById(int id){
        PoolManager pool = PoolManager.getInstance();
        Connection con = pool.getConnection();
        try{
            /* Primero intentamos ver si es una persona */
            String query="select avatar from persona where publicante_uuid = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            if(!rs.next()){
                /* No es una persona, probamos con grupo */
                con.rollback();
                query = "select avatar from grupo where publicante_uuid = ?";
                ps = con.prepareStatement(query);
                ps.setInt(1,id);
                rs = ps.executeQuery();
                if(!rs.next()){
                    /* este publicante no tiene avatar */
                    con.rollback();
                    pool.returnConnection(con);
                    return null;
                }
            }
            /* Si llegamos aqui es que hay avatar */
            String resultado = rs.getString(1);
            pool.returnConnection(con);
            return resultado;

        }catch (SQLException e) {
            try{
                con.rollback();
            }catch(SQLException e2){e2.printStackTrace();}
            e.printStackTrace();
            pool.returnConnection(con);
            return null;
        }
    }

    public String getNombreById(int id){
        PoolManager pool = PoolManager.getInstance();
        Connection con = pool.getConnection();
        try{
            /* Primero intentamos ver si es una persona */
            String query="select nombre from persona where publicante_uuid = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            if(!rs.next()){
                /* No es una persona, probamos con grupo */
                con.rollback();
                query = "select nombre from grupo where publicante_uuid = ?";
                ps = con.prepareStatement(query);
                ps.setInt(1,id);
                rs = ps.executeQuery();
                if(!rs.next()){
                    /* este publicante no tiene avatar */
                    con.rollback();
                    pool.returnConnection(con);
                    return null;
                }
            }
            /* Si llegamos aqui es que hay avatar */
            String resultado = rs.getString(1);
            pool.returnConnection(con);
            return resultado;

        }catch (SQLException e) {
            try{
                con.rollback();
            }catch(SQLException e2){e2.printStackTrace();}
            e.printStackTrace();
            pool.returnConnection(con);
            return null;
        }
    }

}
