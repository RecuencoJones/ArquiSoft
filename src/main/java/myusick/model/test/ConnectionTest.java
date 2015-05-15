package myusick.model.test;

import myusick.controller.MyusickService;
import myusick.controller.dto.GroupDTO;
import myusick.controller.dto.PublicationsDTO;
import myusick.controller.dto.RegisterDTO;
import myusick.controller.dto.SkillTagDTO;

/**
 * Created by Sandra on 30/03/2015.
 */
public class ConnectionTest {
    public static void main (String [] args){
        MyusickService m = new MyusickService();
        System.out.println("register user" +m.registerUser(new RegisterDTO("prueba5","apellido","34324","ciudad","pais","2342352","e@mail.com","password","password")));
        System.out.println("register group "+m.registerGroup(new GroupDTO(2, "nombre6", "46346435")));
        System.out.println("profile data "+m.getProfileData(1));
        System.out.println("login data "+m.getLoginData("e@mail.com", "password"));
        System.out.println("registrar tag "+m.registrarTag(new SkillTagDTO("unaTag6", 6)));
        System.out.println("registrar skill "+m.registrarSkill(new SkillTagDTO("unaAptitud3", 1)));
        System.out.println("insertar publicacion "+m.insertarPublicacion(new PublicationsDTO(2, "contenido2", 3325324)));
        System.out.println("follow "+m.follow(2, 1));
        System.out.println("get followers id "+m.getFollowersIds(1));
        System.out.println("unfollow "+m.unfollow(2, 1));
        System.out.println("is follow "+m.isfollow(2, 1));
        System.out.println("get post "+m.getPost(3));
        System.out.println("solicitud insercion grupo "+m.solicitudInsercionGrupo(7, 5));
        System.out.println("pendientes aceptar "+m.pendientesDeAceptar(5));
        System.out.println("responder peticion "+m.responderPeticion(7, 5, true));
        System.out.println("eliminar de grupo "+m.eliminarDeGrupo(7, 5));
        System.out.println("ultimas publicaciones "+m.ultimasPublicaciones(1));
        System.out.println("buscar por tag "+m.buscarPorTag("unaTag"));
        System.out.println("buscar por aptitud "+ m.buscarPorAptitud("unaAptitud"));
        System.out.println("buscar persona por nombre "+m.buscarPersonaPorNombre("prueba"));
        System.out.println("buscar grupo por nombre "+m.buscarGrupoPorNombre("Los Solfamidas"));
    }
}
