package myusick.util.security;

import myusick.model.LoginUser;

/**
 * Created by david on 11/03/2015.
 */
public class AuthToken {
    
    private String token;
    private String user;
    private Errors errors;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setErrors(Errors errors) {
        this.errors = errors;
    }

    /**
     * Generates a token based on user data 
     * @param loginUser user to be tokenized
     * @return token
     */
    public static AuthToken generateToken(LoginUser loginUser){
        char[] chars = loginUser.getPassword().toCharArray();
        int[] bytes = new int[chars.length];
        String s = "";
        for(int i = 0; i < bytes.length; i++){
            bytes[i] = chars[i];
            s += Integer.toBinaryString(bytes[i]);
        }
        AuthToken token = new AuthToken();
        token.setToken(s);
        token.setUser(loginUser.getUser());
        return token;
    }
}
