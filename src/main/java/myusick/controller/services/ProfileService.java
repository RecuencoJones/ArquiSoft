package myusick.controller.services;

import com.google.gson.Gson;
import myusick.controller.dto.ProfileDTO;
import myusick.controller.dto.PublisherDTO;
import myusick.controller.MyusickService;

import java.util.ArrayList;

/**
 * Created by david on 20/03/2015.
 */
public class ProfileService {
    
    public static String profile(int userid) {
        Gson gson = new Gson();        
        ProfileDTO user = new MyusickService().getProfileData(userid);
        return gson.toJson(user);
    }

    public static String getGroups(int userid) {
        Gson gson = new Gson();
        ProfileDTO user = new MyusickService().getProfileData(userid);
        ArrayList<Integer> bands = new ArrayList<>();
        for (PublisherDTO band : user.getGroups()){
            bands.add(band.getId());
        }
        return gson.toJson(bands);
    }
}


        /*String[] skills = {"Guitarra","Bajo","Retrasado"};
        String[] tags = {};
        ArrayList<PublisherDTO> groups = new ArrayList<>();
        groups.add(new PublisherDTO(123,"Ceporrin"));
        groups.add(new PublisherDTO(456,"Ceporron"));
        ArrayList<PostDTO> publications = new ArrayList<>();
        publications.add(new PostDTO(2,"img/placeholder.jpg",1676556867,"David",1,"Foo bar"));
        publications.add(new PostDTO(1,"img/placeholder.jpg",1464646878,"David",1,"LOL"));
        ProfileDTO user = new ProfileDTO("David",
                "Cute retarded unicorn",
                "img/placeholder.jpg",
                skills,
                tags,
                null,
                groups,
                publications);*/