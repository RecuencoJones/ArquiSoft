package myusick.api.endpoints;

import com.google.gson.Gson;
import myusick.model.LoginUser;
import myusick.util.security.AuthToken;

public class LoginEndpoint {

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
        LoginUser userDatabase, userForm;
        userForm = new LoginUser(user,password);
        userDatabase = new LoginUser("foo@bar.com","foobar"); //db.queryForUser(authForm.getUser());
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
