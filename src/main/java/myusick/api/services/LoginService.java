package myusick.api.services;

import com.google.gson.Gson;
import myusick.model.dto.LoginUserDTO;
import myusick.util.security.AuthToken;

public class LoginService {

    /**
     * Función de autenticación de usuarios
     * REST endpoint
     * Method: POST /auth
     * Content-Type: application/x-www-form-urlencoded
     * Response-Type: application/json
     *
     * Autentifica a un usuario si existe
     * @param user usuario que trata de loggearse
     * @param password password del usuario
     * @return token de autenticación
     */
    public static String authenticate(String user, String password){
        Gson gson = new Gson();
        LoginUserDTO userDatabase, userForm;
        userForm = new LoginUserDTO(user,password);
        userDatabase = new LoginUserDTO("foo@bar.com","foobar"); //db.queryForUser(authForm.getUser());
        userDatabase.setUserId(1);
        AuthToken authToken = new AuthToken();
        authToken.setToken("no"); //default not allowed
        if(userForm.getPassword().equals(userDatabase.getPassword())){
            //allow auth, send token
            //authToken="allowed";
            authToken = AuthToken.generateToken(userDatabase);
        }
        return gson.toJson(authToken);
    }
}
