package myusick.model.DTO;

/**
 * Created by Cuenta de clase on 02/04/2015.
 */
public class AuthToken {

    private String token;
    private int userid;
    private Errors errors;

    public AuthToken(String token, int userid, Errors errors) {
        this.token = token;
        this.userid = userid;
        this.errors = errors;
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

    public Errors getErrors() {
        return errors;
    }

    public void setErrors(Errors errors) {
        this.errors = errors;
    }
}
