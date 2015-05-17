package myusick.controller.services;

import com.google.gson.Gson;
import myusick.controller.dto.EditDTO;
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

    /**
     * TODO
     * @param editDTO
     * @return
     */
    public static String editProfile(EditDTO editDTO) {
        Gson gson = new Gson();
        if(editDTO.isType()){
            //es grupo
            if(editDTO.getAvatar() != null && !editDTO.getAvatar().isEmpty()){
                new MyusickService().setAvatar(editDTO.getId(), editDTO.getAvatar());
            }
            if(editDTO.getDescripcion() != null && !editDTO.getDescripcion().isEmpty()){
                new MyusickService().setDescripcion(editDTO.getId(), editDTO.getDescripcion());
            }
            if(editDTO.getNombre() != null && !editDTO.getNombre().isEmpty()) {
                new MyusickService().setNombre(editDTO.getId(), editDTO.getNombre());
            }
        }else{
            //es persona
            if(editDTO.getAvatar() != null && !editDTO.getAvatar().isEmpty()){
                new MyusickService().setAvatarPersona(editDTO.getId(),editDTO.getAvatar());
            }
            if(editDTO.getDescripcion() != null && !editDTO.getDescripcion().isEmpty()){
                new MyusickService().setDescripcionPersona(editDTO.getId(),editDTO.getDescripcion());
            }
            if(editDTO.getNombre() != null && !editDTO.getNombre().isEmpty()){
                new MyusickService().setNombrePersona(editDTO.getId(),editDTO.getNombre());
            }
            if(editDTO.getTelefono() != 0){
                new MyusickService().setTelefono(editDTO.getId(),editDTO.getTelefono());
            }
            if(editDTO.getPassword() != null && !editDTO.getPassword().isEmpty() && editDTO.getPassword().equals(editDTO.getRepassword())){
                new MyusickService().setPassword(editDTO.getId(),editDTO.getPassword());
            }
        }
        return "{\"ok\": true}";
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