package myusick.model.services;

import myusick.model.dto.*;
import myusick.persistence.DAO.*;
import myusick.persistence.VO.Grupo;
import myusick.persistence.VO.Persona;
import myusick.persistence.connection.ConnectionAdmin;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Cuenta de clase on 02/04/2015.
 */
public class MyusickService {

    public MyusickService() {
    }

    public int registerUser(RegisterDTO rd){
        PersonaDAO pdao = new PersonaDAO();
        try {
            pdao.setConnection(ConnectionAdmin.getConnection());
            int res = pdao.registerUser(rd);
            pdao.closeConnection();
            return res;
        }catch (SQLException e){
            e.printStackTrace();
            return -1;
        }
    }

    public boolean registerGroup(GroupDTO gd){
        GrupoDAO gdao = new GrupoDAO();
        try{
            gdao.setConnection(ConnectionAdmin.getConnection());
            boolean res = gdao.registerGroup(gd);
            gdao.closeConnection();
            return res;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public ProfileDTO getProfileData(int uuid) {
        ProfileDTO profile = new ProfileDTO();
        GrupoDAO gdao = new GrupoDAO();
        PersonaDAO pdao = new PersonaDAO();
        TagDAO tdao = new TagDAO();
        AptitudDAO adao = new AptitudDAO();
        PublicacionDAO pubdao = new PublicacionDAO();
        try {
            gdao.setConnection(ConnectionAdmin.getConnection());
            pdao.setConnection(ConnectionAdmin.getConnection());
            tdao.setConnection(ConnectionAdmin.getConnection());
            adao.setConnection(ConnectionAdmin.getConnection());
            pubdao.setConnection(ConnectionAdmin.getConnection());
            
            if (gdao.esUnGrupo(uuid)) {
        /* Cogemos los datos necesarios de la entidad grupo*/
                Grupo g = gdao.getDataProfile(uuid);
                profile.setType(true);
                profile.setName(g.getNombre());
                profile.setYear(g.getAnyofundacion());
                profile.setAvatar(g.getAvatar());
                profile.setDescription(g.getDescripcion());
        /* Cogemos las tags que tiene almacenadas, si las hay*/
                profile.setTags(tdao.getTagsByGrupo(uuid));
        /* Si es un grupo, almacenamos los miembros del mismo */
                profile.setMembers(gdao.getMembersGroup(uuid));
        /* Almacenamos las publicaciones que tenga */
                profile.setPublications(pubdao.getPublicacionesById(uuid));
            } else if (pdao.esUnaPersona(uuid)) {
        /* Cogemos los datos necesarios de la entidad persona*/
                Persona p = pdao.getDataProfile(uuid);
                profile.setType(false);
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
                profile.setPublications(pubdao.getPublicacionesById(uuid));
            } else {
            /* El usuario no existe */
                System.out.printf("No existe el usuario\n");
                profile = null;
            }
            gdao.closeConnection();
            pdao.closeConnection();
            tdao.closeConnection();
            adao.closeConnection();
            pubdao.closeConnection();
            
            return profile;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public LoginDTO getLoginData(String email, String password){
        PersonaDAO pdao = new PersonaDAO();
        try {
            pdao.setConnection(ConnectionAdmin.getConnection());
            LoginDTO res =pdao.getLoginData(email, password);
            pdao.closeConnection();
            return res;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public boolean registrarTag(TagDTO td){
        TagDAO tdao = new TagDAO();
        try {
            tdao.setConnection(ConnectionAdmin.getConnection());
            boolean res = tdao.registrarTag(td);
            tdao.closeConnection();
            return res;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public int insertarPublicacion(PublicationsDTO pdto){
        PublicacionDAO pubdao = new PublicacionDAO();
        try {
            pubdao.setConnection(ConnectionAdmin.getConnection());
            int res = pubdao.insertarPublicacion(pdto.getDate(), pdto.getContent(), pdto.getId());
            pubdao.closeConnection();
            return res;
        }catch (SQLException e){
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Devuelve la lista de ids de publicantes
     * @param uuid id del publicante
     * @return lista de enteros con los ids de publicantes
     */
    public ArrayList<Integer> getFollowers(int uuid){
        SeguirDAO sdao = new SeguirDAO();
        try {
            sdao.setConnection(ConnectionAdmin.getConnection());
            ArrayList<Integer> res = sdao.getFollowers(uuid);
            sdao.closeConnection();
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public boolean follow(int seguidor, int seguido){
        SeguirDAO sdao = new SeguirDAO();
        try {
            sdao.setConnection(ConnectionAdmin.getConnection());
            boolean res = sdao.follow(seguidor,seguido);
            sdao.closeConnection();
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean unfollow(int seguidor, int seguido){
        SeguirDAO sdao = new SeguirDAO();
        try {
            sdao.setConnection(ConnectionAdmin.getConnection());
            boolean res = sdao.unfollow(seguidor, seguido);
            sdao.closeConnection();
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isfollow(int seguidor, int seguido){
        SeguirDAO sdao = new SeguirDAO();
        try {
            sdao.setConnection(ConnectionAdmin.getConnection());
            boolean res = sdao.isfollow(seguidor, seguido);
            sdao.closeConnection();
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
