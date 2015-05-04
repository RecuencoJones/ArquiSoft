package myusick.persistence.DAO;

import myusick.model.dto.PostDTO;
import myusick.model.dto.PublicationsDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cuenta de clase on 02/04/2015.
 */
public class PublicacionDAO {

    private Connection con;

    public void setConnection(Connection con){
        try{
            this.con = con;
            this.con.setAutoCommit(false);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public ArrayList<PublicationsDTO> getPublicacionesById(int uuid){
        ArrayList<PublicationsDTO> result = new ArrayList<>();
        try{
            String queryString = "select idPublicacion,contenido,fecha from Publicacion where Publicante_UUID=?";
            PreparedStatement preparedStatement = con.prepareStatement(queryString);
            preparedStatement.setInt(1, uuid);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                result.add(new PublicationsDTO(uuid,resultSet.getString(2),resultSet.getLong(3)));
            }
            con.commit();
            return result;
        }catch (Exception e) {
            try {con.rollback();}
            catch(SQLException e2){
                e2.printStackTrace();
                return null;
            }
            e.printStackTrace();
            return null;
        }
    }

    public int insertarPublicacion(long fecha, String contenido, int publicante_uuid){
        try {
            String query="insert into publicacion (fecha, contenido, publicante_uuid) values (?,?,?)";
            PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1,fecha);
            ps.setString(2,contenido);
            ps.setInt(3,publicante_uuid);
            int insertedRows = ps.executeUpdate();
            if(insertedRows == 1){
                ResultSet keys = ps.getGeneratedKeys();
                keys.next();
                int idpub = keys.getInt(1);
                con.commit();
                return idpub;
            }else{
                con.rollback();
                return -1;
            }

        } catch (SQLException e) {
            try {con.rollback();}
            catch(SQLException e2){
                e2.printStackTrace();
                return -1;
            }
            e.printStackTrace();
            return -1;
        }
    }

    public PostDTO getPostById(int id) {
        try {
            String query = "select pu.idpublicacion, pe.avatar, pu.fecha, pe.nombre, pe.publicante_uuid, pu.contenido " +
                    "from persona pe, publicacion pu " +
                    "where pu.idpublicacion=? and pe.publicante_uuid=pu.Publicante_UUID";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            if(!rs.next()){
                /* Probamos a ver si es la publicacion de un grupo */
                query = "select pu.idpublicacion, g.avatar, pu.fecha, g.nombre, g.publicante_uuid, pu.contenido " +
                        "from grupo g, publicacion pu " +
                        "where pu.idpublicacion=? and g.publicante_uuid=pu.Publicante_UUID";
                ps = con.prepareStatement(query);
                ps.setInt(1,id);
                rs = ps.executeQuery();
                if(!rs.next()){
                    /* No existe publicacion con ese id */
                    con.rollback();
                    return null;
                }
            }
            /* Si llegamos aqui, es que la publicacion existe y tenemos el rs con el contenido */
            PostDTO res = new PostDTO(rs.getInt(1),rs.getString(2),rs.getLong(3),rs.getString(4),rs.getInt(5),rs.getString(6));
            con.commit();
            return res;
        }catch (SQLException e){
            try {con.rollback();}
            catch(SQLException e2){
                e2.printStackTrace();
                return null;
            }
            e.printStackTrace();
            return null;
        }
    }

    public List<PostDTO> ultimasPublicaciones(int seguidor){
        List<PostDTO> publicaciones = new ArrayList<PostDTO>();
        try {
            String query = "select * from publicacion where publicante_uuid in (" +
                    "select seguido from seguir where seguidor = ?" +
                    ") order by fecha asc limit 10";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, seguidor);
            ResultSet rs = ps.executeQuery();
            con.commit();
            int id; PublicanteDAO pdao = new PublicanteDAO();
            while(rs.next()){
                id = rs.getInt("publicante_uuid");
                publicaciones.add(new PostDTO(rs.getInt("idPublicacion"),
                        pdao.getAvatarById(id),rs.getLong("fecha"),pdao.getNombreById(id),id,
                        rs.getString("contenido")));
            }
            return publicaciones;
        }catch (SQLException e){
            try {con.rollback();}
            catch(SQLException e2){
                e2.printStackTrace();
                return null;
            }
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
