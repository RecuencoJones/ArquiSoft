package myusick.api.services;

import com.google.gson.Gson;
import myusick.model.dto.TagDTO;
import myusick.model.services.MyusickService;

import javax.ws.rs.core.UriInfo;

/**
 * Created by david on 05/04/2015.
 */
public class TagService {

    public static String newTag(UriInfo info, TagDTO tagDTO) {
        Gson gson = new Gson();
        boolean success = new MyusickService().registrarTag(tagDTO);
        if(success){
            return "{\"ok\": \"true\"}";
        }else{
            return "{\"ok\": \"false\"}";
        }
    }
}
