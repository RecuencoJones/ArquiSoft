package myusick.model.services;

import myusick.model.dto.*;
import myusick.persistence.DAO.*;
import myusick.persistence.VO.Grupo;
import myusick.persistence.VO.Persona;
import myusick.persistence.connection.ConnectionAdmin;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sandra on 02/04/2015.
 */
public class MyusickService {

    public MyusickService() {
    }

    public int registerUser(RegisterDTO rd) {
        PersonaDAO pdao = new PersonaDAO();
        int res = pdao.registerUser(rd);
        return res;
    }

    public int registerGroup(GroupDTO gd) {
        GrupoDAO gdao = new GrupoDAO();
        int res = gdao.registerGroup(gd);
        return res;
    }

    public ProfileDTO getProfileData(int uuid) {
        ProfileDTO profile = new ProfileDTO();
        GrupoDAO gdao = new GrupoDAO();
        PersonaDAO pdao = new PersonaDAO();
        TagDAO tdao = new TagDAO();
        AptitudDAO adao = new AptitudDAO();
        PublicacionDAO pubdao = new PublicacionDAO();

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

        return profile;
    }

    public LoginDTO getLoginData(String email, String password) {
        PersonaDAO pdao = new PersonaDAO();
        LoginDTO res = pdao.getLoginData(email, password);
        return res;
    }

    public boolean registrarTag(TagDTO td) {
        TagDAO tdao = new TagDAO();
        boolean res = tdao.registrarTag(td);
        return res;
    }

    public int insertarPublicacion(PublicationsDTO pdto) {
        PublicacionDAO pubdao = new PublicacionDAO();
        int res = pubdao.insertarPublicacion(pdto.getDate(), pdto.getContent(), pdto.getId());
        return res;
    }

    /**
     * Devuelve la lista de ids de publicantes
     *
     * @param uuid id del publicante
     * @return lista de enteros con los ids de publicantes
     */
    public ArrayList<Integer> getFollowers(int uuid) {
        SeguirDAO sdao = new SeguirDAO();
        ArrayList<Integer> res = sdao.getFollowers(uuid);
        return res;
    }

    public boolean follow(int seguidor, int seguido) {
        SeguirDAO sdao = new SeguirDAO();
        boolean res = sdao.follow(seguidor, seguido);
        return res;
    }

    public boolean unfollow(int seguidor, int seguido) {
        SeguirDAO sdao = new SeguirDAO();
        boolean res = sdao.unfollow(seguidor, seguido);
        return res;
    }

    public boolean isfollow(int seguidor, int seguido) {
        SeguirDAO sdao = new SeguirDAO();
        boolean res = sdao.isfollow(seguidor, seguido);
        return res;
    }

    public PostDTO getPost(int id) {
        PublicacionDAO pdao = new PublicacionDAO();
        PostDTO res = pdao.getPostById(id);
        return res;
    }

    public boolean solicitudInsercionGrupo(int persona, int grupo) {
        GrupoDAO gdao = new GrupoDAO();
        boolean resultado = gdao.solicitudInsercionGrupo(persona, grupo);
        return resultado;
    }

    public boolean responderPeticion(int persona, int grupo, boolean decision) {
        GrupoDAO gdao = new GrupoDAO();
        boolean resultado;
        if (decision) {
                /* El miembro ha sido anadido al grupo */
            resultado = gdao.anadirAGrupo(persona, grupo);
        } else {
                /* El miembro ha sido rechazado del grupo */
            resultado = gdao.rechazarDeGrupo(persona, grupo);
        }
        return resultado;
    }

    public boolean eliminarDeGrupo(int persona, int grupo) {
        GrupoDAO gdao = new GrupoDAO();
        boolean resultado = gdao.eliminarDeGrupo(persona, grupo);
        return resultado;
    }

    public List<PublisherDTO> pendientesDeAceptar(int grupo) {
        GrupoDAO gdao = new GrupoDAO();
        List<PublisherDTO> resultado = gdao.pendientesDeAceptar(grupo);
        return resultado;
    }

    public List<PostDTO> ultimasPublicaciones(int seguidor) {
        PublicacionDAO pdao = new PublicacionDAO();
        List<PostDTO> resultado = pdao.ultimasPublicaciones(seguidor);
        return resultado;
    }

    public List<ProfileDTO> buscarPorNombre(String nombre) {
        /* Primero intentamos ver si es una persona */
        PersonaDAO pdao = new PersonaDAO();
        List<ProfileDTO> resultado = pdao.buscarPorNombre(nombre);
            /* Buscamos si hay coincidencias por grupo */
        GrupoDAO gdao = new GrupoDAO();
        resultado.addAll(gdao.buscarPorNombre(nombre));
            /*
             * Tanto en caso de que se haya encontrado persona, grupo o no se hayan
             * encontrado resultados, se devuelve la lista igualmente (completa o null)
             */
        return resultado;
    }

    public List<ProfileDTO> buscarPorTag(String tag) {
        /* Primero intentamos ver si es una persona */
        PersonaDAO pdao = new PersonaDAO();
        List<ProfileDTO> resultado = pdao.buscarPorTag(tag);
        /* Buscamos si hay coincidencias por grupo */
        GrupoDAO gdao = new GrupoDAO();
        resultado.addAll(gdao.buscarPorTag(tag));
        /*
         * Tanto en caso de que se haya encontrado persona, grupo o no se hayan
         * encontrado resultados, se devuelve la lista igualmente (completa o null)
         */
        return resultado;
    }

    public List<ProfileDTO> buscarPorAptitud(String aptitud) {
    /* Primero intentamos ver si es una persona */
        PersonaDAO pdao = new PersonaDAO();
        List<ProfileDTO> resultado = pdao.buscarPorAptitud(aptitud);
        return resultado;
    }
}
