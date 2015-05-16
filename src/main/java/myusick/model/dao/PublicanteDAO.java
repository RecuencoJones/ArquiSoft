package myusick.model.dao;

import myusick.model.connection.PoolManager;

import java.sql.*;

/**
 * Created by Cuenta de clase on 02/04/2015.
 */
public class PublicanteDAO {

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

    //    public boolean editarPublicante(int uuid, boolean nuevo_tipo) {
//        PoolManager pool = PoolManager.getInstance();
//        Connection con = pool.getConnection();
//        try {
//            String query4 = "update publicante set tipoPublicante=? where UUID=?";
//            PreparedStatement ps = con.prepareStatement(query4);
//            ps.setBoolean(1, nuevo_tipo);
//            ps.setInt(2, uuid);
//            int resul = ps.executeUpdate();
//            boolean resultado;
//            if (resul > 0) resultado = true;
//            else resultado = false;
//            pool.returnConnection(con);
//            return resultado;
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            pool.returnConnection(con);
//            return false;
//        }
//    }
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
//    public boolean borrarPublicante(int uuid) {
//            /* Recogemos la informacion de que tipo de publicante es para el futuro */
//        boolean esGrupo = new GrupoDAO().esUnGrupo(uuid);
//        System.out.println("es grupo "+esGrupo);
//            /* Primero borramos los seguidores */
//        boolean resultado = new SeguirDAO().eliminarSeguidorySeguido(uuid);
//        System.out.println("seguidores "+resultado);
//            /* Ahora borramos sus tags */
//        if (esGrupo) {
//            resultado = resultado && new TagDAO().borrarTagsAsociadas(uuid, "Grupo");
//                /* Borramos sus publicaciones */
//            resultado = resultado && new PublicacionDAO().borrarPublicaciones(uuid);
//                /* Borramos los miembros del grupo */
//            resultado = resultado && new GrupoDAO().borrarGrupo(uuid);
//        } else {
//            resultado = resultado && new TagDAO().borrarTagsAsociadas(uuid, "Persona");
//                /* Borramos sus aptitudes */
//            resultado = resultado && new AptitudDAO().borrarAptitudesAsociadas(uuid);
//                /* Lo borramos de sus grupos */
//            resultado = resultado && new PersonaDAO().borrarDeGrupos(uuid);
//                /* Borramos sus publicaciones */
//            resultado = resultado && new PublicacionDAO().borrarPublicaciones(uuid);
//                /* Ahora borramos a la propia persona */
//            resultado = resultado && new PersonaDAO().borrarPersona(uuid);
//
//        }
//            /* Por ultimo lo eliminamos de la tabla de publicantes si ha ido bien hasta ahora*/
//        if (resultado) {
//            PoolManager pool = PoolManager.getInstance();
//            Connection con = pool.getConnection();
//            try {
//                String query = "delete from publicante where UUID=?";
//                PreparedStatement ps = con.prepareStatement(query);
//                ps.setInt(1, uuid);
//                int deleteRows = ps.executeUpdate();
//                if (deleteRows == 1) {
//                    con.commit();
//                    resultado = true;
//                } else {
//                    con.rollback();
//                    resultado = false;
//                }
//                pool.returnConnection(con);
//            } catch (Exception ex) {
//                ex.printStackTrace();
//                try {
//                    con.rollback();
//                } catch (SQLException e2) {
//                    e2.printStackTrace();
//                }
//                pool.returnConnection(con);
//                return false;
//            }
//        }
//        return resultado;
//    }

}
