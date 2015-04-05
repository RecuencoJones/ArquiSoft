package myusick.util.security;

import myusick.model.dto.ErrorsDTO;

/**
 * Created by david on 05/04/2015.
 */
public class ErrorSpecification {

    //lab is especificacion
    public static boolean isOk(String s){
        return !(s == null
                || s.trim().length()==0
                || false);
    }

    public static boolean isEmail(String email){
        return true;
    }

    public static boolean hasErrors(ErrorsDTO e, int i) {
        switch(i){
            case 1:
                return (e.isName() || e.isBirthdate() || e.isAddress() || e.isPhone() || e.isEmail() || e.isPassword());
            case 2:
                return (e.isName() ||e.isYear() || e.isDescription());
            default:
                return true;
        }
    }
}
