package myusick.api;

import myusick.api.endpoints.*;
import myusick.model.RegisterUser;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

/**
 * REST backend
 * Backend del servidor
 * Todos los endpoints de la API se encontrarán aquí
 */
@Path("/")
public class RestServiceEndpoints {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String listEndpoints(){
        return DirectoryEndpoint.listEndpoints();
    }

    @POST
    @Path("/auth")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public String authenticate(@FormParam("user") String user, @FormParam("password") String password){
        return LoginEndpoint.authenticate(user, password);
    }

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String register(@Context UriInfo info, RegisterUser registerUser){
        return RegisterEndpoint.register(info, registerUser);
    }
}