package myusick.controller;

import myusick.controller.dto.*;
import myusick.model.dao.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Myusick. Arquitectura Software 2015
 *
 * @author David Recuenco (648701)
 * @author Guillermo Perez (610382)
 * @author Sandra Campos (629928)
 *
 * Servicio que interactua entre los servicios web y la
 * base de datos.
 */
public class MyusickService {

    /**
     * Metodo constructor de la clase
     */
    public MyusickService() {
    }

    /**
     * Inserta una persona en la base de datos
     *
     * @param rd informacion de una persona concreta
     * @return id de la persona, insertado en el objeto
     * RegisterDTO, -1 en caso de error
     */
    public int registerUser(RegisterDTO rd) {
        PersonaDAO pdao = new PersonaDAO();
        int res = pdao.registerUser(rd);
        return res;
    }

    /**
     * Inserta un grupo en la base de datos
     *
     * @param gd informacion del grupo que se quiere insertar
     * @return uuid del grupo insertado, -1 en caso contrario
     */
    public int registerGroup(GroupDTO gd) {
        GrupoDAO gdao = new GrupoDAO();
        int res = gdao.registerGroup(gd);
        return res;
    }

    /**
     * Obtiene la informacion de perfil de un publicante concreto
     * @param uuid id del publicante
     * @return informacion de perfil del publicante
     */
    public ProfileDTO getProfileData(int uuid) {
        ProfileDTO profile = new ProfileDTO();
        GrupoDAO gdao = new GrupoDAO();
        PersonaDAO pdao = new PersonaDAO();
        TagDAO tdao = new TagDAO();
        AptitudDAO adao = new AptitudDAO();
        PublicacionDAO pubdao = new PublicacionDAO();
        if (gdao.esUnGrupo(uuid)) {
        /* Cogemos los datos necesarios de la entidad grupo*/
            ProfileDTO g = gdao.getDataProfile(uuid);
            profile.setType(true);
            profile.setName(g.getName());
            profile.setYear(g.getYear());
            profile.setAvatar(g.getAvatar());
            profile.setDescription(g.getDescription());
        /* Cogemos las tags que tiene almacenadas, si las hay*/
            profile.setTags(tdao.getTagsByGrupo(uuid));
        /* Si es un grupo, almacenamos los miembros del mismo */
            profile.setMembers(gdao.getMembersGroup(uuid));
        /* Almacenamos las publicaciones que tenga */
            profile.setPublications(pubdao.getPublicacionesById(uuid));
        } else if (pdao.esUnaPersona(uuid)) {
        /* Cogemos los datos necesarios de la entidad persona*/
            ProfileDTO p = pdao.getDataProfile(uuid);
            profile.setType(false);
            profile.setName(p.getName());
            profile.setAvatar(p.getAvatar());
            profile.setDescription(p.getDescription());
        /* Cogemos las aptitudes que tiene almacenadas, si las hay*/
            profile.setSkills(adao.getAptitudesByPersona(uuid));
        /* Cogemos las tags que tiene almacenadas, si las hay*/
            profile.setTags(tdao.getTagsByPersona(uuid));
        /* Almacenamos si pertenece a algun grupo y a cuales en caso afirmativo */
            profile.setGroups(pdao.getGroupsByMember(uuid));
        /* Almacenamos las publicaciones que tenga */
            profile.setPublications(pubdao.getPublicacionesById(uuid));
        } else {
            /* El usuario no existe */
            System.out.printf("No existe el usuario\n");
            profile = null;
        }

        return profile;
    }

    /**
     * Proporciona la informacion de login de una persona concreta
     *
     * @param email    email para acceder a la red social del publicante
     * @param password contrasena para acceder a la red social del publicante
     * @return informacion de login del publicante concreto
     */
    public LoginDTO getLoginData(String email, String password) {
        PersonaDAO pdao = new PersonaDAO();
        LoginDTO res = pdao.getLoginData(email, password);
        return res;
    }

    /**
     * Registra un nuevo tag en la base de datos
     * @param td informacion del tag a insertar
     * @return cierto en caso de que la insercion sea correcta, falso
     * en caso contrario
     */
    public boolean registrarTag(SkillTagDTO td) {
        TagDAO tdao = new TagDAO();
        boolean res = tdao.registrarTag(td);
        return res;
    }

