package myusick.api;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

/**
 * Created by david on 10/03/2015.
 */

@Provider
@PreMatching
public class CrossDomainFilter implements ContainerResponseFilter {

    /**
     * Configuración del filtro CORS
     * @param cres
     * @param arg1
     * @throws IOException
     */
    @Override
    public void filter(ContainerRequestContext cres, ContainerResponseContext arg1) throws IOException {
        arg1.getHeaders().add("Access-Control-Allow-Origin", "*");
        arg1.getHeaders().add("Access-Control-Allow-Credentials", "true");
        arg1.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
        arg1.getHeaders().add("Access-Control-Max-Age", "1209600");
        arg1.getHeaders().add("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
    }
}
