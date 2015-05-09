package myusick.api.services;

import com.google.gson.Gson;
import myusick.model.services.MyusickService;

/**
 * Created by david on 07/05/2015.
 */
public class SearchService {

    //TODO
    public static String searchPerson(String term) {
        Gson gson = new Gson();
        new MyusickService().buscarPersonaPorNombre(term);
        return gson.toJson("derp");
    }

    //TODO
    public static String searchGroup(String term) {
        Gson gson = new Gson();
        new MyusickService().buscarGrupoPorNombre(term);
        return null;
    }

    //TODO
    public static String searchTag(String term) {
        Gson gson = new Gson();
        new MyusickService().buscarPublicantePorTag(term);
        return null;
    }

    //TODO
    public static String searchSkill(String term) {
        Gson gson = new Gson();
        new MyusickService().buscarPersonaPorAptitud(term);
        return null;
    }
}
