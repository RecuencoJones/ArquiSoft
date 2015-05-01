package myusick.api.services;

import com.google.gson.Gson;
import myusick.api.WebsocketDispatcher;
import myusick.api.WebsocketProvider;
import myusick.model.dto.PublicationsDTO;
import myusick.model.services.MyusickService;

import javax.ws.rs.core.UriInfo;

/**
 * Created by david on 08/04/2015.
 */
public class PostService {

    public static String newPost(UriInfo info, PublicationsDTO postDTO) {
        Gson gson = new Gson();
        int pubid = new MyusickService().insertarPublicacion(postDTO);
        PublicationsDTO newPub = new PublicationsDTO(pubid, postDTO.getContent(), postDTO.getDate());
        
        //broadcast
        String subs = "1,2,3,4,5";
        WebsocketDispatcher wsd = WebsocketProvider.getWebsocketDispatcher();
        for(String s : subs.split(",")){
            wsd.dispatch(newPub,s);
        }
        System.out.println("======================");
        return gson.toJson(newPub);
    }
}