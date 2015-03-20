package myusick.api.endpoints;

import myusick.util.doc.DocUtil;

/**
 * Created by david on 16/03/2015.
 */
public class APIdirectory {

    /**
     * REST endpoint
     * Method: GET /
     * Response-Type: text/plain
     *
     * Devuelve un listado en texto plano del directorio de endpoints del servidor
     * @return listado en texto plano del directorio de endpoints del servidor
     */
    public static String listEndpoints(){
        String s = "Directorio de endpoints de esta API: \n";
        s += DocUtil.docEndpoint("GET", "/", "", "text/plain", "Devuelve este documento");
        s += DocUtil.docEndpoint("POST", "/auth", "application/x-www-form-urlencoded", "application/json", "Autentica a un usuario");
        s += DocUtil.docEndpoint("POST", "/register", "application/json", "application/json", "Registra a un usuario");
        return s;
    }
}