package myusick.api;

import com.google.gson.Gson;

import myusick.model.User;
import myusick.util.security.AuthToken;
import myusick.util.doc.DocUtil;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * REST backend
 * Backend del servidor
 * Todos los endpoints de la API se encontrarán aquí
 */
@Path("/")
public class RestServiceEndpoints {

    /**
     * REST endpoint
     * Method: GET /
     * Response-Type: text/plain
     *
     * Devuelve un listado en texto plano del directorio de endpoints del servidor
     * @return listado en texto plano del directorio de endpoints del servidor
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String listEndpoints(){
        String s = "Directorio de endpoints de esta API: \n";
        s += DocUtil.docEndpoint("GET", "/", "", "text/plain", "Devuelve este documento");
        s += DocUtil.docEndpoint("GET", "/hello/{name}", "URL param", "text/plain", "Devuelve \"Hello \" + el parametro especificado en la URL");
        s += DocUtil.docEndpoint("POST", "/auth", "application/x-www-form-urlencoded", "application/json", "Autentica a un usuario");
        return s;
    }
    
    /**
     * Función de ejemplo
     * REST endpoint
     * Method: GET /hello/{name}
     * Response-Type: text/plain
     * 
     * Devuelve un string con "Hello " + el parámetro especificado en la URL
     * @param name parámetro de la URL
     * @return "Hello " + name
     */
	@GET
    @Path("/hello/{name}")
	@Produces(MediaType.TEXT_PLAIN)
	public String getMessage(@PathParam("name") String name) {
		return "Hello "+name;
	}

    /*
     * El resto de métodos deberán situarse por aquí 
     */

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
    @POST
    @Path("/auth")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public String authenticate(@FormParam("user") String user, @FormParam("password") String password){
        Gson gson = new Gson();
        User userDatabase, userForm;
        userForm = new User(user,password);
        userDatabase = new User("foo@bar.com","foobar"); //db.queryForUser(authForm.getUser());
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