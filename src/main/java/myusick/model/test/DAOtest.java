package myusick.model.test;

import myusick.model.connection.PoolManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Clase que hace las funciones de DAO para acceder a la base de datos
 * utilizando el pool
 *
 * @author Sandra Campos
 */
public class DAOtest {

    private void pruebaConcurrencia(){
        try {
            PoolManager pm = PoolManager.getInstance();
            Connection con = pm.getConnection();
            String queryString = "select nombre from persona where publicante_uuid=?";
            PreparedStatement preparedStatement = con.prepareStatement(queryString);
            preparedStatement.setInt(1, 1);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                System.out.println(resultSet.getString(1));
            }
            else{
                System.out.printf("nada");
            }
            pm = PoolManager.getInstance();
            con = pm.getConnection();
            queryString = "select nombre from persona where publicante_uuid=?";
            preparedStatement = con.prepareStatement(queryString);
            preparedStatement.setInt(1, 1);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                System.out.println(resultSet.getString(1));
            }
            else{
                System.out.printf("nada");
            }
            pm = PoolManager.getInstance();
            con = pm.getConnection();
            queryString = "select nombre from persona where publicante_uuid=?";
            preparedStatement = con.prepareStatement(queryString);
            preparedStatement.setInt(1, 1);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                System.out.println(resultSet.getString(1));
            }
            else{
                System.out.printf("nada");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("nada");
        }
    }
    private void pruebaBuena(){
        try {
            PoolManager pm = PoolManager.getInstance();
            Connection con = pm.getConnection();
            String queryString = "select nombre from persona where publicante_uuid=?";
            PreparedStatement preparedStatement = con.prepareStatement(queryString);
            preparedStatement.setInt(1, 1);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                System.out.println(resultSet.getString(1));
            }
            else{
                System.out.printf("nada");
            }
            pm.returnConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("nada");
        }
    }
    public static void main (String [] args){
        DAOtest d = new DAOtest();
//        d.pruebaBuena();
        d.pruebaConcurrencia();
    }
}
