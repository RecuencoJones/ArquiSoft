package myusick.util.security;

/**
 * Created by david on 13/03/2015.
 */
public class Errors {
    
    private boolean name;
    private boolean birthdate;
    private boolean address;
    private boolean phone;
    private boolean email;
    private boolean password;
    private boolean empty;

    public Errors(){
                
    }
    
    public Errors setEmpty(){
        this.empty = true;
        return this;
    }
    
    public Errors setName() {
        this.name = true;
        return this;
    }

    public Errors setBirthdate() {
        this.birthdate = true;
        return this;
    }

    public Errors setAddress() {
        this.address = true;
        return this;
    }

    public Errors setPhone() {
        this.phone = true;
        return this;
    }

    public Errors setEmail() {
        this.email = true;
        return this;
    }

    public Errors setPassword() {
        this.password = true;
        return this;
    }

    public boolean hasErrors() {
        return (name || birthdate || address || phone || email || password);
    }
    
    //lab is especificacion
    public static boolean isOk(String s){
        return !(s == null
                || s.trim().length()==0
                || false);
    }
    
    public static boolean isEmail(String email){
        return true;
    }
}
