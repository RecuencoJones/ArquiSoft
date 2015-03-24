package myusick.model.dto;

/**
 * Created by david on 11/03/2015.
 */
public class LoginUserDTO {
    
    private String user;
    private String password;
    private int userId;
    
    public LoginUserDTO(String user, String password){
        this.user=user;
        this.password=password;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
