package myusick.api.services;

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
        s += DocUtil.docEndpoint("GET", "/profile/{userid}", "PathParam(userid: int)", "application/json", "Saca los datos de perfil del usuario {userid}");
        s += DocUtil.docEndpoint("POST", "/newGroup", "application/json", "application/json", "Crea un grupo para un usuario");
        s += DocUtil.docEndpoint("POST", "/post", "application/json", "application/json", "Crea un post para un usuario");
        s += DocUtil.docEndpoint("POST", "/newtag", "application/json", "application/json", "Crea y/o añade un tag a un usuario");
        return s;
    }
}
