package myusick.api;

import myusick.api.endpoints.*;
import myusick.model.DTO.RegisterUserDTO;

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
        return APIdirectory.listEndpoints();
    }

    @POST
    @Path("/auth")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public String authenticate(@FormParam("user") String user, @FormParam("password") String password){
        return LoginDAO.authenticate(user, password);
    }

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String register(@Context UriInfo info, RegisterUserDTO registerUserDTO){
        return RegisterDAO.register(info, registerUserDTO);
    }
    
    @GET
    @Path("/profile/{userid}")
    @Produces(MediaType.APPLICATION_JSON)
    public String profile(@PathParam("userid") int userid){
        return ProfileDAO.profile(userid);
    }
    
}