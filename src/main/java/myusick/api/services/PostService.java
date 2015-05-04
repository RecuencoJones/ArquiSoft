package myusick.api.services;

import com.google.gson.Gson;
import myusick.api.WebsocketDispatcher;
import myusick.api.WebsocketProvider;
import myusick.model.dto.PostDTO;
import myusick.model.dto.PublicationsDTO;
import myusick.model.services.MyusickService;

import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;

/**
 * Created by david on 08/04/2015.
 */
public class PostService {

    public static String newPost(UriInfo info, PublicationsDTO postDTO) {
        Gson gson = new Gson();
        int pubid = new MyusickService().insertarPublicacion(postDTO);
        PublicationsDTO newPub = new PublicationsDTO(postDTO.getId(), postDTO.getContent(), postDTO.getDate());
        
        //broadcast
//        String subs = "1,2,3,4,5";
        ArrayList<Integer> subs = new MyusickService().getFollowers(postDTO.getId());
        WebsocketDispatcher wsd = WebsocketProvider.getWebsocketDispatcher();
        for(Integer s : subs){
            wsd.dispatch(newPub,s.toString());
        }
        System.out.println("======================");
        return gson.toJson(newPub);
    }

    public static String getPost(int id) {
        Gson gson = new Gson();
        PostDTO pub = new MyusickService().getPost(id);
        return gson.toJson(pub);
    }
}
