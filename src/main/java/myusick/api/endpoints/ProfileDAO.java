package myusick.api.endpoints;

import com.google.gson.Gson;
import myusick.model.dto.ProfileUserDTO;
import myusick.model.dto.PublicationDTO;
import myusick.model.dto.ShortGroupDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by david on 20/03/2015.
 */
public class ProfileDAO {
    public static String profile(int userid) {
        Gson gson = new Gson();
        String[] skills = {"Guitarra","Bajo","Retrasado"};
        List<ShortGroupDTO> groups = new ArrayList<>();
        groups.add(new ShortGroupDTO(123,"Ceporrín"));
        groups.add(new ShortGroupDTO(456,"Ceporrón"));
        List<PublicationDTO> publications = new ArrayList<>();
        publications.add(new PublicationDTO(2,1676556867,"David",1,"Foo bar"));
        publications.add(new PublicationDTO(1,1464646878,"David",1,"LOL"));
        ProfileUserDTO user = new ProfileUserDTO("David",
                "Cute retarded unicorn",
                "img/placeholder.JPG",
                skills,
                groups,
                publications);
        
        return gson.toJson(user);
    }
}
