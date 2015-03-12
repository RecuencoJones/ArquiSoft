package myusick.util;

import myusick.model.User;

/**
 * Created by david on 11/03/2015.
 */
public class AuthToken {
    
    private String token;
    private String user;

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

    /**
     * Generates a token based on user data 
     * @param user user to be tokenized
     * @return token
     */
    public static AuthToken generateToken(User user){
        char[] chars = user.getPassword().toCharArray();
        int[] bytes = new int[chars.length];
        String s = "";
        for(int i = 0; i < bytes.length; i++){
            bytes[i] = chars[i];
            s += Integer.toBinaryString(bytes[i]);
        }
        AuthToken token = new AuthToken();
        token.setToken(s);
        token.setUser(user.getUser());
        return token;
    }
}
