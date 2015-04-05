package myusick.api;

import com.google.gson.Gson;
import myusick.api.services.*;
import myusick.model.dto.NewGroupDTO;
import myusick.model.dto.RegisterUserDTO;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

//import org.glassfish.jersey.media.sse.EventOutput;
//import org.glassfish.jersey.media.sse.SseFeature;

/**
 * REST backend
 * Backend del servidor
 * Todos los endpoints de la API se encontrarán aquí
 */
@Path("/")
public class RestServices {

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
        return LoginService.authenticate(user, password);
    }

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String register(@Context UriInfo info, RegisterUserDTO registerUserDTO){
        return RegisterService.register(info, registerUserDTO);
    }
    
    @GET
    @Path("/profile/{userid}")
    @Produces(MediaType.APPLICATION_JSON)
    public String profile(@PathParam("userid") int userid){
        return ProfileService.profile(userid);
    }
    
    @POST
    @Path("/newGroup")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String newGroup(@Context UriInfo info, NewGroupDTO newGroupDTO){
        return NewGroupService.newGroup(info, newGroupDTO);
    }
    
    /*@GET
    @Path("/home/{userid}")
    @Produces(MediaType.APPLICATION_JSON)
    public String home(@PathParam("userid") int userid){
        
    }
    
    @GET
    @Path("events")
    @Produces(SseFeature.SERVER_SENT_EVENTS)
    public EventOutput getEvents(){
        EventOutput eo = new EventOutput();
        
        return eo;
    }
    */
    
}