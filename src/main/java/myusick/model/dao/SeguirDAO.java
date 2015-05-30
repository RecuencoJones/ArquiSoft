package myusick.model.dao;

import myusick.controller.dto.PublisherDTO;
import myusick.model.connection.PoolManager;
import java.sql.*;
import java.util.ArrayList;

/**
 * Myusick. Arquitectura Software 2015
 * @author David Recuenco (648701)
 * @author Guillermo Perez (610382)
 * @author Sandra Campos (629928)
 *
 * Clase DAO que proporciona el acceso a los datos relacionados
 * con la funcionalidad de seguir a personas y grupos
 */
public class SeguirDAO {
    /**
     * Establece una relacion entre dos publicantes
     * @param seguidor id de la persona que quiere seguir a alguien
     * @param seguido id de la persona que va a ser seguida
     * @return cierto en caso de que la operacion resulte con exito,
     * falso en caso contrario
     */
    public boolean follow(int seguidor, int seguido){
        PoolManager pool = PoolManager.getInstance();
        Connection con = pool.getConnection();
        try {
            String query = "insert into Seguir (seguidor,seguido) values (?,?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, seguidor);
            ps.setInt(2, seguido);
            ps.executeUpdate();
            con.commit();
            pool.returnConnection(con);
            return true;
        }catch (SQLException e){
            try{
                con.rollback();
            }catch(SQLException e2){e2.printStackTrace();}
            e.printStackTrace();
            pool.returnConnection(con);
            return false;
        }
    }

    /**
     * Elimina la relacion existente entre dos persona
     * @param seguidor id de la persona que quiere seguir a alguien
     * @param seguido id de la persona que va a ser seguida
     * @return cierto en caso de que la operacion resulte con exito,
     * falso en caso contrario
     */
    public boolean unfollow(int seguidor, int seguido){
        PoolManager pool = PoolManager.getInstance();
        Connection con = pool.getConnection();
        try {
            String query = "delete from Seguir where seguidor=? and seguido=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, seguidor);
            ps.setInt(2, seguido);
            ps.executeUpdate();
            con.commit();
            pool.returnConnection(con);
            return true;
        }catch (SQLException e){
            try{
                con.rollback();
            }catch(SQLException e2){e2.printStackTrace();}
            e.printStackTrace();
            pool.returnConnection(con);
            return false;
        }
    }

