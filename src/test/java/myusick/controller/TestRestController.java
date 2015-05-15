package myusick.controller;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by david on 12/05/2015.
 */
public class TestRestController {

    RestServices restServices;

    @Before
    public void setup(){
        this.restServices = new RestServices();
        assertEquals("derp","derp");
    }

    @Test
    public void testPost(){

    }
}
