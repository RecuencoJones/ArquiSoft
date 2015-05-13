package myusick.util.security;


import myusick.controller.dto.AuthTokenDTO;
import myusick.controller.dto.LoginDTO;
import myusick.controller.dto.PublisherDTO;

import java.util.ArrayList;

/**
 * Created by david on 11/03/2015.
 */
public class AuthTokenGenerator {

    /**
     * Generates a token based on user data 
     * @param loginUserDTO user to be tokenized
     * @return token
     */
    public static AuthTokenDTO generateToken(LoginDTO loginUserDTO){
        char[] chars = loginUserDTO.getPassword().toCharArray();
        int[] bytes = new int[chars.length];
        String s = "";
        for(int i = 0; i < bytes.length; i++){
            bytes[i] = chars[i];
            s += Integer.toBinaryString(bytes[i]);
        }
        ArrayList<Integer> groupsIds = new ArrayList<>();
        for(PublisherDTO g: loginUserDTO.getGroups()){
            groupsIds.add(g.getId());
        }
        AuthTokenDTO token = new AuthTokenDTO(s,loginUserDTO.getUserId(),groupsIds);
        return token;
    }
}
