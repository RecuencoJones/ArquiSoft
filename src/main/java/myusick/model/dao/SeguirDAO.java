package myusick.model.dao;

import myusick.model.connection.PoolManager;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by david on 01/05/2015.
 */
public class SeguirDAO {
    public boolean follow(int seguidor, int seguido){
        PoolManager pool = PoolManager.getInstance();
        Connection con = pool.getConnection();
        try {
            String query = "insert into Seguir (seguidor,seguido) values (?,?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, seguidor);
            ps.setInt(2, seguido);
            ps.executeUpdate();
            con.commit();
            pool.returnConnection(con);
            return true;
        }catch (SQLException e){
            try{
                con.rollback();
            }catch(SQLException e2){e2.printStackTrace();}
            e.printStackTrace();
            pool.returnConnection(con);
            return false;
        }
    }

    public boolean unfollow(int seguidor, int seguido){
        PoolManager pool = PoolManager.getInstance();
        Connection con = pool.getConnection();
        try {
            String query = "delete from Seguir where seguidor=? and seguido=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, seguidor);
            ps.setInt(2, seguido);
            ps.executeUpdate();
            con.commit();
            pool.returnConnection(con);
            return true;
        }catch (SQLException e){
            try{
                con.rollback();
            }catch(SQLException e2){e2.printStackTrace();}
            e.printStackTrace();
            pool.returnConnection(con);
            return false;
        }
    }

    public boolean isfollow(int seguidor, int seguido){
        PoolManager pool = PoolManager.getInstance();
        Connection con = pool.getConnection();
        try {
            String query = "select * from Seguir where seguidor=? and seguido=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, seguidor);
            ps.setInt(2, seguido);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                con.commit();
                pool.returnConnection(con);
                return true;
            }
            else{
                try{
                    con.rollback();
                }catch(SQLException e2){e2.printStackTrace();}
                pool.returnConnection(con);
                return false;
            }
        }catch (SQLException e){
            try{
                con.rollback();
            }catch(SQLException e2){e2.printStackTrace();}
            e.printStackTrace();
            pool.returnConnection(con);
            return false;
        }
    }
    
    public ArrayList<Integer> getFollowers(int uuid) {
        PoolManager pool = PoolManager.getInstance();
        Connection con = pool.getConnection();
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
            pool.returnConnection(con);
            return result;
        }catch(SQLException e){
            try{
                con.rollback();
            }catch(SQLException e2){e2.printStackTrace();}
            e.printStackTrace();
            pool.returnConnection(con);
            return null;
        }
    }
}
