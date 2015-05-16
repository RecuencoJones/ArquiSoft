package myusick.model.test;

import myusick.controller.MyusickService;

/**
 * Created by Cuenta de clase on 04/04/2015.
 */
public class ServiceTest {

    public static void main (String [] args){
        MyusickService m = new MyusickService();
        System.out.println(m.getFollowers(3));
    }
}
