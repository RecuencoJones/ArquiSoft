package myusick.api;

import myusick.api.services.*;
import myusick.model.dto.GroupDTO;
import myusick.model.dto.PublicationsDTO;
import myusick.model.dto.RegisterDTO;
import myusick.model.dto.TagDTO;

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

    @POST
    @Path("/newtag")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String newTag(@Context UriInfo info, TagDTO tagDTO){
        return TagService.newTag(info, tagDTO);
    }

    @GET
    @Path("/post/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getPost(@PathParam("id") int id){
        return PostService.getPost(id);
    }
    
    @POST
    @Path("/post")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String newPost(@Context UriInfo info, PublicationsDTO postDTO){
        return PostService.newPost(info, postDTO);
    }

    @GET
    @Path("/follow/{seguidor}/{seguido}")
    public String follow(@PathParam("seguidor") int seguidor, @PathParam("seguido") int seguido){
        return FollowService.follow(seguidor,seguido);
    }

    @GET
    @Path("/unfollow/{seguidor}/{seguido}")
    public String unfollow(@PathParam("seguidor") int seguidor, @PathParam("seguido") int seguido){
        return FollowService.unfollow(seguidor, seguido);
    }

    @GET
    @Path("/isfollowing/{seguidor}/{seguido}")
    public String isFollowing(@PathParam("seguidor") int seguidor, @PathParam("seguido") int seguido){
        return FollowService.isFollowing(seguidor, seguido);
    }
    
    /*@GET
    @Path("/home/{userid}")
    @Produces(MediaType.APPLICATION_JSON)
    public String home(@PathParam("userid") int userid){
        
    }
    
    @GET
    @Path("/post/{postid}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getPost(@PathParam("postid") int postid){
        return PostService.getPost(postid);
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