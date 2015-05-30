package myusick.model.dao;

import myusick.controller.dto.GroupDTO;
import myusick.controller.dto.ProfileDTO;
import myusick.controller.dto.PublisherDTO;
import myusick.controller.dto.ShortProfileDTO;
import myusick.model.connection.PoolManager;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Myusick. Arquitectura Software 2015
 *
 * @author David Recuenco (648701)
 * @author Guillermo Perez (610382)
 * @author Sandra Campos (629928)
 *         Clase DAO que proporciona el acceso a los datos relacionados
 *         con las personas del sistema
 */
public class GrupoDAO {

    /**
     * Extrae la informacion de perfil de todos los miembros
     * que conforman un grupo concreto
     *
     * @param uuid id del grupo
     * @return lista con la informacion de los perfiles de los
     * integrantes del grupo
     */
    public ArrayList<PublisherDTO> getMembersGroup(int uuid) {
        PoolManager pool = PoolManager.getInstance();
        Connection con = pool.getConnection();
        ArrayList<PublisherDTO> result = new ArrayList<>();
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
            pool.returnConnection(con);
            return result;
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
            e.printStackTrace();
            pool.returnConnection(con);
            return null;
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            pool.returnConnection(con);
            return result;
        }
    }

    /**
     * Indica si un publicante concreto es un grupo
     *
     * @param uuid id del publicante
     * @return cierto en caso de que sea un grupo,
     * falso en caso contrario
     */
    public boolean esUnGrupo(int uuid) {
        PoolManager pool = PoolManager.getInstance();
        Connection con = pool.getConnection();
        try {
            String queryString = "select tipoPublicante from publicante where uuid=?";
            PreparedStatement preparedStatement = con.prepareStatement(queryString);
            preparedStatement.setInt(1, uuid);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                con.commit();
                boolean resultado = resultSet.getBoolean(1) == true;
                pool.returnConnection(con);
                return resultado;
            } else {
                con.rollback();
                pool.returnConnection(con);
                return false;
            }
        } catch (SQLException e) {
            try {
                e.printStackTrace();
                con.rollback();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
            e.printStackTrace();
            pool.returnConnection(con);
            return false;
        }
    }

    /**
     * Extrae la informacion de perfil de un grupo concreto
     *
     * @param uuid id del grupo
     * @return informacion de perfil del grupo
     */
    public ProfileDTO getDataProfile(int uuid) {
        PoolManager pool = PoolManager.getInstance();
        Connection con = pool.getConnection();
        try {
            String queryString = "select nombre,descripcion,anyoFundacion,avatar from grupo where publicante_uuid = ?";
            PreparedStatement preparedStatement = con.prepareStatement(queryString);
            preparedStatement.setInt(1, uuid);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                con.commit();
                ProfileDTO g = new ProfileDTO(resultSet.getString("nombre"), resultSet.getString("descripcion"),
                        resultSet.getString("avatar"), null, new TagDAO().getTagsByGrupo(uuid),
                        getMembersGroup(uuid), null, new PublicacionDAO().getPublicacionesById(uuid));
                pool.returnConnection(con);
                return g;
            } else {
                con.rollback();
                pool.returnConnection(con);
                return null;
            }
        } catch (Exception e) {
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
     * Obtiene la informacion de un publicante a partir del
     * id del grupo
     *
     * @param uuid id del grupo
     * @return informacion de publicante del grupo
     */
    public PublisherDTO getDataPublisher(int uuid) {
        PoolManager pool = PoolManager.getInstance();
        Connection con = pool.getConnection();
        try {
            String queryString = "select nombre, avatar from grupo where publicante_uuid = ?";
            PreparedStatement preparedStatement = con.prepareStatement(queryString);
            preparedStatement.setInt(1, uuid);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                con.commit();
                PublisherDTO p = new PublisherDTO(uuid, resultSet.getString("nombre"),
                        resultSet.getString("avatar"));
                return p;
            } else {
                con.rollback();
                pool.returnConnection(con);
                return null;
            }
        } catch (Exception e) {
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
     * Inserta un grupo en la base de datos
     *
     * @param gd informacion del grupo que se quiere insertar
     * @return uuid del grupo insertado, -1 en caso contrario
     */
    public int registerGroup(GroupDTO gd) {
        PoolManager pool = PoolManager.getInstance();
        Connection con = pool.getConnection();
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
                    query = "insert into es_integrante (UUID_G,UUID_P) values (?,?)";
                    ps = con.prepareStatement(query);
                    ps.setInt(1, uuid);
                    ps.setInt(2, gd.getCreator());
                    insertedRows = ps.executeUpdate();
                    con.commit();
                    pool.returnConnection(con);
                    if (insertedRows == 1) {
                        return uuid;
                    } else return uuid;
                } else {
                    con.rollback();
                    pool.returnConnection(con);
                    return -1;
                }
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
     * Presenta una solicitud para ingresar en un grupo concreto
     *
     * @param persona id de la persona que solicita entrar
     * @param grupo   id del grupo al que se solicita entrar
     * @return cierto en caso de que la peticion se haya realizado
     * correctamente, falso en caso contrario
     */
    public boolean solicitudInsercionGrupo(int persona, int grupo) {
        PoolManager pool = PoolManager.getInstance();
        Connection con = pool.getConnection();
        try {
            String query = "insert into Pendiente_aceptacion (Grupo,Persona) values (?,?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, grupo);
            ps.setInt(2, persona);
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
            try {
                con.rollback();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
            e.printStackTrace();
            pool.returnConnection(con);
            return false;
        }
    }

    /**
     * Responder a la peticion de insercion desfavorablemente, la persona
     * no ingresara en el grupo
     *
     * @param persona id de la persona que solicitaba entrar
     * @param grupo   id del grupo al que se solicitaba entrar
     * @return cierto en caso de que se haya respondido correctamente, falso
     * en caso contrario
     */
    public boolean rechazarDeGrupo(int persona, int grupo) {
        PoolManager pool = PoolManager.getInstance();
        Connection con = pool.getConnection();
        try {
            String query = "delete from Pendiente_aceptacion where persona = ? and grupo = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, persona);
            ps.setInt(2, grupo);
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
            try {
                con.rollback();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
            e.printStackTrace();
            pool.returnConnection(con);
            return false;
        }
    }

    /**
     * Responder a la peticion de solicitud de insercion en un grupo favorablemente.
     * La persona pertenecera a partir de ahora al grupo que solicito.
     *
     * @param persona id de la persona que solicitaba entrar
     * @param grupo   id del grupo al que se le solicitaba entrar
     * @return cierto en caso de que se haya respondido correctamente, falso
     * en caso contrario.
     */
    public boolean anadirAGrupo(int persona, int grupo) {
        PoolManager pool = PoolManager.getInstance();
        Connection con = pool.getConnection();
        /* Primero lo eliminamos de la tabla de pendientes por aceptar */
        try {
            String query = "delete from Pendiente_aceptacion where persona = ? and grupo = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, persona);
            ps.setInt(2, grupo);
            int insertedRows = ps.executeUpdate();
            if (insertedRows != 0) {
                /* Ahora, lo anadimos al grupo al que solicito entrar */
                query = "insert into es_integrante(UUID_G,UUID_P) values (?,?)";
                ps = con.prepareStatement(query);
                ps.setInt(1, grupo);
                ps.setInt(2, persona);
                insertedRows = ps.executeUpdate();
                if (insertedRows == 1) {
                    con.commit();
                    pool.returnConnection(con);
                    return true;
                } else {
                    con.rollback();
                    pool.returnConnection(con);
                    return false;
                }
            } else {
                /* Esa persona no tenia una solicitud pendiente, error */
                con.rollback();
                pool.returnConnection(con);
                return false;
            }
        } catch (SQLException e) {
            try {
                e.printStackTrace();
                con.rollback();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
            e.printStackTrace();
            pool.returnConnection(con);
            return false;
        }

    }

    /**
     * Elimina a una persona concreta de un grupo al que pertenece
     *
     * @param persona id de la persona
     * @param grupo   id del grupo
     * @return cierto en caso de que la eliminacion sea correcta, falso
     * en caso contrario
     */
    public boolean eliminarDeGrupo(int persona, int grupo) {
        PoolManager pool = PoolManager.getInstance();
        Connection con = pool.getConnection();
        try {
            String query = "delete from es_integrante where uuid_p = ? and uuid_g = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, persona);
            ps.setInt(2, grupo);
            int insertedRows = ps.executeUpdate();
            if (insertedRows != 0) {
                con.commit();
                pool.returnConnection(con);
                return true;
            } else {
                con.rollback();
                pool.returnConnection(con);
                return false;
            }
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
            e.printStackTrace();
            pool.returnConnection(con);
            return false;
        }
    }

    /**
     * Extrae una lista de publicantes que han presentado una solicitud de insercion
     * a un grupo concreto y que aun no se han contestado.
     *
     * @param grupo id del grupo al que se solicita entrar
     * @return lista de publicantes pendientes de respuesta
     */
    public List<PublisherDTO> pendientesDeAceptar(int grupo) {
        PoolManager pool = PoolManager.getInstance();
        Connection con = pool.getConnection();
        List<PublisherDTO> pendientes = new ArrayList<PublisherDTO>();
        try {
            String query = "select pa.persona, p.nombre from " +
                    "pendiente_aceptacion pa, persona p where grupo=? and pa.persona=p.publicante_uuid";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, grupo);
            ResultSet rs = ps.executeQuery();
            con.commit();
            while (rs.next()) {
                pendientes.add(new PublisherDTO(rs.getInt(1), rs.getString(2)));
            }
            pool.returnConnection(con);
            return pendientes;
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
     * Busca aquellos perfiles que contengan, total o parcialmente, en su nombre
     * la cadena de caracteres insertada como parametro
     *
     * @param nombre cadena con la que se pretende encontrar coincidencias
     * @return lista con los perfiles en los que haya coincidencias
     */
    public List<ShortProfileDTO> buscarPorNombre(String nombre) {
        PoolManager pool = PoolManager.getInstance();
        Connection con = pool.getConnection();
        List<ShortProfileDTO> resultado = new ArrayList<>();
        try {
            String query = "select Publicante_uuid, nombre, avatar from grupo where nombre like ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, "%" + nombre + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int uuid = rs.getInt(1);
                ShortProfileDTO perfil = new ShortProfileDTO(uuid, rs.getString("nombre"), rs.getString("avatar"), true);
                resultado.add(perfil);
            }
            pool.returnConnection(con);
            return resultado;
        } catch (Exception e) {
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
     * Obtiene aquellos perfiles de grupos que tengan relacion con
     * la tag insertada como parametro
     *
     * @param tag nombre de la tag
     * @return lista de perfiles con coincidencias
     */
    public List<ShortProfileDTO> buscarPorTag(String tag) {
        PoolManager pool = PoolManager.getInstance();
        Connection con = pool.getConnection();
        List<ShortProfileDTO> resultado = new ArrayList<>();
        try {
            String query = "select nombre,avatar, Publicante_UUID from grupo where publicante_uuid in(" +
                    "  select UUID_G from grupo_tiene_tag where idTag in (" +
                    "    select idTag from tag where nombreTag=?" +
                    "))";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, tag);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int uuid = rs.getInt("Publicante_UUID");
                ShortProfileDTO perfil = new ShortProfileDTO(uuid, rs.getString("nombre"), rs.getString("avatar"), true);
                resultado.add(perfil);
            }
            pool.returnConnection(con);
            return resultado;
        } catch (Exception e) {
            try {
                con.rollback();
            } catch (SQLException e2) {
                pool.returnConnection(con);
                e2.printStackTrace();
            }
            e.printStackTrace();
            pool.returnConnection(con);
            return null;
        }
    }

    /**
     * Actualiza el nombre del grupo
     *
     * @param UUID   id del grupo
     * @param nombre nuevo nombre del grupo
     * @return cierto en caso de que la actualizacion sea correcta,
     * falso en caso contrario
     */
    public boolean setNombre(int UUID, String nombre) {
        PoolManager pool = PoolManager.getInstance();
        Connection con = pool.getConnection();
        try {
            if (nombre.length() > 45 || nombre.length() == 0) return false;
            if (UUID != -1) {
                String query = "update grupo set nombre=? where Publicante_UUID=?";
                PreparedStatement ps = con.prepareStatement(query);
                ps.setString(1, nombre);
                ps.setInt(2, UUID);
                int alteredRows = ps.executeUpdate();
                if (alteredRows == 1) {
                    con.commit();
                    pool.returnConnection(con);
                    return true;
                } else {
                    con.rollback();
                    pool.returnConnection(con);
                    return false;
                }
            } else {
                con.rollback();
                pool.returnConnection(con);
                return false;
            }
        } catch (Exception ex) {
            pool.returnConnection(con);
            return false;
        }
    }

    /**
     * Actualiza el anyo de fundacion del grupo
     *
     * @param UUID id del grupo
     * @param nac  nueva fecha de fundacion del grupo
     * @return cierto en caso de que la actualizacion sea correcta,
     * falso en caso contrario
     */
    public boolean setAnyo(int UUID, int nac) {
        PoolManager pool = PoolManager.getInstance();
        Connection con = pool.getConnection();
        try {
            PublicanteDAO pdao = new PublicanteDAO();
            if (UUID != -1) {
                String query = "update grupo set AnyoFundacion=? where Publicante_UUID=?";
                PreparedStatement ps = con.prepareStatement(query);
                ps.setInt(1, nac);
                ps.setInt(2, UUID);
                int alteredRows = ps.executeUpdate();
                if (alteredRows == 1) {
                    con.commit();
                    pool.returnConnection(con);
                    return true;
                } else {
                    con.rollback();
                    pool.returnConnection(con);
                    return false;
                }
            } else {
                con.rollback();
                pool.returnConnection(con);
                return false;
            }
        } catch (Exception ex) {
            pool.returnConnection(con);
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Actualiza la descripcion del grupo
     *
     * @param UUID  id del grupo
     * @param descr nueva descripcion del grupo
     * @return cierto en caso de que la actualizacion sea correcta,
     * falso en caso contrario
     */
    public boolean setDescripcion(int UUID, String descr) {
        PoolManager pool = PoolManager.getInstance();
        Connection con = pool.getConnection();
        try {
            PublicanteDAO pdao = new PublicanteDAO();
            if (descr.length() > 144 || descr.length() == 0) {
                pool.returnConnection(con);
                return false;
            }
            if (UUID != -1) {
                String query = "update grupo set descripcion=? where Publicante_UUID=?";
                PreparedStatement ps = con.prepareStatement(query);
                ps.setString(1, descr);
                ps.setInt(2, UUID);
                int alteredRows = ps.executeUpdate();
                if (alteredRows == 1) {
                    con.commit();
                    pool.returnConnection(con);
                    return true;
                } else {
                    con.rollback();
                    pool.returnConnection(con);
                    return false;
                }
            } else {
                con.rollback();
                pool.returnConnection(con);
                return false;
            }
        } catch (Exception ex) {
            pool.returnConnection(con);
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Actualiza la imagen de avatar del grupo
     *
     * @param UUID id del grupo
     * @param url  nueva url de la imagen de avatar del grupo
     * @return cierto en caso de que la actualizacion sea correcta,
     * falso en caso contrario
     */
    public boolean setAvatar(int UUID, String url) {
        PoolManager pool = PoolManager.getInstance();
        Connection con = pool.getConnection();
        try {
            PublicanteDAO pdao = new PublicanteDAO();
            if (url.length() > 500 || url.length() == 0) {
                pool.returnConnection(con);
                return false;
            }
            if (UUID != -1) {
                String query = "update grupo set avatar=? where Publicante_UUID=?";
                PreparedStatement ps = con.prepareStatement(query);
                ps.setString(1, url);
                ps.setInt(2, UUID);
                int alteredRows = ps.executeUpdate();
                if (alteredRows == 1) {
                    con.commit();
                    pool.returnConnection(con);
                    return true;
                } else {
                    con.rollback();
                    pool.returnConnection(con);
                    return false;
                }
            } else {
                con.rollback();
                pool.returnConnection(con);
                return false;
            }
        } catch (Exception ex) {
            pool.returnConnection(con);
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Borra un grupo de la base de datos
     *
     * @param uuid id del grupo
     * @return cierto en caso de que se haya borrado correctamente,
     * falso en caso contrario
     */
    public boolean borrarGrupo(int uuid) {
        PoolManager pool = PoolManager.getInstance();
        Connection con = pool.getConnection();
        try {
            String query1 = "delete from grupo_tiene_tag where UUID_G=?";
            PreparedStatement ps1 = con.prepareStatement(query1);
            ps1.setInt(1, uuid);
            int eliminadas_relacion1 = ps1.executeUpdate();

            String query2 = "delete from es_integrante where UUID_G=?";
            PreparedStatement ps2 = con.prepareStatement(query2);
            ps2.setInt(1, uuid);
            int eliminadas_relacion2 = ps2.executeUpdate();

            String query3 = "delete from pendiente_aceptacion where grupo=?";
            PreparedStatement ps3 = con.prepareStatement(query3);
            ps3.setInt(1, uuid);
            int eliminadas_relacion3 = ps3.executeUpdate();

            String query4 = "delete from grupo where Publicante_UUID=?";
            PreparedStatement ps4 = con.prepareStatement(query4);
            ps4.setInt(1, uuid);
            int eliminadas_relacion4 = ps4.executeUpdate();

            con.commit();

            PublicanteDAO pdao = new PublicanteDAO();
            boolean eliminadas_entidad = pdao.borrarPublicante(uuid);

            if (eliminadas_entidad) {
                con.commit();
                pool.returnConnection(con);
                return true;
            } else {
                con.rollback();
                pool.returnConnection(con);
                return false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            pool.returnConnection(con);
            return false;
        }
    }

}
