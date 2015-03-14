package myusick.api;

import com.google.gson.Gson;

import myusick.model.LoginUser;
import myusick.model.RegisterUser;
import myusick.util.security.AuthToken;
import myusick.util.doc.DocUtil;
import myusick.util.security.Errors;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

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
        s += DocUtil.docEndpoint("POST", "/register", "application/json", "application/json", "Registra a un usuario");
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
    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String register(@Context UriInfo info, RegisterUser registerUser){
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