package myusick.persistence.DAO;

import java.sql.Connection;

/**
 * Created by Cuenta de clase on 02/04/2015.
 */
public class PublicanteDAO {

    private Connection con;

    public void setConnection(Connection con){
        this.con = con;
    }
}
