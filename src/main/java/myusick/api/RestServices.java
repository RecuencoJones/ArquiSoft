package myusick.api;

import myusick.api.services.*;
import myusick.model.dto.GroupDTO;
import myusick.model.dto.RegisterDTO;

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
    public String register(@Context UriInfo info, RegisterDTO registerDTO){
        return RegisterService.register(info, registerDTO);
    }

    @GET
    @Path("/profile/{userid}")
    @Produces(MediaType.APPLICATION_JSON)
    public String userProfile(@PathParam("userid") int userid){
        return ProfileService.profile(userid);
    }
    
    @POST
    @Path("/newgroup")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String newGroup(@Context UriInfo info, GroupDTO groupDTO){
        System.out.println("AQUI LLEGA 1");
        return NewGroupService.newGroup(info, groupDTO);
    }
    
    /*@GET
    @Path("/home/{userid}")
    @Produces(MediaType.APPLICATION_JSON)
    public String home(@PathParam("userid") int userid){
        
    }
    
    @POST
    @Path("/post")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String newPost(@Context UriInfo, PostDTO postDTO){
        return PostService.newPost(info, postDTO);
    }
    
    @GET
    @Path("/post/{postid}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getPost(@PathParam("postid") int postid){
        return PostService.getPost(postid);
    }
    
    @POST
    @Path("/newTag")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String newTag(@Context UriInfo, TagDTO tagDTO){
        return TagService.newTag(info, tagDTO);
    }
        
    @POST
    @Path("/apply/{userid}/{groupid}")
    @Produces(MediaType.APPLICATION_JSON)
    public String applyForGroup(@PathParam("userid") int userid,
                                @PathParam("groupid") int groupid){
        return ApplyService(userid, groupid);
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