    /**
     * Inserta una nueva aptitud en la base de datos
     * @param td informacion de la aptitud que se quiere insertar
     * @return cierto en caso de que la insercion sea correcta, falso
     * en caso contrario.
     */
    public boolean registrarSkill(SkillTagDTO td) {
        AptitudDAO adao = new AptitudDAO();
        boolean res = adao.registrarAptitud(td);
        return res;
    }

    /**
     * Inserta una publicacion en la base de datos
     * @param pdto informacion de la publicacion que se quiere insertar.
     * @return id de la publicacion generado automaticamente por la base de datos,
     * -1 en caso de que exista un error con la base de datos.
     */
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
    public ArrayList<Integer> getFollowersIds(int uuid) {
        SeguirDAO sdao = new SeguirDAO();
        ArrayList<Integer> res = sdao.getFollowersIds(uuid);
        return res;
    }

    /**
     * Extrae una lista con la informacion de los publicantes que siguen
     * a un publicante concreto pasado por parametro
     * @param uuid id del publicante del que se quiere saber la informacion
     * @return lista con la informacion de cada publicante que sigue al publicante
     * pasado por parametro
     */
    public ArrayList<PublisherDTO> getFollowers(int uuid){
        SeguirDAO sdao = new SeguirDAO();
        ArrayList<PublisherDTO> followers = sdao.getFollowers(uuid);
        return followers;
    }

    /**
     * Extrae una lista con toda la informacion de aquellos publicantes
     * a los que el publicante pasado por parametro sigue.
     * @param userid id del publicante del que se desea obtener la informacion
     * @return lista con la informacion de aquellos publicantes
     * a los que el publicante pasado por parametro sigue
     */
    public ArrayList<PublisherDTO> getFollowing(int userid) {
        SeguirDAO sdao = new SeguirDAO();
        ArrayList<PublisherDTO> following = sdao.getFollowing(userid);
        return following;
    }
    /**
     * Establece una relacion entre dos publicantes
     * @param seguidor id de la persona que quiere seguir a alguien
     * @param seguido id de la persona que va a ser seguida
     * @return cierto en caso de que la operacion resulte con exito,
     * falso en caso contrario
     */
    public boolean follow(int seguidor, int seguido) {
        SeguirDAO sdao = new SeguirDAO();
        boolean res = sdao.follow(seguidor, seguido);
        return res;
    }

    /**
     * Elimina la relacion existente entre dos persona
     * @param seguidor id de la persona que quiere seguir a alguien
     * @param seguido id de la persona que va a ser seguida
     * @return cierto en caso de que la operacion resulte con exito,
     * falso en caso contrario
     */
    public boolean unfollow(int seguidor, int seguido) {
        SeguirDAO sdao = new SeguirDAO();
        boolean res = sdao.unfollow(seguidor, seguido);
        return res;
    }

    /**
     * Devuelve un objeto de tipo booleano que indicara si los dos
     * publicantes tienen una relacion en la base de datos
     * @param seguidor id de la persona que quiere seguir a alguien
     * @param seguido id de la persona que va a ser seguida
     * @return cierto en caso de que seguidor y seguido tengan una relacion
     * en la base de datos
     */
    public boolean isfollow(int seguidor, int seguido) {
        SeguirDAO sdao = new SeguirDAO();
        boolean res = sdao.isfollow(seguidor, seguido);
        return res;
    }

    /**
     * Extrae una publicacion a partir de un id concreto
     * @param id id de la publicacion
     * @return informacion de la publicacion concreta
     */
    public PostDTO getPost(int id) {
        PublicacionDAO pdao = new PublicacionDAO();
        PostDTO res = pdao.getPostById(id);
        return res;
    }

    /**
     * Presenta una solicitud para ingresar en un grupo concreto
     *
     * @param persona id de la persona que solicita entrar
     * @param grupo   id del grupo al que se solicita entrar
     * @return cierto en caso de que la peticion se haya realizado
     * correctamente, falso en caso contrario
     */
    public boolean solicitudInsercionGrupo(int persona, int grupo) {
        GrupoDAO gdao = new GrupoDAO();
        boolean resultado = gdao.solicitudInsercionGrupo(persona, grupo);
        return resultado;
    }

