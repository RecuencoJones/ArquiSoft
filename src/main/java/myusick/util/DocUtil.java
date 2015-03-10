package myusick.util;

public class DocUtil {

    /**
     * Método para documentar los endpoints
     * @param httpVerb verbo HTTP usado
     * @param path el path del endpoint
     * @param type tipo de documento de petición o respuesta
     * @param description descripción del endpoint
     * @return string formateado con la documentación
     */
    public static String docEndpoint(String httpVerb, String path, String type, String description){
        if(httpVerb.equalsIgnoreCase("post")) {
            return "\t" + httpVerb + "\t" + path + "\n" +
                    "\tConsumes: " + type + "\n" +
                    "\t\t+ " + description + "\n\n";
        }else{
            return "\t" + httpVerb + "\t" + path + "\n" +
                    "\tResponse-Type: " + type + "\n" +
                    "\t\t+ " + description + "\n\n";
        }
    }
}
