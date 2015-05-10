package myusick.controller.services;

import com.google.gson.Gson;
import myusick.controller.SSEDispatcher;
import myusick.controller.SSEProvider;
import myusick.controller.dto.PostDTO;
import myusick.controller.dto.PublicationsDTO;
import myusick.controller.MyusickService;

import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by david on 08/04/2015.
 */
public class PostService {

    public static String newPost(UriInfo info, PublicationsDTO postDTO) {
        Gson gson = new Gson();
        int pubid = new MyusickService().insertarPublicacion(postDTO);
        PostDTO post = new MyusickService().getPost(pubid);
//        PublicationsDTO newPub = new PublicationsDTO(postDTO.getId(), postDTO.getContent(), postDTO.getDate());
        
        //broadcast
//        String subs = "1,2,3,4,5";
        ArrayList<Integer> subs = new MyusickService().getFollowersIds(postDTO.getId());
        SSEDispatcher wsd = SSEProvider.getSSEDispatcher();
        for(Integer s : subs){
            wsd.dispatch(post,s.toString());
        }
        System.out.println("======================");
        return gson.toJson(post);
    }

    public static String getPost(int id) {
        Gson gson = new Gson();
        PostDTO pub = new MyusickService().getPost(id);
        return gson.toJson(pub);
    }

    public static String getLatestPosts(int userid) {
        Gson gson = new Gson();
        List<PostDTO> posts = new MyusickService().ultimasPublicaciones(userid);
        return gson.toJson(posts);
    }
}
