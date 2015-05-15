package myusick.controller.services;

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
    public static String listServices(){
        String s = "Directorio de servicios de esta API: \n";
        //RestServices
        s += DocUtil.header("Rest");
        s += DocUtil.docService("GET", "/", "", "text/plain", "Devuelve este documento");
        s += DocUtil.docService("POST", "/auth", "application/x-www-form-urlencoded", "application/json", "Autentica a un usuario");
        s += DocUtil.docService("POST", "/register", "application/json", "application/json", "Registra a un usuario");
        s += DocUtil.docService("GET", "/profile/{userid}", "PathParam(userid: int)", "application/json", "Saca los datos de perfil del usuario {userid}");
        s += DocUtil.docService("POST", "/newgroup", "application/json", "application/json", "Crea un grupo para un usuario");
        s += DocUtil.docService("POST", "/newtag", "application/json", "application/json", "Crea y/o añade un tag a un usuario");
        s += DocUtil.docService("POST", "/newskill", "application/json", "application/json", "Crea y/o añade una aptitud a un usuario");
        s += DocUtil.docService("POST", "/post", "application/json", "application/json", "Crea un post para un usuario");
        s += DocUtil.docService("GET", "/post/{id}", "PathParam(id: int)", "application/json", "Busca el comentario cuyo id es {id}");
        s += DocUtil.docService("PUT", "/follow/{seguidor}/{seguido}", "PathParam(seguidor: int, seguido: int)", "application/json", "El usuario {seguidor} sigue a {seguido}");
        s += DocUtil.docService("DELETE", "/unfollow/{seguidor}/{seguido}", "PathParam(seguidor: int, seguido: int)", "application/json", "El usuario {seguidor} deja de seguir a {seguido}");
        s += DocUtil.docService("GET", "/isfollowing/{seguidor}/{seguido}", "PathParam(seguidor: int, seguido: int)", "application/json", "Comprueba si el usuario {seguidor} sigue a {seguido}");
        s += DocUtil.docService("PUT", "/band/apply/{bandid}/{userid}", "PathParam(bandid: int, userid: int)", "application/json", "Agrega un nuevo miembro {userid} pendiente de aceptacion a la banda {bandid}");
        s += DocUtil.docService("DELETE", "/band/leave/{bandid}/{userid}", "PathParam(bandid: int, userid: int)", "application/json", "Elimina un nuevo miembro {userid} existente de la banda {bandid}");
        s += DocUtil.docService("PUT", "/band/accept/{bandid}/{userid}", "PathParam(bandid: int, userid: int)", "application/json", "Acepta un nuevo miembro {userid} en la banda {bandid}");
        s += DocUtil.docService("DELETE", "/band/reject/{bandid}/{userid}", "PathParam(bandid: int, userid: int)", "application/json", "Rechaza un nuevo miembro {userid} en la banda {bandid}");
        s += DocUtil.docService("GET", "/band/applicants/{bandid}", "PathParam(bandid: int)", "application/json", "Busca los miembros pendientes de aceptacion de la banda cuyo id es {bandid}");
        s += DocUtil.docService("GET", "/groups/{userid}", "PathParam(userid: int)", "application/json", "Busca los grupos del usuario cuyo id es {userid}");
        s += DocUtil.docService("GET", "/last/{userid}", "PathParam(userid: int)", "application/json", "Busca los ultimos mensajes de los publicantes a los cuales esta suscrito el usuario cuyo id es {userid}");
        s += DocUtil.docService("GET", "/search/person/{term}","PathParam(term: string)", "application/json", "Busca todas las personas cuyo nombre incluya {term}");
        s += DocUtil.docService("GET", "/search/group/{term}","PathParam(term: string)", "application/json", "Busca todos los grupos cuyo nombre incluya {term}");
        s += DocUtil.docService("GET", "/search/tag/{term}","PathParam(term: string)", "application/json", "Busca todos los publicantes que tengan la etiqueta {term}");
        s += DocUtil.docService("GET", "/search/skill/{term}","PathParam(term: string)", "application/json", "Busca todas las personas que tengan la aptitud {term}");
        s += DocUtil.docService("POST", "/edit/profile", "application/json", "application/json", "Edita el perfil de un publicante con los valores recibidos");
        //WebsocketProvider
        s += DocUtil.header("SSE");
        s += DocUtil.docService("GET", "/ws/sub/{id}", "PathParam(id: int)", "text/event-stream", "Suscribe a un usuario a su canal");
        s += DocUtil.docService("DELETE", "/ws/unsub/{id}", "PathParam(id: int)", "text/plain", "Desuscribe a un usuario de su canal");
        return s;
    }
}
