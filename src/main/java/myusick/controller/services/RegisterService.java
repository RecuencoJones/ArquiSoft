package myusick.controller.services;

import com.google.gson.Gson;
import myusick.controller.dto.AuthTokenDTO;
import myusick.controller.dto.ErrorsDTO;
import myusick.controller.dto.LoginDTO;
import myusick.controller.MyusickService;
import myusick.util.security.AuthTokenGenerator;
import myusick.controller.dto.RegisterDTO;
import myusick.util.security.ErrorSpecification;

import javax.ws.rs.core.UriInfo;

/**
 * Created by david on 16/03/2015.
 */
public class RegisterService {

    /**
     * Función de registro de usuarios
     * REST endpoint
     * Method: POST /register
     * Content-Type: application/json
     * Response-Type: application/json
     *
     * Registra a un usuario si los campos son correctos
     * @param info shit
     * @param registerDTO usuario a registrar
     * @return token de autenticación
     */
    public static String register(UriInfo info, RegisterDTO registerDTO){
        Gson gson = new Gson();
        AuthTokenDTO authTokenDTO = new AuthTokenDTO();
        authTokenDTO.setToken("no");
        ErrorsDTO errorsDTO = new ErrorsDTO();

        System.out.println(registerDTO);

        // If mapping went wrong
        if(registerDTO == null){
            errorsDTO.setEmpty();
            authTokenDTO.setErrorsDTO(errorsDTO);
            return gson.toJson(authTokenDTO);
        }

        // Check and set errors
        if(!ErrorSpecification.isOk(registerDTO.getName()))
            errorsDTO.setName();
        if(!ErrorSpecification.isOk(registerDTO.getBirthdate()))
            errorsDTO.setBirthdate();
        if(!ErrorSpecification.isOk(registerDTO.getCity()))
            errorsDTO.setAddress();
        if(!ErrorSpecification.isOk(registerDTO.getCountry()))
            errorsDTO.setAddress();
        if(!ErrorSpecification.isOk(registerDTO.getEmail()))
            errorsDTO.setEmail();
        if(!ErrorSpecification.isOk(registerDTO.getPassword()))
            errorsDTO.setPassword();
        if(!ErrorSpecification.isOk(registerDTO.getRepassword()))
            errorsDTO.setPassword();

        // Check if there were any errors
        if(!ErrorSpecification.hasErrors(errorsDTO,1)){
            // save registerUser to db
            int uuid = new MyusickService().registerUser(registerDTO);
            if(uuid!=-1) {
                LoginDTO user = new MyusickService().getLoginData(registerDTO.getEmail(), registerDTO.getPassword());
                user.setUserId(uuid);
                authTokenDTO = AuthTokenGenerator.generateToken(user);
            }else{
                errorsDTO.setEmail();
                authTokenDTO.setErrorsDTO(errorsDTO);
            }
        }else{
            authTokenDTO.setErrorsDTO(errorsDTO);
        }
        return gson.toJson(authTokenDTO);
    }
}
