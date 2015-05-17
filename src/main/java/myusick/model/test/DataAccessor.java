package myusick.model.test;

/**
 * Created by Sandra on 25/03/2015.
 */
public class DataAccessor {
//    public static void anadirTagAGrupo(SessionFactory sessionFactory, Grupo grupo, Tag tag){
//        Session session = sessionFactory.openSession();
//        session.beginTransaction();
//
//        grupo.anadirTag(tag);
//        session.saveOrUpdate(grupo);
//
//        session.getTransaction().commit();
//        session.close();
//    }
//
//    public static void anadirTagAPersona(SessionFactory sessionFactory, Persona per, Tag tag){
//        Session session = sessionFactory.openSession();
//        session.beginTransaction();
//
//        per.anadirTag(tag);
//        session.saveOrUpdate(per);
//
//        session.getTransaction().commit();
//        session.close();
//    }
//
//    public static void anadirAptitudAPersona(SessionFactory sessionFactory, Persona per, Aptitud aptitud){
//        Session session = sessionFactory.openSession();
//        session.beginTransaction();
//
//        per.anadirAptitud(aptitud);
//        session.saveOrUpdate(per);
//
//        session.getTransaction().commit();
//        session.close();
//    }
//
//    public static void anadirPublicacionAPublicante(SessionFactory sessionFactory, Publicante publicante, Publicacion publicacion) {
//        Session session = sessionFactory.openSession();
//        session.beginTransaction();
//
//        publicacion.setPublicante(publicante);
//        session.save(publicacion);
//
//        session.getTransaction().commit();
//        session.close();
//    }
//
//    public static void anadirPersonaAGrupo(SessionFactory sessionFactory, Persona per, Grupo grupo){
//        Session session = sessionFactory.openSession();
//        session.beginTransaction();
//
//        per.anadirGrupo(grupo);
//        session.saveOrUpdate(per);
//
//        session.getTransaction().commit();
//        session.close();
//    }
//
//    public static void crearGrupo(SessionFactory sessionFactory, Grupo grupo, Publicante pub){
//        Session session = sessionFactory.openSession();
//        session.beginTransaction();
//
//        session.persist(pub);
//        grupo.setId(pub.getUuid());
//        grupo.setPublicante(pub);
//        session.save(grupo);
//
//        session.getTransaction().commit();
//        session.close();
//
//    }
//
//    public static void crearPersona(SessionFactory sessionFactory, Persona per, Publicante pub){
//        Session session = sessionFactory.openSession();
//        session.beginTransaction();
//
//        session.persist(pub);
//        per.setPublicante_uuid(pub.getUuid());
//        per.setPublicante(pub);
//        session.save(per);
//
//        session.getTransaction().commit();
//        session.close();
//    }
}
