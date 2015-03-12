package myusick.api;

import org.glassfish.jersey.server.ResourceConfig;

/**
 * Created by david on 10/03/2015.
 */
public class ApplicationConfig extends ResourceConfig{

    /**
     * Configuración de la aplicación
     * Registra el servicio donde se encuentran todos los endpoints
     * Adicionalmente registra la configuración de CORS
     * Todas las peticiones se harán a través de esta clase
     */
    public ApplicationConfig() {
        register(CrossDomainFilter.class);
        register(RestServiceEndpoints.class);
        //register(MOXyJsonProvider.class);
    }
}
