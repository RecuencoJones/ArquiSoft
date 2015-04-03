package myusick.util.security;

import myusick.model.DTO.LoginUserDTO;

/**
 * Created by david on 11/03/2015.
 */
public class AuthToken {
    
    private String token;
    private int userid;
    private Errors errors;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getUser() {
        return userid;
    }

    public void setUser(int userid) {
        this.userid = userid;
    }

    public void setErrors(Errors errors) {
        this.errors = errors;
    }

    /**
     * Generates a token based on user data 
     * @param loginUserDTO user to be tokenized
     * @return token
     */
    public static AuthToken generateToken(LoginUserDTO loginUserDTO){
        char[] chars = loginUserDTO.getPassword().toCharArray();
        int[] bytes = new int[chars.length];
        String s = "";
        for(int i = 0; i < bytes.length; i++){
            bytes[i] = chars[i];
            s += Integer.toBinaryString(bytes[i]);
        }
        AuthToken token = new AuthToken();
        token.setToken(s);
        token.setUser(loginUserDTO.getUserId());
        return token;
    }
}
