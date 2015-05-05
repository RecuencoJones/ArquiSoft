package myusick.persistence.DAO;

import myusick.model.dto.PublicationsDTO;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by david on 01/05/2015.
 */
public class SeguirDAO {

    private Connection con;

    public void setConnection(Connection con) {
        try{
            this.con = con;
            this.con.setAutoCommit(false);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    
    public boolean follow(int seguidor, int seguido){
        try {
            String query = "insert into Seguir (seguidor,seguido) values (?,?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, seguidor);
            ps.setInt(2, seguido);
            ps.executeUpdate();
            con.commit();
            return true;
        }catch (SQLException e){
            try{
                con.rollback();
            }catch(SQLException e2){e2.printStackTrace();}
            e.printStackTrace();
            return false;
        }
    }

    public boolean unfollow(int seguidor, int seguido){
        try {
            String query = "delete from Seguir where seguidor=? and seguido=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, seguidor);
            ps.setInt(2, seguido);
            ps.executeUpdate();
            con.commit();
            return true;
        }catch (SQLException e){
            try{
                con.rollback();
            }catch(SQLException e2){e2.printStackTrace();}
            e.printStackTrace();
            return false;
        }
    }

    public boolean isfollow(int seguidor, int seguido){
        try {
            String query = "select * from Seguir where seguidor=? and seguido=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, seguidor);
            ps.setInt(2, seguido);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                con.commit();
                return true;
            }
            else{
                try{
                    con.rollback();
                }catch(SQLException e2){e2.printStackTrace();}
                return false;
            }
        }catch (SQLException e){
            try{
                con.rollback();
            }catch(SQLException e2){e2.printStackTrace();}
            e.printStackTrace();
            return false;
        }
    }
    
    public ArrayList<Integer> getFollowers(int uuid) {
        ArrayList<Integer> result = new ArrayList<>();
        try{
            String query = "select seguidor from Seguir where seguido=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, uuid);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                result.add(rs.getInt(1));
            }
            con.commit();
            return result;
        }catch(SQLException e){
            try{
                con.rollback();
            }catch(SQLException e2){e2.printStackTrace();}
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
