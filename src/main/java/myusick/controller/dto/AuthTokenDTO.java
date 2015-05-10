package myusick.controller.dto;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * Created by Cuenta de clase on 02/04/2015.
 */
public class AuthTokenDTO implements Serializable {

    private String token;
    private int userid;
    private ArrayList<Integer> groupsIds;
    private ErrorsDTO errorsDTO;

    public AuthTokenDTO() {
    }

    public AuthTokenDTO(String token, int userid, ArrayList<Integer> groupsIds) {
        this.token = token;
        this.userid = userid;
        this.groupsIds = groupsIds;
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

    public ArrayList<Integer> getGroupsIds() {
        return groupsIds;
    }

    public void setGroupsIds(ArrayList<Integer> groupsIds) {
        this.groupsIds = groupsIds;
    }

    public ErrorsDTO getErrorsDTO() {
        return errorsDTO;
    }

    public void setErrorsDTO(ErrorsDTO errorsDTO) {
        this.errorsDTO = errorsDTO;
    }
}
