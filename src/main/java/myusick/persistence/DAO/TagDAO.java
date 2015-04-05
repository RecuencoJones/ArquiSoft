package myusick.persistence.DAO;

import myusick.model.dto.GroupDTO;
import myusick.persistence.VO.Persona;
import myusick.persistence.connection.ConnectionAdmin;

import java.sql.*;

/**
 * Created by Cuenta de clase on 02/04/2015.
 */
public class TagDAO {

    private Connection con;

    public void setConnection(Connection con) {
        this.con = con;
    }

    public String[] getTagsByPersona(int uuid) {
        try {
            String queryString = "select nombreTag from tag where idTag in (select idTag " +
                    "from persona_tiene_tag where UUID_P=?)";
            PreparedStatement preparedStatement = con.prepareStatement(queryString);
            preparedStatement.setInt(1, uuid);
            ResultSet resultSet = preparedStatement.executeQuery();
            int i = 0;
            String[] result = new String[20];
            while (resultSet.next()) {
                result[i] = resultSet.getString(i);
                i++;
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace(System.err);
            return null;
        }
    }

    public boolean registrarTag(String nombre, int publicante) {
        try {
            String query = "insert into tag (nombreTag) values (?)";
            PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, nombre);
            int insertedRows = ps.executeUpdate();
            if(insertedRows == 1){
                ResultSet keys = ps.getGeneratedKeys();
                keys.next();
                int uuid = keys.getInt(1);
                /* anadimos ahora a la tabla de asociacion con el publicante */
                GrupoDAO gdao = new GrupoDAO(); gdao.setConnection(ConnectionAdmin.getConnection());
                PersonaDAO pdao = new PersonaDAO(); pdao.setConnection(ConnectionAdmin.getConnection());
                if(gdao.esUnGrupo(publicante)){
                    query = "insert into grupo_tiene_tag (uuid_g,idTag) values (?,?)";
                }else if(pdao.esUnaPersona(publicante)){
                    query = "insert into persona_tiene_tag (uuid_p,idTag) values (?,?)";
                }else{
                    /* error, el publicante no existe*/
                    return false;
                }
                ps = con.prepareStatement(query);
                ps.setInt(1, publicante);
                ps.setInt(2,uuid);
                insertedRows = ps.executeUpdate();
                if(insertedRows == 1){
                    return true;
                }else return false;
            }else return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