    /**
     * Responder a la peticion de insercion a la persona que lo solicito
     *
     * @param persona id de la persona que solicitaba entrar
     * @param grupo   id del grupo al que se solicitaba entrar
     * @param decision decision que se va a comunicar
     * @return cierto en caso de que se haya respondido correctamente, falso
     * en caso contrario
     */
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

    /**
     * Elimina a una persona concreta de un grupo al que pertenece
     *
     * @param persona id de la persona
     * @param grupo   id del grupo
     * @return cierto en caso de que la eliminacion sea correcta, falso
     * en caso contrario
     */
    public boolean eliminarDeGrupo(int persona, int grupo) {
        GrupoDAO gdao = new GrupoDAO();
        boolean resultado = gdao.eliminarDeGrupo(persona, grupo);
        return resultado;
    }

    /**
     * Extrae una lista de publicantes que han presentado una solicitud de insercion
     * a un grupo concreto y que aun no se han contestado.
     *
     * @param grupo id del grupo al que se solicita entrar
     * @return lista de publicantes pendientes de respuesta
     */
    public List<PublisherDTO> pendientesDeAceptar(int grupo) {
        GrupoDAO gdao = new GrupoDAO();
        List<PublisherDTO> resultado = gdao.pendientesDeAceptar(grupo);
        return resultado;
    }

    /**
     * Extrae las 10 ultimas publicaciones de todos aquellos
     * seguidores que tenga un publicante concreto, incluidos los suyos
     * propios.
     * @param seguidor id del publicante del que se quieren ver las
     *                 publicaciones de sus seguidores
     * @return lista con las diez ultimas publicaciones, tanto de sus
     * seguidores como de el mismo
     */
    public List<PostDTO> ultimasPublicaciones(int seguidor) {
        PublicacionDAO pdao = new PublicacionDAO();
        List<PostDTO> resultado = pdao.ultimasPublicaciones(seguidor);
        return resultado;
    }

    /**
     * Busca aquellos perfiles que posean relaciones con tags
     * cuyo nombre coincida con el que se inserta como parametro
     *
     * @param tag nombre del tag
     * @return lista de los perfiles con coincidencias
     */
    public List<ShortProfileDTO> buscarPorTag(String tag) {
        /* Primero intentamos ver si es una persona */
        PersonaDAO pdao = new PersonaDAO();
        List<ShortProfileDTO> resultado = pdao.buscarPorTag(tag);
            /* Buscamos si hay coincidencias por grupo */
        GrupoDAO gdao = new GrupoDAO();
        resultado.addAll(gdao.buscarPorTag(tag));
            /*
             * Tanto en caso de que se haya encontrado persona, grupo o no se hayan
             * encontrado resultados, se devuelve la lista igualmente (completa o null)
             */
        return resultado;
    }

    /**
     * Busca aquellos perfiles que posean relaciones con aptitudes
     * cuyo nombre coincida con el que se inserta como parametro
     *
     * @param aptitud nombre de la aptitud
     * @return lista de los perfiles con coincidencias
     */
    public List<ShortProfileDTO> buscarPorAptitud(String aptitud) {
        PersonaDAO pdao = new PersonaDAO();
        List<ShortProfileDTO> resultado = pdao.buscarPorAptitud(aptitud);
        return resultado;
    }

    /**
     * Extrae todos aquellos perfiles cuyo nombre coincida total
     * o parcialmente con alguno de los perfiles de la base de datos
     *
     * @param term cadena con la que se quiere coincidir el nombre
     *               de algun perfil de la base de datos
     * @return lista con los perfiles en los que se encuentren coincidencias
     */
    public List<ShortProfileDTO> buscarPersonaPorNombre(String term) {
        PersonaDAO pdao = new PersonaDAO();
        List<ShortProfileDTO> resultado = pdao.buscarPorNombre(term);
        return resultado;
    }

    /**
     * Extrae todos aquellos perfiles cuyo nombre coincida total
     * o parcialmente con alguno de los perfiles de la base de datos
     *
     * @param term cadena con la que se quiere coincidir el nombre
     *               de algun perfil de la base de datos
     * @return lista con los perfiles en los que se encuentren coincidencias
     */
    public List<ShortProfileDTO> buscarGrupoPorNombre(String term) {
        GrupoDAO gdao = new GrupoDAO();
        List<ShortProfileDTO> resultado = gdao.buscarPorNombre(term);
        return resultado;
    }

