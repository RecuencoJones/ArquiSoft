package myusick.util.doc;

public class DocUtil {

    /**
     * Método para documentar los endpoints
     * @param httpVerb verbo HTTP usado
     * @param path el path del endpoint
     * @param consumes tipo de documento consumido
     * @param produces tipo de documento producido
     * @param description descripción del endpoint
     * @return string formateado con la documentación
     */
    public static String docService(String httpVerb, String path, String consumes, String produces, String description){
        return "\t" + httpVerb + "\t" + path + "\n" +
                "\tConsumes: " + consumes + "\n" +
                "\tResponse-Type: " + produces + "\n" +
                "\t\t+ " + description + "\n\n";

    }

    public static String header(String rest) {
        return "\n" +
                "\t============================\n" +
                "\t" + rest + "\n" +
                "\t============================\n";
    }
}
