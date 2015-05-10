package myusick.model.dao;

import myusick.controller.dto.TagDTO;
import myusick.model.connection.ConnectionAdmin;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Cuenta de clase on 02/04/2015.
 */
public class AptitudDAO {

    private Connection con;

    public void setConnection(Connection con){
        try{
            this.con = con;
            this.con.setAutoCommit(false);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public ArrayList<String> getAptitudesByPersona(int uuid){
        ArrayList<String> result = new ArrayList<>();
        try {
            String queryString = "select nombre from aptitud where idAptitud in (select idAptitud " +
                    "from tiene_aptitud where uuid_p=?)";
            PreparedStatement preparedStatement = con.prepareStatement(queryString);
            preparedStatement.setInt(1, uuid);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                result.add(resultSet.getString(1));
            }
            con.commit();
            return result;
        }catch(Exception e){
            try{
                con.rollback();
            }catch(SQLException e2){e2.printStackTrace();}
            e.printStackTrace();
            return null;
        }
    }

    public boolean registrarAptitud(TagDTO td) {
        try {
            int existeAptitud = getIdByNombre(td.getNombre());
            if (existeAptitud == -1) {
                /*El tag no existe */
                String query = "insert into aptitud (nombre) values (?)";
                PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, td.getNombre());
                int insertedRows = ps.executeUpdate();
                if (insertedRows == 1) {
                    ResultSet keys = ps.getGeneratedKeys();
                    keys.next();
                    int uuid = keys.getInt(1);
                    /* anadimos ahora a la tabla de asociacion con el publicante */
                    con.commit();
                    return asociarAptitud(uuid, td.getPublicante());

                } else{
                    con.rollback();
                    return false;
                }
            } else if (existeAptitud == -2) {
                /*Error de BD*/
                con.rollback();
                return false;
            } else {
                /*el tag existe*/
                con.rollback();
                return asociarAptitud(existeAptitud, td.getPublicante());
            }
        } catch (SQLException e) {
            try{
                con.rollback();
            }catch(SQLException e2){e2.printStackTrace();}
            e.printStackTrace();
            return false;
        }
    }

    public int getIdByNombre(String nombre){
        try{
            String query = "select idAptitud from aptitud where nombre = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, nombre);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                con.commit();
                return rs.getInt(1);
            }
            else{
                con.rollback();
                return -1;
            }
        }catch(SQLException e){
            try{
                con.rollback();
            }catch(SQLException e2){e2.printStackTrace();}
            e.printStackTrace();
            return -2;
        }
    }

    private boolean asociarAptitud(int idAptitud, int idPublicante) {
        try {
            PersonaDAO pdao = new PersonaDAO();
            pdao.setConnection(ConnectionAdmin.getConnection());
            String query = "insert into tiene_aptitud (uuid_p,idAptitud) values (?,?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, idPublicante);
            ps.setInt(2, idAptitud);
            int insertedRows = ps.executeUpdate();
            if (insertedRows == 1) {
                con.commit();
                pdao.closeConnection();
                return true;
            } else {
                con.rollback();
                pdao.closeConnection();
                return false;
            }
        } catch (SQLException e) {
            try{
                con.rollback();
            }catch(SQLException e2){e2.printStackTrace();}
            e.printStackTrace();
            return false;
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
