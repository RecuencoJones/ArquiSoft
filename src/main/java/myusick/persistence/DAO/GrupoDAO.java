package myusick.persistence.DAO;

import myusick.model.dto.GroupDTO;
import myusick.model.dto.ProfileDTO;
import myusick.model.dto.PublisherDTO;
import myusick.model.dto.RegisterDTO;
import myusick.persistence.VO.Grupo;
import myusick.persistence.VO.Persona;
import myusick.persistence.connection.ConnectionAdmin;
import myusick.persistence.connection.PoolManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cuenta de clase on 02/04/2015.
 */
public class GrupoDAO {

    public ArrayList<PublisherDTO> getMembersGroup(int uuid){
        ArrayList<PublisherDTO> result = new ArrayList<>();
        PoolManager pm = PoolManager.getInstance();
        Connection con = pm.getConnection();
        try {
            String queryString = "select Publicante_UUID,nombre from Persona where publicante_uuid " +
                    "in (select uuid_p from es_integrante where uuid_g=?)";
            PreparedStatement preparedStatement = con.prepareStatement(queryString);
            preparedStatement.setInt(1, uuid);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result.add(new PublisherDTO(resultSet.getInt(1), resultSet.getString(2)));
            }
            con.commit();
            pm.returnConnection(con);
            return result;
        } catch (SQLException e) {
            try{
                con.rollback();
                pm.returnConnection(con);
            }catch(SQLException e2){e2.printStackTrace();}
            e.printStackTrace();
            pm.returnConnection(con);
            return null;
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            pm.returnConnection(con);
            return result;
        }
    }

    public boolean esUnGrupo(int uuid) {
        PoolManager pm = PoolManager.getInstance();
        Connection con = pm.getConnection();
        try {
            String queryString = "select tipoPublicante from publicante where uuid=?";
            PreparedStatement preparedStatement = con.prepareStatement(queryString);
            preparedStatement.setInt(1, uuid);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                con.commit();
                pm.returnConnection(con);
                return resultSet.getBoolean(1) == true;
            }
            else{
                con.rollback();
                pm.returnConnection(con);
                return false;
            }
        } catch (SQLException e) {
            try{
                con.rollback();
            }catch(SQLException e2){e2.printStackTrace();}
            e.printStackTrace();
            pm.returnConnection(con);
            return false;
        }
    }

    public Grupo getDataProfile(int uuid) {
        PoolManager pm = PoolManager.getInstance();
        Connection con = pm.getConnection();
        try {
            String queryString = "select nombre,descripcion,anyoFundacion,avatar from grupo where publicante_uuid = ?";
            PreparedStatement preparedStatement = con.prepareStatement(queryString);
            preparedStatement.setInt(1, uuid);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                con.commit();
                pm.returnConnection(con);
                return new Grupo(uuid, resultSet.getString("nombre"), resultSet.getLong("anyoFundacion"),
                        resultSet.getString("descripcion"), resultSet.getString("avatar"));
            } else{
                con.rollback();
                pm.returnConnection(con);
                return null;
            }
        } catch (Exception e) {
            try{
                con.rollback();
            }catch(SQLException e2){e2.printStackTrace();}
            pm.returnConnection(con);
            e.printStackTrace();
            return null;
        }
    }

    public int registerGroup(GroupDTO gd) {
        PoolManager pm = PoolManager.getInstance();
        Connection con = pm.getConnection();
        try {
            PublicanteDAO pdao = new PublicanteDAO();
            int uuid = pdao.insertarPublicante(true);
            if (uuid != -1) {
                String query = "insert into grupo (Publicante_UUID,nombre,anyofundacion,descripcion) values (?,?,?,?)";
                PreparedStatement ps = con.prepareStatement(query);
                ps.setInt(1, uuid);
                ps.setString(2, gd.getName());
                ps.setInt(3, Integer.parseInt(gd.getYear()));
                ps.setString(4, gd.getDescription());
                int insertedRows = ps.executeUpdate();
                if (insertedRows == 1) {
                /* insertamos al usuario en la tabla de union con el grupo */
                    System.out.println("grupo "+uuid+" persona "+gd.getCreator());
                    query = "insert into es_integrante (UUID_G,UUID_P) values (?,?)";
                    ps = con.prepareStatement(query);
                    ps.setInt(1, uuid);
                    ps.setInt(2,gd.getCreator());
                    insertedRows = ps.executeUpdate();
                    con.commit();
                    if(insertedRows == 1){
                        pm.returnConnection(con);
                        return uuid;
                    }else{
                        pm.returnConnection(con);
                        return uuid;
                    }
                } else {
                    con.rollback();
                    pm.returnConnection(con);
                    return -1;
                }
            } else {
                con.rollback();
                pm.returnConnection(con);
                return -1;
            }
        } catch (SQLException e) {
            try{
                con.rollback();
            }catch(SQLException e2){e2.printStackTrace();}
            e.printStackTrace();
            pm.returnConnection(con);
            return -1;
        }
    }

    public boolean solicitudInsercionGrupo(int persona, int grupo){
        PoolManager pm = PoolManager.getInstance();
        Connection con = pm.getConnection();
        try{
            String query = "insert into Pendiente_aceptacion (Grupo,Persona) values (?,?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, grupo);
            ps.setInt(2, persona);
            int insertedRows = ps.executeUpdate();
            if (insertedRows == 1) {
                con.commit();
                pm.returnConnection(con);
                return true;
            }
            else{
                con.rollback();
                pm.returnConnection(con);
                return false;
            }
        }catch (SQLException e) {
            try{
                con.rollback();
            }catch(SQLException e2){e2.printStackTrace();}
            pm.returnConnection(con);
            e.printStackTrace();
            return false;
        }
    }

    public boolean rechazarDeGrupo (int persona, int grupo){
        PoolManager pm = PoolManager.getInstance();
        Connection con = pm.getConnection();
        try{
            String query = "delete from Pendiente_aceptacion where persona = ? and grupo = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, persona);
            ps.setInt(2, grupo);
            int insertedRows = ps.executeUpdate();
            if (insertedRows == 1) {
                con.commit();
                pm.returnConnection(con);
                return true;
            }else{
                con.rollback();
                pm.returnConnection(con);
                return false;
            }
        }catch (SQLException e) {
            try{
                con.rollback();
            }catch(SQLException e2){e2.printStackTrace();}
            pm.returnConnection(con);
            e.printStackTrace();
            return false;
        }
    }

    public boolean anadirAGrupo(int persona, int grupo){
        PoolManager pm = PoolManager.getInstance();
        Connection con = pm.getConnection();
        /* Primero lo eliminamos de la tabla de pendientes por aceptar */
        try{
            String query = "delete from Pendiente_aceptacion where persona = ? and grupo = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, persona);
            ps.setInt(2, grupo);
            int insertedRows = ps.executeUpdate();
            if (insertedRows != 0) {
                System.out.println("Ha llegado a borrar peticion");
                /* Ahora, lo anadimos al grupo al que solicito entrar */
                query = "insert into es_integrante(UUID_G,UUID_P) values (?,?)";
                ps = con.prepareStatement(query);
                ps.setInt(1, grupo);
                ps.setInt(2, persona);
                insertedRows = ps.executeUpdate();
                if (insertedRows == 1) {
                    con.commit();
                    pm.returnConnection(con);
                    return true;
                }else{
                    con.rollback();
                    System.out.println("No ha insertado nada en es_integrante");
                    pm.returnConnection(con);
                    return false;
                }
            }
            else{
                /* Esa persona no tenia una solicitud pendiente, error */
                System.out.println("No hay solicitud pendiente");
                con.rollback();
                pm.returnConnection(con);
                return false;
            }
        }catch (SQLException e) {
            try{
                con.rollback();
            }catch(SQLException e2){e2.printStackTrace();}
            e.printStackTrace();
            pm.returnConnection(con);
            return false;
        }

    }

    public boolean eliminarDeGrupo(int persona, int grupo){
        PoolManager pm = PoolManager.getInstance();
        Connection con = pm.getConnection();
        try{
            String query = "delete from es_integrante where uuid_p = ? and uuid_g = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, persona);
            ps.setInt(2, grupo);
            int insertedRows = ps.executeUpdate();
            System.out.println(insertedRows);
            if (insertedRows != 0) {
                con.commit();
                pm.returnConnection(con);
                return true;
            }
            else{
                con.rollback();
                pm.returnConnection(con);
                return false;
            }
        }catch (SQLException e) {
            try{
                con.rollback();
            }catch(SQLException e2){e2.printStackTrace();}
            pm.returnConnection(con);
            e.printStackTrace();
            return false;
        }
    }

    public List<PublisherDTO> pendientesDeAceptar(int grupo){
        PoolManager pm = PoolManager.getInstance();
        Connection con = pm.getConnection();
        List<PublisherDTO> pendientes = new ArrayList<PublisherDTO>();
        try{
            String query = "select pa.persona, p.nombre from " +
                    "pendiente_aceptacion pa, persona p where grupo=? and pa.persona=p.publicante_uuid";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, grupo);
            ResultSet rs = ps.executeQuery();
            con.commit();
            while (rs.next()) {
                pendientes.add(new PublisherDTO(rs.getInt(1),rs.getString(2)));
            }
            pm.returnConnection(con);
            return pendientes;
        }catch (SQLException e) {
            try{
                con.rollback();
            }catch(SQLException e2){e2.printStackTrace();}
            pm.returnConnection(con);
            e.printStackTrace();
            return null;
        }
    }

    public List<ProfileDTO> buscarPorNombre(String nombre){
        PoolManager pm = PoolManager.getInstance();
        Connection con = pm.getConnection();
        List<ProfileDTO> resultado = new ArrayList<ProfileDTO>();
        try{
            String query = "select Publicante_uuid, descripcion, avatar from persona where nombre = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, nombre);
            ResultSet rs = ps.executeQuery();
            AptitudDAO adao = new AptitudDAO();
            TagDAO tdao = new TagDAO();
            PublicacionDAO pdao = new PublicacionDAO();
            while(rs.next()){
                int uuid = rs.getInt(1);
                ProfileDTO perfil = new ProfileDTO(nombre,rs.getString("descripcion"),
                        rs.getString("avatar"),adao.getAptitudesByPersona(uuid),tdao.getTagsByPersona(uuid),
                        this.getMembersGroup(uuid),null,pdao.getPublicacionesById(uuid));
                resultado.add(perfil);
            }
            pm.returnConnection(con);
            return resultado;
        }catch (Exception e) {
            try{
                con.rollback();
            }catch(SQLException e2){e2.printStackTrace();}
            e.printStackTrace();
            pm.returnConnection(con);
            return null;
        }
    }

    public List<ProfileDTO> buscarPorTag(String tag){
        PoolManager pm = PoolManager.getInstance();
        Connection con = pm.getConnection();
        List<ProfileDTO> resultado = new ArrayList<ProfileDTO>();
        try{
            String query = "select nombre,avatar, descripcion, Publicante_UUID from grupo where publicante_uuid in(" +
                    "  select UUID_G from grupo_tiene_tag where idTag in (" +
                    "    select idTag from tag where nombreTag=?" +
                    "))";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, tag);
            ResultSet rs = ps.executeQuery();
            AptitudDAO adao = new AptitudDAO();
            TagDAO tdao = new TagDAO();
            PublicacionDAO pdao = new PublicacionDAO();
            while(rs.next()){
                int uuid = rs.getInt("Publicante_UUID");
                ProfileDTO perfil = new ProfileDTO(rs.getString("nombre"),rs.getString("descripcion"),
                        rs.getString("avatar"),adao.getAptitudesByPersona(uuid),tdao.getTagsByPersona(uuid),
                        null,this.getMembersGroup(uuid),pdao.getPublicacionesById(uuid));
                resultado.add(perfil);
            }
            pm.returnConnection(con);
            return resultado;
        }catch (Exception e) {
            try{
                con.rollback();
            }catch(SQLException e2){e2.printStackTrace();}
            e.printStackTrace();
            pm.returnConnection(con);
            return null;
        }
    }
}
