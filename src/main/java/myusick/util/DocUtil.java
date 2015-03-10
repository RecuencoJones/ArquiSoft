package myusick.util;

public class DocUtil {
    
    public static String docEndpoint(String httpVerb, String path, String responseType, String description){
        return "\t"+httpVerb+"\t"+path+"\n" +
                "\tResponse-Type: "+responseType+"\n" +
                "\t\t+ "+description+"\n\n";
        
    }
}
