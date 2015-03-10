package myusick.api;

import myusick.util.DocUtil;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * REST backend
 * Backend del servidor
 * Todos los endpoints de la API se encontrarán aquí
 */
@Path("/")
public class Service {

    /**
     * REST endpoint
     * Method: GET /
     * Response-Type: text/plain
     *
     * Devuelve un listado en texto plano del directorio de endpoints del servidor
     * @return listado en texto plano del directorio de endpoints del servidor
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String listEndpoints(){
        String s = "Directorio de endpoints de esta API: \n";
        s += DocUtil.docEndpoint("GET", "/", "text/plain", "Devuelve este documento");
        s += DocUtil.docEndpoint("GET", "/hello/{name}", "text/plain", "Devuelve \"Hello \" + el parametro especificado en la URL");
        return s;
    }
    
    /**
     * Función de ejemplo
     * REST endpoint
     * Method: GET /hello/{name}
     * Response-Type: text/plain
     * 
     * Devuelve un string con "Hello " + el parámetro especificado en la URL
     * @param name parámetro de la URL
     * @return "Hello " + name
     */
	@GET
    @Path("/hello/{name}")
	@Produces(MediaType.TEXT_PLAIN)
	public String getMessage(@PathParam("name") String name) {
		return "Hello "+name;
	}

    /*
     * El resto de métodos deberán situarse por aquí 
     */
}