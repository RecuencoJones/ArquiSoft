package myusick.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import myusick.controller.dto.SkillTagDTO;
import myusick.model.connection.PoolManager;
import java.sql.*;

/**
 * Myusick. Arquitectura Software 2015
 * @author David Recuenco (648701)
 * @author Guillermo Perez (610382)
 * @author Sandra Campos (629928)
 *
 * Clase DAO que proporciona el acceso a los datos relacionados
 * con las tags del sistema
 */
public class TagDAO {

    /**
     * Metodo encargado de devolver los tags que se tengan asociados a una
     * persona concreta
     * @param uuid id de la persona
     * @return tags asociadas a la persona
     */
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

    /**
     * Metodo encargado de devolver los tags que se tengan asociados a un
     * grupo concreto
     * @param uuid id del grupo
     * @return tags asociadas al grupo
     */
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

    /**
     * Registra un nuevo tag en la base de datos
     * @param td informacion del tag a insertar
     * @return cierto en caso de que la insercion sea correcta, falso
     * en caso contrario
     */
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

    /**
     * Devuelve el id de la tag a partir de su nombre
     * @param nombre nombre de la tag
     * @return id de la tag, -1 en caso de que la tag no exista
     * y -2 en caso de un problema de conexion con la BD
     */
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

    /**
     * Establece una relacion entre un publicante y una tag. De esta manera, ese publicante
     * tendra asociada dicha tag en caso de consulta.
     * @param idTag id del tag que se desea asociar
     * @param idPublicante id delp publicante que se desea asociar
     * @return cierto en caso de que la operacion en la BD haya sido correcta, falso en
     * caso contrario.
     */
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

    /**
     * Modifica el nombre de una tag concreta
     * @param id id de la tag a modificar
     * @param nuevo nuevo nombre que va a tener
     * @return cierto en caso de que la actualizacion haya
     * sido correcta, falso en caso contrario.
     */
    public boolean editarTag(int id, String nuevo){
        PoolManager pool = PoolManager.getInstance();
        Connection con = pool.getConnection();
        try {
            if(nuevo.length()>45 || nuevo.length()==0) return false;
            String query = "update tag set nombreTag=? where idTag=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, nuevo);
            ps.setInt(2, id);
            int alteredRows = ps.executeUpdate();
            if (alteredRows == 1) {
                con.commit();
                pool.returnConnection(con);
                return true;
            }else{
                con.rollback();
                pool.returnConnection(con);
                return false;
            }
        }catch(Exception ex){
            pool.returnConnection(con);
            return false;
        }
    }

    /**
     * Borra un tag concreto de la base de datos
     * @param id id del tag que se desea borrar
     * @return cierto en caso de que el borrado sea correcto, falso
     * en caso contrario.
     */
    public boolean borrarTag(int id){
        PoolManager pool = PoolManager.getInstance();
        Connection con = pool.getConnection();
        try {
            String query1 = "delete from grupo_tiene_tag where idtag=?";
            PreparedStatement ps1 = con.prepareStatement(query1);
            ps1.setInt(1, id);
            int eliminadas_relacion1 = ps1.executeUpdate();

            String query2 = "delete from persona_tiene_tag where idtag=?";
            PreparedStatement ps2 = con.prepareStatement(query2);
            ps2.setInt(1, id);
            int eliminadas_relacion2 = ps2.executeUpdate();

            String query3 = "delete from tag where idtag=?";
            PreparedStatement ps3 = con.prepareStatement(query3);
            ps3.setInt(1, id);
            int eliminadas_entidad = ps3.executeUpdate();

            if (eliminadas_entidad == 1) {
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
     * Borra todos los tags que tenga asociados un publicante
     * @param uuid id del publicante
     * @param tipo indica si el publicante es un grupo o un publicante, de esta manera
     *             se unifican dos metodos en uno
     * @return cierto en caso de que el borrado haya sido correcto, falso en caso
     * contrario
     */
    public boolean borrarTagsAsociadas(int uuid, String tipo){
        PoolManager pool = PoolManager.getInstance();
        Connection con = pool.getConnection();
        try{
            String query = "delete from "+tipo+"_tiene_tag where UUID_"+tipo.charAt(0)+"=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1,uuid);
            ps.executeUpdate();
            con.commit();
            pool.returnConnection(con);
            return true;
        }catch(SQLException e){
            e.printStackTrace();
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
