package myusick.controller.services;

import com.google.gson.Gson;
import myusick.controller.dto.SkillTagDTO;
import myusick.controller.MyusickService;

import javax.ws.rs.core.UriInfo;

/**
 * Created by david on 05/04/2015.
 */
public class TagService {

    public static String newTag(UriInfo info, SkillTagDTO tagDTO) {
        Gson gson = new Gson();
        boolean success = new MyusickService().registrarTag(tagDTO);
        if(success){
            return "{\"ok\": \"true\"}";
        }else{
            return "{\"ok\": \"false\"}";
        }
    }
}
