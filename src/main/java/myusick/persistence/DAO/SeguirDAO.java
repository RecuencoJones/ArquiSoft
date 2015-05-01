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
        this.con = con;
    }
    
    public boolean follow(int seguidor, int seguido){
        try {
            String query = "insert into Seguir (seguidor,seguido) values (?,?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, seguidor);
            ps.setInt(2, seguido);
            ps.executeUpdate();
            return true;
        }catch (SQLException e){
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
            return true;
        }catch (SQLException e){
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
            if(rs.next()) return true;
            else return false;
        }catch (SQLException e){
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
            return result;
        }catch(SQLException e){
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
