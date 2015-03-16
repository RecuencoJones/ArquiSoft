package myusick.api.endpoints;

import com.google.gson.Gson;
import myusick.model.LoginUser;
import myusick.model.RegisterUser;
import myusick.util.security.AuthToken;
import myusick.util.security.Errors;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

/**
 * Created by david on 16/03/2015.
 */
public class RegisterEndpoint {

    /**
     * Función de registro de usuarios
     * REST endpoint
     * Method: POST /register
     * Content-Type: application/json
     * Response-Type: application/json
     *
     * Registra a un usuario si los campos son correctos
     * @param info shit
     * @param registerUser usuario a registrar
     * @return token de autenticación
     */
    public static String register(UriInfo info, RegisterUser registerUser){
        Gson gson = new Gson();
        AuthToken authToken = new AuthToken();
        authToken.setToken("no");
        Errors errors = new Errors();

        if(registerUser == null){
            authToken.setErrors(errors.setEmpty());
            return gson.toJson(authToken);
        }

        System.out.println(registerUser);

        //Check and set errors
        if(!Errors.isOk(registerUser.getName()))
            errors.setName();
        if(!Errors.isOk(registerUser.getLastname()))
            errors.setName();
        if(!Errors.isOk(registerUser.getBirthdate()))
            errors.setBirthdate();
        if(!Errors.isOk(registerUser.getAddress()))
            errors.setAddress();
        if(!Errors.isOk(registerUser.getCity()))
            errors.setAddress();
        if(!Errors.isOk(registerUser.getCountry()))
            errors.setAddress();
        if(!Errors.isOk(registerUser.getZipcode()))
            errors.setAddress();
        if(!Errors.isOk(registerUser.getPhone()))
            errors.setPhone();
        if(!Errors.isOk(registerUser.getEmail()))
            errors.setEmail();
        if(!Errors.isOk(registerUser.getPassword()))
            errors.setPassword();
        if(!Errors.isOk(registerUser.getRepassword()))
            errors.setPassword();

        //Check if there were errors
        if(!errors.hasErrors()){
            //save registerUser to db
            LoginUser user = new LoginUser(registerUser.getEmail(),registerUser.getPassword());
            authToken = AuthToken.generateToken(user);
        }else{
            authToken.setErrors(errors);
        }
        return gson.toJson(authToken);
    }
}
