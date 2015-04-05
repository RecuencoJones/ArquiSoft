package myusick.api.services;

import com.google.gson.Gson;
import myusick.model.dto.PostDTO;
import myusick.model.dto.ProfileDTO;
import myusick.model.dto.PublisherDTO;

import java.util.ArrayList;

/**
 * Created by david on 20/03/2015.
 */
public class ProfileService {
    public static String profile(int userid) {
        Gson gson = new Gson();
        String[] skills = {"Guitarra","Bajo","Retrasado"};
        String[] tags = {};
        ArrayList<PublisherDTO> groups = new ArrayList<>();
        groups.add(new PublisherDTO(123,"Ceporrin"));
        groups.add(new PublisherDTO(456,"Ceporron"));
        ArrayList<PostDTO> publications = new ArrayList<>();
        publications.add(new PostDTO(2,"img/placeholder.JPG",1676556867,"David",1,"Foo bar"));
        publications.add(new PostDTO(1,"img/placeholder.JPG",1464646878,"David",1,"LOL"));
        ProfileDTO user = new ProfileDTO("David",
                "Cute retarded unicorn",
                "img/placeholder.JPG",
                skills,
                tags,
                null,
                groups,
                publications);
        
        return gson.toJson(user);
    }
}
