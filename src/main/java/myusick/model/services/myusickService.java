package myusick.model.services;

import myusick.model.dto.LoginDTO;
import myusick.model.dto.ProfileDTO;
import myusick.model.dto.RegisterDTO;
import myusick.persistence.DAO.*;
import myusick.persistence.VO.Grupo;
import myusick.persistence.VO.Persona;
import myusick.persistence.connection.ConnectionAdmin;

import java.sql.SQLException;

/**
 * Created by Cuenta de clase on 02/04/2015.
 */
public class MyusickService {

    private GrupoDAO gdao;
    private AptitudDAO adao;
    private PersonaDAO pdao;
    private PublicacionDAO pubdao;
    private TagDAO tdao;

    public MyusickService() {
        try{
            gdao = new GrupoDAO(); gdao.setConnection(ConnectionAdmin.getConnection());
            adao = new AptitudDAO(); adao.setConnection (ConnectionAdmin.getConnection());
            pdao = new PersonaDAO(); pdao.setConnection (ConnectionAdmin.getConnection());
            pubdao = new PublicacionDAO(); pubdao.setConnection (ConnectionAdmin.getConnection());
            tdao = new TagDAO(); tdao.setConnection (ConnectionAdmin.getConnection());

        }catch (SQLException e){
            /* Problema con la BD */
            e.printStackTrace();
            System.out.println("EJEJEJEJEJEJE");
        }
    }

    public int registerUser(RegisterDTO rd){
        return pdao.registerUser(rd);
    }
    
    public ProfileDTO getProfileData(int uuid) {
        ProfileDTO profile = new ProfileDTO();
        if (gdao.esUnGrupo(uuid)) {
        /* Cogemos los datos necesarios de la entidad grupo*/
            Grupo g = gdao.getDataProfile(uuid);
            profile.setName(g.getNombre());
            profile.setAvatar(g.getAvatar());
            profile.setDescription(g.getDescripcion());
        /* Cogemos las aptitudes que tiene almacenadas, si las hay*/
            profile.setSkills(adao.getAptitudesByPersona(uuid));
        /* Cogemos las tags que tiene almacenadas, si las hay*/
            profile.setTags(tdao.getTagsByPersona(uuid));
        /* Si es un grupo, almacenamos los miembros del mismo */
            profile.setMembers(gdao.getMembersGroup(uuid));
        /* Almacenamos las publicaciones que tenga */
            profile.setPublications(pubdao.getPublicacionesById(uuid));
        } else if (pdao.esUnaPersona(uuid)) {
        /* Cogemos los datos necesarios de la entidad persona*/
            Persona p = pdao.getDataProfile(uuid);
            profile.setName(p.getNombre());
            profile.setAvatar(p.getAvatar());
            profile.setDescription(p.getDescripcion());
        /* Cogemos las aptitudes que tiene almacenadas, si las hay*/
            profile.setSkills(adao.getAptitudesByPersona(uuid));
        /* Cogemos las tags que tiene almacenadas, si las hay*/
            profile.setTags(tdao.getTagsByPersona(uuid));
        /* Almacenamos si pertenece a algun grupo y a cuales en caso afirmativo */
            profile.setGroups(pdao.getGroupsByMember(p.getPublicante_uuid()));
        /* Almacenamos las publicaciones que tenga */
            profile.setPublications(pubdao.getPublicacionesById(p.getPublicante_uuid()));
        } else {
            /* El usuario no existe */
            System.out.printf("No existe el usuario\n");
            profile = null;
        }
        return profile;
    }

    public LoginDTO getLoginData(String email, String password){
        return pdao.getLoginData(email,password);
    }


}