    /**
     * Devuelve un objeto de tipo booleano que indicara si los dos
     * publicantes tienen una relacion en la base de datos
     * @param seguidor id de la persona que quiere seguir a alguien
     * @param seguido id de la persona que va a ser seguida
     * @return cierto en caso de que seguidor y seguido tengan una relacion
     * en la base de datos
     */
    public boolean isfollow(int seguidor, int seguido){
        PoolManager pool = PoolManager.getInstance();
        Connection con = pool.getConnection();
        try {
            String query = "select * from Seguir where seguidor=? and seguido=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, seguidor);
            ps.setInt(2, seguido);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                con.commit();
                pool.returnConnection(con);
                return true;
            }
            else{
                try{
                    con.rollback();
                }catch(SQLException e2){e2.printStackTrace();}
                pool.returnConnection(con);
                return false;
            }
        }catch (SQLException e){
            try{
                con.rollback();
            }catch(SQLException e2){e2.printStackTrace();}
            e.printStackTrace();
            pool.returnConnection(con);
            return false;
        }
    }

    /**
     * Extrae los ids de todos los seguidores que un publicante tenga
     * @param uuid id del publicante
     * @return lista con los ids de los seguidores
     */
    public ArrayList<Integer> getFollowersIds(int uuid) {
        PoolManager pool = PoolManager.getInstance();
        Connection con = pool.getConnection();
        ArrayList<Integer> result = new ArrayList<>();
        try{
            String query = "select seguidor from Seguir where seguido=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, uuid);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                result.add(rs.getInt(1));
            }
            con.commit();
            pool.returnConnection(con);
            return result;
        }catch(SQLException e){
            try{
                con.rollback();
            }catch(SQLException e2){e2.printStackTrace();}
            e.printStackTrace();
            pool.returnConnection(con);
            return null;
        }
    }

    /**
     * Extrae una lista con la informacion de los publicantes que siguen
     * a un publicante concreto pasado por parametro
     * @param uuid id del publicante del que se quiere saber la informacion
     * @return lista con la informacion de cada publicante que sigue al publicante
     * pasado por parametro
     */
    public ArrayList<PublisherDTO> getFollowers(int uuid) {
        PoolManager pool = PoolManager.getInstance();
        Connection con = pool.getConnection();
        ArrayList<PublisherDTO> result = new ArrayList<PublisherDTO>();
        try{
            String query =  "select seguidor from seguir where seguido = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, uuid);
            ResultSet rs = ps.executeQuery();
            con.commit();
            while(rs.next()){
                /* Con los ids sacamos la informacion de perfil */
                int id = rs.getInt(1);
                GrupoDAO g = new GrupoDAO();
                PublisherDTO pub;
                if(g.esUnGrupo(id)){
                    pub = g.getDataPublisher(id);
                    pub.setType(true);
                }else{
                    pub = new PersonaDAO().getDataPublisher(id);
                    pub.setType(false);
                }
                result.add(pub);
            }
            pool.returnConnection(con);
            return result;
        }catch(SQLException e){
            try{
                con.rollback();
            }catch(SQLException e2){e2.printStackTrace();}
            e.printStackTrace();
            pool.returnConnection(con);
            return null;
        }
    }

    /**
     * Extrae una lista con toda la informacion de aquellos publicantes
     * a los que el publicante pasado por parametro sigue.
     * @param uuid id del publicante del que se desea obtener la informacion
     * @return lista con la informacion de aquellos publicantes
     * a los que el publicante pasado por parametro sigue
     */
    public ArrayList<PublisherDTO> getFollowing(int uuid) {
        PoolManager pool = PoolManager.getInstance();
        Connection con = pool.getConnection();
        ArrayList<PublisherDTO> result = new ArrayList<PublisherDTO>();
        try{
            String query =  "select seguido from seguir where seguidor = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, uuid);
            ResultSet rs = ps.executeQuery();
            con.commit();
            while(rs.next()){
                /* Con los ids sacamos la informacion de perfil */
                int id = rs.getInt(1);
                GrupoDAO g = new GrupoDAO();
                PublisherDTO pub;
                if(g.esUnGrupo(id)){
                    pub = g.getDataPublisher(id);
                    pub.setType(true);
                }else{
                    pub = new PersonaDAO().getDataPublisher(id);
                    pub.setType(false);
                }
                result.add(pub);
            }
            pool.returnConnection(con);
            return result;
        }catch(SQLException e){
            try{
                con.rollback();
            }catch(SQLException e2){e2.printStackTrace();}
            e.printStackTrace();
            pool.returnConnection(con);
            return null;
        }
    }

    /**
     * Borra todas aquellas relaciones que tenga un publicante concreto
     * @param uuid id del publicante
     * @return cierto en caso de que la operacion resulte con exito,
     * falso en caso contrario
     */
    public boolean eliminarSeguidorySeguido(int uuid){
        PoolManager pool = PoolManager.getInstance();
        Connection con = pool.getConnection();
        try{
            String query1 = "delete from seguir where seguidor=? or seguido=?";
            PreparedStatement ps1 = con.prepareStatement(query1);
            ps1.setInt(1, uuid);
            ps1.setInt(2, uuid);
            ps1.executeUpdate();
            con.commit();
            pool.returnConnection(con);
            return true;
        }catch(Exception ex){
            ex.printStackTrace();
            pool.returnConnection(con);
            return false;
        }
    }
}
