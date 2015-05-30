package myusick.model.dao;

import myusick.controller.dto.PostDTO;
import myusick.controller.dto.PublicationsDTO;
import myusick.model.connection.PoolManager;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Myusick. Arquitectura Software 2015
 * @author David Recuenco (648701)
 * @author Guillermo Perez (610382)
 * @author Sandra Campos (629928)
 *
 * Clase DAO que proporciona el acceso a los datos relacionados
 * con las publicaciones del sistema
 */
public class PublicacionDAO {

    /**
     * Extrae las publicaciones de un publicante concreto
     * @param uuid id del publicante
     * @return lista con la informacion de las publicaciones
     * de un publicante concreto
     */
    public ArrayList<PublicationsDTO> getPublicacionesById(int uuid){
        PoolManager pool = PoolManager.getInstance();
        Connection con = pool.getConnection();
        ArrayList<PublicationsDTO> result = new ArrayList<>();
        try{
            String queryString = "select idPublicacion,contenido,fecha from Publicacion where Publicante_UUID=?";
            PreparedStatement preparedStatement = con.prepareStatement(queryString);
            preparedStatement.setInt(1, uuid);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                result.add(new PublicationsDTO(resultSet.getInt(1),resultSet.getString(2),resultSet.getLong(3)));
            }
            con.commit();
            pool.returnConnection(con);
            return result;
        }catch (Exception e) {
            try {con.rollback();}
            catch(SQLException e2){
                e2.printStackTrace();
                pool.returnConnection(con);
                return null;
            }
            e.printStackTrace();
            pool.returnConnection(con);
            return null;
        }
    }

    /**
     * Inserta una publicacion en la base de datos
     * @param fecha fecha de la publicacion, se establece en formato
     *              long porque cuenta los milisegundos que han pasado desde 1970
     *              hasta la fecha concreta. Imprescindible para la adaptacion
     *              con AngularJS en el front-end.
     * @param contenido texto de la publicacion
     * @param publicante_uuid id del publicante que ha realizado la publicacion
     * @return id de la publicacion generado automaticamente por la base de datos,
     * -1 en caso de que exista un error con la base de datos.
     */
    public int insertarPublicacion(long fecha, String contenido, int publicante_uuid){
        PoolManager pool = PoolManager.getInstance();
        Connection con = pool.getConnection();
        try {
            String query="insert into publicacion (fecha, contenido, publicante_uuid) values (?,?,?)";
            PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, fecha);
            ps.setString(2, contenido);
            ps.setInt(3, publicante_uuid);
            int insertedRows = ps.executeUpdate();
            if(insertedRows == 1){
                ResultSet keys = ps.getGeneratedKeys();
                keys.next();
                int idpub = keys.getInt(1);
                con.commit();
                pool.returnConnection(con);
                return idpub;
            }else{
                con.rollback();
                pool.returnConnection(con);
                return -1;
            }

        } catch (SQLException e) {
            try {con.rollback();}
            catch(SQLException e2){
                e2.printStackTrace();
                pool.returnConnection(con);
                return -1;
            }
            e.printStackTrace();
            pool.returnConnection(con);
            return -1;
        }
    }

    /**
     * Extrae una publicacion a partir de un id concreto
     * @param id id de la publicacion
     * @return informacion de la publicacion concreta
     */
    public PostDTO getPostById(int id) {
        PoolManager pool = PoolManager.getInstance();
        Connection con = pool.getConnection();
        try {
            boolean isGroup = false;
            String query = "select pu.idpublicacion, pe.avatar, pu.fecha, pe.nombre, pe.publicante_uuid, pu.contenido " +
                    "from persona pe, publicacion pu " +
                    "where pu.idpublicacion=? and pe.publicante_uuid=pu.Publicante_UUID";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            if(!rs.next()){
                /* Probamos a ver si es la publicacion de un grupo */
                isGroup = true;
                query = "select pu.idpublicacion, g.avatar, pu.fecha, g.nombre, g.publicante_uuid, pu.contenido " +
                        "from grupo g, publicacion pu " +
                        "where pu.idpublicacion=? and g.publicante_uuid=pu.Publicante_UUID";
                ps = con.prepareStatement(query);
                ps.setInt(1,id);
                rs = ps.executeQuery();
                if(!rs.next()){
                    /* No existe publicacion con ese id */
                    con.rollback();
                    pool.returnConnection(con);
                    return null;
                }
            }
            /* Si llegamos aqui, es que la publicacion existe y tenemos el rs con el contenido */
            PostDTO res = new PostDTO(rs.getInt(1),rs.getString(2),rs.getLong(3),rs.getString(4),rs.getInt(5),rs.getString(6));
            res.setType(isGroup);
            con.commit();
            pool.returnConnection(con);
            return res;
        }catch (SQLException e){
            try {con.rollback();}
            catch(SQLException e2){
                e2.printStackTrace();
                pool.returnConnection(con);
                return null;
            }
            e.printStackTrace();
            pool.returnConnection(con);
            return null;
        }
    }

    /**
     * Extrae las 10 ultimas publicaciones de todos aquellos
     * seguidores que tenga un publicante concreto, incluidos los suyos
     * propios.
     * @param seguidor id del publicante del que se quieren ver las
     *                 publicaciones de sus seguidores
     * @return lista con las diez ultimas publicaciones, tanto de sus
     * seguidores como de el mismo
     */
    public List<PostDTO> ultimasPublicaciones(int seguidor){
        PoolManager pool = PoolManager.getInstance();
        Connection con = pool.getConnection();
        List<PostDTO> publicaciones = new ArrayList<PostDTO>();
        try {
            String query = "select * from publicacion where publicante_uuid in (" +
                    "select seguido from seguir where seguidor = ?" +
                    ") or publicante_uuid = ? order by fecha desc limit 10";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, seguidor);
            ps.setInt(2, seguidor);
            ResultSet rs = ps.executeQuery();
            con.commit();
            int id; 
            PublicanteDAO pdao = new PublicanteDAO();
            GrupoDAO gdao = new GrupoDAO();
            while(rs.next()){
                id = rs.getInt("publicante_uuid");
                PostDTO post = new PostDTO(rs.getInt("idPublicacion"),
                        pdao.getAvatarById(id),rs.getLong("fecha"),pdao.getNombreById(id),id,
                        rs.getString("contenido"));
                post.setType(gdao.esUnGrupo(id));
                publicaciones.add(post);
            }
            pool.returnConnection(con);
            return publicaciones;
        }catch (SQLException e){
            try {con.rollback();}
            catch(SQLException e2){
                e2.printStackTrace();
                pool.returnConnection(con);
                return null;
            }
            e.printStackTrace();
            pool.returnConnection(con);
            return null;
        }
    }

    /**
     * Actualiza los datos de una publicacion conreta
     * @param id id de la publicacion
     * @param fecha fecha de la publicacion
     * @param contenido texto de la publicacion
     * @return cierto en caso de que la actualizacion se realice
     * con exito, falso en caso contrario
     */
    public boolean editarPublicacion(int id, long fecha, String contenido){
        PoolManager pool = PoolManager.getInstance();
        Connection con = pool.getConnection();
        try {
            String query1 = "update publicacion set fecha=?, contenido=? where idPublicacion=?";
            PreparedStatement ps1 = con.prepareStatement(query1);
            ps1.setLong(1, fecha);
            ps1.setString(2, contenido);
            ps1.setInt(3, id);
            int editadas_entidad = ps1.executeUpdate();

            if (editadas_entidad == 1) {
                con.commit();
                pool.returnConnection(con);
                return true;
            }
            else{
                con.rollback();
                pool.returnConnection(con);
                return false;
            }
        }catch(Exception ex){
            ex.printStackTrace();
            pool.returnConnection(con);
            return false;
        }
    }

    /**
     * Elimina una publicacion concreta de la base de datos
     * @param id id de la publicacion
     * @return cierto en caso de que la eliminacion se realice con exito, falso en caso contrario
     */
    public boolean borrarPublicacion(int id){
        PoolManager pool = PoolManager.getInstance();
        Connection con = pool.getConnection();
        try {
            String query1 = "delete from publicacion where idpublicacion=?";
            PreparedStatement ps1 = con.prepareStatement(query1);
            ps1.setInt(1, id);
            int eliminadas_entidad = ps1.executeUpdate();

            if (eliminadas_entidad == 1) {
                con.commit();
                pool.returnConnection(con);
                return true;
            }else{
                con.rollback();
                pool.returnConnection(con);
                return false;
            }
        }catch(Exception ex){
            ex.printStackTrace();
            try{
                con.rollback();
            }catch(SQLException e2){
                e2.printStackTrace();
            }
            pool.returnConnection(con);
            return false;
        }
    }

    /**
     * Borra todas aquellas publicaciones que haya escrito
     * un publicante concreto
     * @param uuid id del publicante
     * @return cierto en caso de que la eliminacion se realice
     * con exito, falso en caso contrario
     */
    public boolean borrarPublicaciones(int uuid){
        PoolManager pool = PoolManager.getInstance();
        Connection con = pool.getConnection();
        try {
            String query1 = "delete from publicacion where Publicante_UUID=?";
            PreparedStatement ps1 = con.prepareStatement(query1);
            ps1.setInt(1, uuid);
            ps1.executeUpdate();
            /*
             * No tenemos en cuenta las filas borradas porque puede que no tuviera
             * ninguna publicacion
             */
            con.commit();
            pool.returnConnection(con);
            return true;
        }catch(Exception ex){
            ex.printStackTrace();
            try{
                con.rollback();
            }catch(SQLException e2){
                e2.printStackTrace();
            }
            pool.returnConnection(con);
            return false;
        }
    }
}
