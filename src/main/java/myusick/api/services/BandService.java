package myusick.api.services;

import com.google.gson.Gson;
import myusick.model.dto.PublisherDTO;
import myusick.model.services.MyusickService;

import java.util.List;

/**
 * Created by david on 06/05/2015.
 */
public class BandService {
    public static String apply(int bandid, int personid) {
        boolean res = new MyusickService().solicitudInsercionGrupo(personid,bandid);
        return "{\"res\":"+res+"}";
    }

    // TODO
    public static String leave(int bandid, int personid) {
        boolean res = new MyusickService().eliminarDeGrupo(personid,bandid);
        new MyusickService().unfollow(personid,bandid);
        return "{\"res\":"+res+"}";
    }

    public static String accept(int bandid, int personid) {
        boolean res = new MyusickService().responderPeticion(personid,bandid,true);
        new MyusickService().follow(personid,bandid);
        return "{\"res\":"+res+"}";
    }

    public static String reject(int bandid, int personid) {
        boolean res = new MyusickService().responderPeticion(personid, bandid, false);
        return "{\"res\":"+res+"}";
    }

    public static String getApplicants(int bandid) {
        Gson gson = new Gson();
        List<PublisherDTO> ids = new MyusickService().pendientesDeAceptar(bandid);
        return gson.toJson(ids);
    }
}
