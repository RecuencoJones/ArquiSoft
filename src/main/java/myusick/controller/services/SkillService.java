package myusick.controller.services;

import com.google.gson.Gson;
import myusick.controller.dto.SkillTagDTO;
import myusick.controller.MyusickService;

import javax.ws.rs.core.UriInfo;

/**
 * Created by david on 09/05/2015.
 */
public class SkillService {
    public static String newSkill(UriInfo info, SkillTagDTO skillDTO) {
        Gson gson = new Gson();
        boolean success = new MyusickService().registrarSkill(skillDTO);
        if(success){
            return "{\"ok\": \"true\"}";
        }else{
            return "{\"ok\": \"false\"}";
        }
    }
}
