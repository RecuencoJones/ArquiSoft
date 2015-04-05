package myusick.model.dto;

import java.io.Serializable;


/**
 * Created by Cuenta de clase on 02/04/2015.
 */
public class AuthTokenDTO implements Serializable {

    private String token;
    private int userid;
    private ErrorsDTO errorsDTO;

    public AuthTokenDTO() {
    }

    public AuthTokenDTO(String token, int userid) {
        this.token = token;
        this.userid = userid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public ErrorsDTO getErrorsDTO() {
        return errorsDTO;
    }

    public void setErrorsDTO(ErrorsDTO errorsDTO) {
        this.errorsDTO = errorsDTO;
    }
}
