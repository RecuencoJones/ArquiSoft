package myusick.persistence.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import myusick.model.dto.TagDTO;
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

    public ArrayList<String> getTagsByPersona(int uuid){
        ArrayList<String> result = new ArrayList<>();
        try{
            String queryString = "select nombreTag from tag where idTag in (select idTag " +
                    "from persona_tiene_tag where UUID_P=?)";
            PreparedStatement preparedStatement = con.prepareStatement(queryString);
            preparedStatement.setInt(1, uuid);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                result.add(resultSet.getString(1));
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace(System.err);
            return null;
        }
    }

    public boolean registrarTag(TagDTO td) {
        try {
            String query = "insert into tag (nombreTag) values (?)";
            PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, td.getNombre());
            int insertedRows = ps.executeUpdate();
            if(insertedRows == 1){
                ResultSet keys = ps.getGeneratedKeys();
                keys.next();
                int uuid = keys.getInt(1);
                /* anadimos ahora a la tabla de asociacion con el publicante */
                GrupoDAO gdao = new GrupoDAO(); gdao.setConnection(ConnectionAdmin.getConnection());
                PersonaDAO pdao = new PersonaDAO(); pdao.setConnection(ConnectionAdmin.getConnection());
                if(gdao.esUnGrupo(td.getPublicante())){
                    query = "insert into grupo_tiene_tag (uuid_g,idTag) values (?,?)";
                }else if(pdao.esUnaPersona(td.getPublicante())){
                    query = "insert into persona_tiene_tag (uuid_p,idTag) values (?,?)";
                }else{
                    /* error, el publicante no existe*/
                    return false;
                }
                ps = con.prepareStatement(query);
                ps.setInt(1, td.getPublicante());
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
