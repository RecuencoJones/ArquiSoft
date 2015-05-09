package myusick.api.services;

import com.google.gson.Gson;
import myusick.model.dto.ProfileDTO;
import myusick.model.services.MyusickService;

import java.util.List;

/**
 * Created by david on 07/05/2015.
 */
public class SearchService {

    public static String searchPerson(String term) {
        Gson gson = new Gson();
        List<ProfileDTO> res = new MyusickService().buscarPersonaPorNombre(term);
        return gson.toJson(res);
    }

    public static String searchGroup(String term) {
        Gson gson = new Gson();
        List<ProfileDTO> res = new MyusickService().buscarGrupoPorNombre(term);
        return gson.toJson(res);
    }

    public static String searchTag(String term) {
        Gson gson = new Gson();
        List<ProfileDTO> res = new MyusickService().buscarPorTag(term);
        return gson.toJson(res);
    }

    public static String searchSkill(String term) {
        Gson gson = new Gson();
        List<ProfileDTO> res = new MyusickService().buscarPorAptitud(term);
        return gson.toJson(res);
    }
}