    /**
     * Cambia el nombre de una aptitud concreta
     * @param id id de la aptitud a actualizar
     * @param nuevo nuevo nombre de la aptitud
     * @return cierto en caso de que la actualizacion
     * sea correcta, falso en caso contrario
     */
    public boolean editarAptitud(int id, String nuevo){
        return new AptitudDAO().editarAptitud(id,nuevo);
    }

    /**
     * Borra una aptitud concreta de la base de datos
     * @param id id de la aptitud
     * @return cierto en caso de que la aptitud sea borrada
     * con exito, falso en caso contrario
     */
    public boolean borrarAptitud(int id){
        return new AptitudDAO().borrarAptitud(id);
    }

    /**
     * Actualiza el nombre del grupo
     * @param UUID   id del grupo
     * @param nombre nuevo nombre del grupo
     * @return cierto en caso de que la actualizacion sea correcta,
     * falso en caso contrario
     */
    public boolean setNombre(int UUID, String nombre) {
       return new GrupoDAO().setNombre(UUID, nombre);
    }

    /**
     * Actualiza el anyo de fundacion del grupo
     *
     * @param UUID id del grupo
     * @param nac  nueva fecha de fundacion del grupo
     * @return cierto en caso de que la actualizacion sea correcta,
     * falso en caso contrario
     */
    public boolean setAnyo(int UUID, int nac){
        return new GrupoDAO().setAnyo(UUID, nac);
    }

    /**
     * Actualiza la descripcion del grupo
     *
     * @param UUID  id del grupo
     * @param descr nueva descripcion del grupo
     * @return cierto en caso de que la actualizacion sea correcta,
     * falso en caso contrario
     */
    public boolean setDescripcion(int UUID, String descr){
        return new GrupoDAO().setDescripcion(UUID, descr);
    }

    /**
     * Actualiza la imagen de avatar del grupo
     *
     * @param UUID id del grupo
     * @param url  nueva url de la imagen de avatar del grupo
     * @return cierto en caso de que la actualizacion sea correcta,
     * falso en caso contrario
     */
    public boolean setAvatar(int UUID, String url){
        return new GrupoDAO().setAvatar(UUID, url);
    }

    /**
     * Borra un grupo de la base de datos
     *
     * @param uuid id del grupo
     * @return cierto en caso de que se haya borrado correctamente,
     * falso en caso contrario
     */
    public boolean borrarGrupo(int uuid){
        return new GrupoDAO().borrarGrupo(uuid);
    }

    /**
     * Actualiza el nombre de una persona concreta
     *
     * @param UUID   id de la persona
     * @param nombre nuevo nombre de la persona
     * @return cierto en caso de que la actualizacion sea correcta,
     * falso en caso contrario.
     */
    public boolean setNombrePersona(int UUID, String nombre){
        return new PersonaDAO().setNombre(UUID, nombre);
    }

    /**
     * Actualiza los apellidos de la persona
     *
     * @param UUID id de la persona
     * @param ap   apellidos de la persona
     * @return cierto en caso de que la actualizacion sea correcta,
     * falso en caso contrario.
     */
    public boolean setApellidos(int UUID, String ap){
       return new PersonaDAO().setApellidos(UUID, ap);
    }

    /**
     * Actualiza el avatar de una persona concreta
     *
     * @param UUID id de la persona
     * @param url  nueva url de la imagen del avatar
     * @return cierto en caso de que la actualizacion sea correcta,
     * falso en caso contrario.
     */
    public boolean setAvatarPersona(int UUID, String url){
        return new PersonaDAO().setAvatar(UUID, url);
    }

    /**
     * Actualiza el email de una persona concreta
     *
     * @param UUID id de la persona
     * @param mail nuevo email de la persona
     * @return cierto en caso de que la actualizacion sea correcta,
     * falso en caso contrario.
     */
    public boolean setEmail(int UUID, String mail){
        return new PersonaDAO().setEmail(UUID, mail);
    }

    /**
     * Actualiza la contrasena de una persona concreta
     *
     * @param UUID id de la persona
     * @param pass nueva contrasena de la persona
     * @return cierto en caso de que la actualizacion sea correcta,
     * falso en caso contrario.
     */
    public boolean setPassword(int UUID, String pass){
       return new PersonaDAO().setPassword(UUID, pass);
    }

