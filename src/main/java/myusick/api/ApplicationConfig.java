package myusick.api;

import org.eclipse.persistence.jaxb.rs.MOXyJsonProvider;
import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.server.ResourceConfig;

import java.util.logging.Logger;

/**
 * Created by david on 10/03/2015.
 */
public class ApplicationConfig extends ResourceConfig {

    /**
     * Configuración de la aplicación
     * Registra el servicio donde se encuentran todos los endpoints
     * Adicionalmente registra la configuración de CORS
     * Todas las peticiones se harán a través de esta clase
     */
    public ApplicationConfig() {
        register(MOXyJsonProvider.class);
        register(CrossDomainFilter.class);
        register(RestServices.class);
        register(WebsocketProvider.class);
        registerInstances(new LoggingFilter(Logger.getLogger(ApplicationConfig.class.getName()),true));
    }
}