package myusick.api.services;

import com.google.gson.Gson;
import myusick.model.dto.ErrorsDTO;
import myusick.model.dto.GroupDTO;
import myusick.util.security.ErrorSpecification;

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
     * @param groupDTO grupo a crear
     * @return token de autenticación
     */
    public static String newGroup(UriInfo info, GroupDTO groupDTO){
        Gson gson = new Gson();
        ErrorsDTO errors = new ErrorsDTO();

        if(groupDTO == null){
            errors.setEmpty();
            return gson.toJson(errors);
        }

        System.out.println(groupDTO);

        //Check and set errors
        if(!ErrorSpecification.isOk(groupDTO.getName()))
            errors.setName();
        if(!ErrorSpecification.isOk(groupDTO.getYear()))
            errors.setYear();
        if(!ErrorSpecification.isOk(groupDTO.getDescription()))
            errors.setDescription();

        //Check if there were errors
        if(!ErrorSpecification.hasErrors(errors,2)){
            //TODO save registerUser to db
            
            return "{\"ok\": \"true\"}";
        }else{
            return gson.toJson(errors);
        }
    }
}