    /**
     * Actualiza la fecha de nacimiento de una persona concreta
     *
     * @param UUID id de la persona
     * @param nac  nueva fecha de nacimiento de la persona
     * @return cierto en caso de que la actualizacion sea correcta,
     * falso en caso contrario.
     */
    public boolean setNacimiento(int UUID, int nac){
        return new PersonaDAO().setNacimiento(UUID, nac);
    }

    /**
     * Actualiza la ciudad de una persona concreta
     *
     * @param UUID id de la persona
     * @param city nueva ciudad de la persona
     * @return cierto en caso de que la actualizacion sea correcta,
     * falso en caso contrario.
     */
    public boolean setCiudad(int UUID, String city){
        return new PersonaDAO().setCiudad(UUID, city);
    }

    /**
     * Actualiza el pais de una persona concreta
     *
     * @param UUID id de la persona
     * @param pais nuevo pais de la persona
     * @return cierto en caso de que la actualizacion sea correcta,
     * falso en caso contrario.
     */
    public boolean setPais(int UUID, String pais){
        return new PersonaDAO().setPais(UUID, pais);
    }

    /**
     * Actualiza el telefono de una persona concreta
     *
     * @param UUID id de la persona
     * @param tel  nuevo telefono de la persona
     * @return cierto en caso de que la actualizacion sea correcta,
     * falso en caso contrario.
     */
    public boolean setTelefono(int UUID, int tel){
        return new PersonaDAO().setTelefono(UUID, tel);
    }

    /**
     * Actualiza la descripcion de una persona concreta
     *
     * @param UUID  id de la persona
     * @param descr nueva descripcion de la persona
     * @return cierto en caso de que la actualizacion sea correcta,
     * falso en caso contrario.
     */
    public boolean setDescripcionPersona(int UUID, String descr){
        return new PersonaDAO().setDescripcion(UUID, descr);
    }

    /**
     * Elimina una persona concreta de la base de datos
     *
     * @param uuid id de la persona
     * @return cierto en caso de que la actualizacion sea correcta,
     * falso en caso contrario.
     */
    public boolean borrarPersona(int uuid){
        return new PersonaDAO().borrarPersona(uuid);
    }

    /**
     * Actualiza los datos de una publicacion conreta
     * @param id id de la publicacion
     * @param fecha fecha de la publicacion
     * @param contenido texto de la publicacion
     * @return cierto en caso de que la actualizacion se realice
     * con exito, falso en caso contrario
     */
    public boolean editarPublicacion(int id, long fecha, String contenido){
        return new PublicacionDAO().editarPublicacion(id, fecha, contenido);
    }

    /**
     * Elimina una publicacion concreta de la base de datos
     * @param id id de la publicacion
     * @return cierto en caso de que la eliminacion se realice con exito, falso en caso contrario
     */
    public boolean borrarPublicacion(int id){
        return new PublicacionDAO().borrarPublicacion(id);
    }
    /**
     * Borra todas aquellas relaciones que tenga un publicante concreto
     * @param uuid id del publicante
     * @return cierto en caso de que la operacion resulte con exito,
     * falso en caso contrario
     */
    public boolean eliminarSeguidorySeguido(int uuid){
        return new SeguirDAO().eliminarSeguidorySeguido(uuid);
    }

    /**
     * Modifica el nombre de una tag concreta
     * @param id id de la tag a modificar
     * @param nuevo nuevo nombre que va a tener
     * @return cierto en caso de que la actualizacion haya
     * sido correcta, falso en caso contrario.
     */
    public boolean editarTag(int id, String nuevo){
        return new TagDAO().editarTag(id, nuevo);
    }

    /**
     * Borra un tag concreto de la base de datos
     * @param id id del tag que se desea borrar
     * @return cierto en caso de que el borrado sea correcto, falso
     * en caso contrario.
     */
    public boolean borrarTag(int id){
        return new TagDAO().borrarTag(id);
    }

    /**
     * Borra todas aquellas publicaciones que haya escrito
     * un publicante concreto
     * @param uuid id del publicante
     * @return cierto en caso de que la eliminacion se realice
     * con exito, falso en caso contrario
     */
    public boolean borrarPublicaciones(int uuid){
        return new PublicacionDAO().borrarPublicaciones(uuid);
    }
}