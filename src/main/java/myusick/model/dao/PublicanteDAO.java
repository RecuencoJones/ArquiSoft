package myusick.model.dao;

import myusick.model.connection.PoolManager;
import java.sql.*;

/**
 * Myusick. Arquitectura Software 2015
 * @author David Recuenco (648701)
 * @author Guillermo Perez (610382)
 * @author Sandra Campos (629928)
 *
 * Clase DAO que proporciona el acceso a los datos relacionados
 * con los publicantes del sistema
 */
public class PublicanteDAO {

    /**
     * Inserta un publicante en la base de datos
     * @param tipo cierto en caso de que sea un grupo, falso
     *             en caso contrario
     * @return id que se le ha asignado automaticamente por la bd en
     * caso de que la insercion sea correcta, -1 en caso de error
     */
    public int insertarPublicante(boolean tipo) {
        PoolManager pool = PoolManager.getInstance();
        Connection con = pool.getConnection();
        try {
            String query = "insert into publicante (tipoPublicante) values (?)";
            PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setBoolean(1, tipo);
            int insertedRows = ps.executeUpdate();
            if (insertedRows == 1) {
                ResultSet keys = ps.getGeneratedKeys();
                keys.next();
                int uuid = keys.getInt(1);
                con.commit();
                pool.returnConnection(con);
                return uuid;
            } else {
                con.rollback();
                pool.returnConnection(con);
                return -1;
            }

        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
            e.printStackTrace();
            pool.returnConnection(con);
            return -1;
        }

    }

    /**
     * Extrae la url del avatar de un publicante concreto
     * @param id id del publicante
     * @return url del avatar del publicante, null en caso de que
     * el publicante no tenga avatar o que el publicante no exista
     */
    public String getAvatarById(int id) {
        PoolManager pool = PoolManager.getInstance();
        Connection con = pool.getConnection();
        try {
            /* Primero intentamos ver si es una persona */
            String query = "select avatar from persona where publicante_uuid = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                /* No es una persona, probamos con grupo */
                con.rollback();
                query = "select avatar from grupo where publicante_uuid = ?";
                ps = con.prepareStatement(query);
                ps.setInt(1, id);
                rs = ps.executeQuery();
                if (!rs.next()) {
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

        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
            e.printStackTrace();
            pool.returnConnection(con);
            return null;
        }
    }

    /**
     * Extrae el nombre de un publicante a partir de su id
     * @param id id del publicante
     * @return nombre del publicante, null en caso de que
     * el publicante no exista
     */
    public String getNombreById(int id) {
        PoolManager pool = PoolManager.getInstance();
        Connection con = pool.getConnection();
        try {
            /* Primero intentamos ver si es una persona */
            String query = "select nombre from persona where publicante_uuid = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                /* No es una persona, probamos con grupo */
                con.rollback();
                query = "select nombre from grupo where publicante_uuid = ?";
                ps = con.prepareStatement(query);
                ps.setInt(1, id);
                rs = ps.executeQuery();
                if (!rs.next()) {
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

        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
            e.printStackTrace();
            pool.returnConnection(con);
            return null;
        }
    }

    /**
     * Elimina un publicante de la base de datos
     * @param uuid id del publicante
     * @return cierto en caso de que la operacion se realice con exito,
     * falso en caso contrario
     */
    public boolean borrarPublicante(int uuid) {
        PoolManager pool = PoolManager.getInstance();
        Connection con = pool.getConnection();
        try {
            SeguirDAO sdao = new SeguirDAO();
            System.out.println("seguidor/seguido: " + sdao.eliminarSeguidorySeguido(uuid));

            String query2 = "delete from publicante where UUID=?";
            PreparedStatement ps2 = con.prepareStatement(query2);
            ps2.setInt(1, uuid);
            ps2.executeUpdate();
            con.commit();
            pool.returnConnection(con);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            try{
                con.rollback();
            }catch (SQLException e2){
                e2.printStackTrace();
            }
            pool.returnConnection(con);
            return false;
        }
    }

}
