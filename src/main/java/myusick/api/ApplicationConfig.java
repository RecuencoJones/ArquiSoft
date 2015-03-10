package myusick.api;

import org.glassfish.jersey.server.ResourceConfig;

/**
 * Created by david on 10/03/2015.
 */
public class ApplicationConfig extends ResourceConfig{

    public ApplicationConfig() {
        register(CrossDomainFilter.class);
        register(Service.class);
        //register(MOXyJsonProvider.class);
    }
}
