package myusick.model.test;

import myusick.controller.MyusickService;

/**
 * Created by Cuenta de clase on 04/04/2015.
 */
public class ServiceTest {

    public static void main (String [] args){
        MyusickService m = new MyusickService();
        System.out.println(m.getFollowing(4));
//        System.out.println(m.pendientesDeAceptar(6));


        //m.registrarTag("soyUnaMierda",1);
////        LoginDTO l = m.getLoginData("foo@bar.com","1234");
////        System.out.printf(l.toString());
//        ProfileDTO p = m.getProfileData(1);
//        System.out.println(p.toString());
//        try {
//            Connection c = ConnectionAdmin.getConnection();
//            String query="select publicante_uuid from persona where email=? and password=?";
//            PreparedStatement ps = c.prepareStatement(query);
//            ps.setString(1,"foo@bar.com");
//            ps.setString(2,"1234");
//            ResultSet rs = ps.executeQuery();
//            int i=1;
//            if(rs.next()){
//                System.out.println(rs.getInt(1));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

    }
}
