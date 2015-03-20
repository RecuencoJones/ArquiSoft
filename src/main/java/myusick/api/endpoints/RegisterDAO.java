package myusick.api.endpoints;

import com.google.gson.Gson;
import myusick.model.dto.LoginUserDTO;
import myusick.model.dto.RegisterUserDTO;
import myusick.util.security.AuthToken;
import myusick.util.security.Errors;

import javax.ws.rs.core.UriInfo;

/**
 * Created by david on 16/03/2015.
 */
public class RegisterDAO {

    /**
     * Función de registro de usuarios
     * REST endpoint
     * Method: POST /register
     * Content-Type: application/json
     * Response-Type: application/json
     *
     * Registra a un usuario si los campos son correctos
     * @param info shit
     * @param registerUserDTO usuario a registrar
     * @return token de autenticación
     */
    public static String register(UriInfo info, RegisterUserDTO registerUserDTO){
        Gson gson = new Gson();
        AuthToken authToken = new AuthToken();
        authToken.setToken("no");
        Errors errors = new Errors();

        if(registerUserDTO == null){
            authToken.setErrors(errors.setEmpty());
            return gson.toJson(authToken);
        }

        System.out.println(registerUserDTO);

        //Check and set errors
        if(!Errors.isOk(registerUserDTO.getName()))
            errors.setName();
        if(!Errors.isOk(registerUserDTO.getBirthdate()))
            errors.setBirthdate();
        if(!Errors.isOk(registerUserDTO.getCity()))
            errors.setAddress();
        if(!Errors.isOk(registerUserDTO.getCountry()))
            errors.setAddress();
        if(!Errors.isOk(registerUserDTO.getEmail()))
            errors.setEmail();
        if(!Errors.isOk(registerUserDTO.getPassword()))
            errors.setPassword();
        if(!Errors.isOk(registerUserDTO.getRepassword()))
            errors.setPassword();

        //Check if there were errors
        if(!errors.hasErrors()){
            //save registerUser to db
            LoginUserDTO user = new LoginUserDTO(registerUserDTO.getEmail(), registerUserDTO.getPassword());
            user.setUserId(1);
            authToken = AuthToken.generateToken(user);
        }else{
            authToken.setErrors(errors);
        }
        return gson.toJson(authToken);
    }
}
