package myusick.api.services;

import com.google.gson.Gson;
import myusick.model.dto.LoginUserDTO;
import myusick.model.dto.NewGroupDTO;
import myusick.model.dto.RegisterUserDTO;
import myusick.util.security.AuthToken;
import myusick.util.security.Errors;

import javax.ws.rs.core.UriInfo;

/**
 * Created by david on 16/03/2015.
 */
public class NewGroupService {

    /**
     * Función de creación de grupos
     * REST endpoint
     * Method: POST /newGroup
     * Content-Type: application/json
     * Response-Type: application/json
     *
     * Crea un grupo si los campos son correctos
     * @param info shit
     * @param newGroupDTO grupo a crear
     * @return token de autenticación
     */
    public static String newGroup(UriInfo info, NewGroupDTO newGroupDTO){
        Gson gson = new Gson();
        Errors errors = new Errors();

        if(newGroupDTO == null){
            errors.setEmpty();
            return gson.toJson(errors);
        }

        System.out.println(newGroupDTO);

        //Check and set errors
        if(!Errors.isOk(newGroupDTO.getName()))
            errors.setName();
        if(!Errors.isOk(newGroupDTO.getYear()))
            errors.setYear();
        if(!Errors.isOk(newGroupDTO.getDescription()))
            errors.setDescription();

        //Check if there were errors
        if(!errors.hasErrors(2)){
            //save registerUser to db
            return "{\"ok\": \"true\"}";
        }else{
            return gson.toJson(errors);
        }
    }
}
