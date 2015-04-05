package myusick.api.services;

import com.google.gson.Gson;
import myusick.model.dto.ErrorsDTO;
import myusick.model.dto.NewGroupDTO;
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
     * @param newGroupDTO grupo a crear
     * @return token de autenticación
     */
    public static String newGroup(UriInfo info, NewGroupDTO newGroupDTO){
        Gson gson = new Gson();
        ErrorsDTO errors = new ErrorsDTO();

        if(newGroupDTO == null){
            errors.setEmpty();
            return gson.toJson(errors);
        }

        System.out.println(newGroupDTO);

        //Check and set errors
        if(!ErrorSpecification.isOk(newGroupDTO.getName()))
            errors.setName();
        if(!ErrorSpecification.isOk(newGroupDTO.getYear()))
            errors.setYear();
        if(!ErrorSpecification.isOk(newGroupDTO.getDescription()))
            errors.setDescription();

        //Check if there were errors
        if(!ErrorSpecification.hasErrors(errors,2)){
            //save registerUser to db
            return "{\"ok\": \"true\"}";
        }else{
            return gson.toJson(errors);
        }
    }
}
