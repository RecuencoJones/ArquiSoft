package myusick.model.test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import myusick.model.entities.*;

import java.sql.Date;

/**
 * Arquitectura Software
 * @author Guillermo Perez Garcia
 * Clase que implementa la insercion de datos en las tablas
 */
public class Test {

	public static void main(String[] args) {
		EntityManagerFactory entityManagerFactory = Persistence
				.createEntityManagerFactory("PersistenciaTest");

       /* try {
            Class c = Class.forName("org.hibernate.ejb.HibernatePersistence");
        } catch (ClassNotFoundException e) { e.printStackTrace(); }*/
        
        EntityManager em = entityManagerFactory.createEntityManager();
		EntityTransaction trans = em.getTransaction();
        
        /*Configuration configuration = new Configuration();
        configuration.addClass(myusick.model.entities.Aptitud.class);
        configuration.addClass(myusick.model.entities.Grupo.class);
        configuration.addClass(myusick.model.entities.Persona.class);
        configuration.addClass(myusick.model.entities.Publicacion.class);
        configuration.addClass(myusick.model.entities.Publicante.class);
        configuration.addClass(myusick.model.entities.Tag.class);*/


		trans.begin();
        /*--------- ENTIDADES ---------*/
       /* Aptitud ap1 = new Aptitud();
        ap1.setIdaptitud(1); ap1.setNombre("bajista");
        Aptitud ap2 = new Aptitud();
        ap2.setIdaptitud(2); ap2.setNombre("vocalista");
        Aptitud ap3 = new Aptitud();
        ap3.setIdaptitud(3); ap3.setNombre("bateria");
        Aptitud ap4 = new Aptitud();
        ap4.setIdaptitud(4); ap4.setNombre("guitarrista");

        Tag tag1 = new Tag();
        tag1.setIdtag(1); tag1.setNombreTag("zaragoza");
        Tag tag2 = new Tag();
        tag2.setIdtag(2); tag2.setNombreTag("rock");
        Tag tag3 = new Tag();
        tag3.setIdtag(3); tag3.setNombreTag("folk-rock");
        Tag tag4 = new Tag();
        tag4.setIdtag(4); tag4.setNombreTag("spain");

        //true=grupo, false=solista
        Publicante pub1 = new Publicante();
        pub1.setUuid(1); pub1.setTipoPublicante(true);
        Publicante pub2 = new Publicante();
        pub2.setUuid(2); pub2.setTipoPublicante(true);
        Publicante pub3 = new Publicante();
        pub3.setUuid(3); pub3.setTipoPublicante(false);
        Publicante pub4 = new Publicante();
        pub4.setUuid(4); pub4.setTipoPublicante(false);
        Publicante pub5 = new Publicante();
        pub4.setUuid(5); pub4.setTipoPublicante(false);

        Grupo gr1 = new Grupo();
        gr1.setNombre("the beatles"); gr1.setAnyo(new Date(1900));
        gr1.setDescripcion("unos pelopijos que no llegaron a nada"); gr1.setPublicante_uuid(1);
        Grupo gr2 = new Grupo();
        gr2.setNombre("kancer de sida"); gr2.setAnyo(new Date(2003));
        gr2.setDescripcion("grupo de covers zaragozano"); gr2.setPublicante_uuid(2);
        Grupo gr3 = new Grupo();
        gr3.setNombre("los toreros muertos"); gr3.setAnyo(new Date(1984));
        gr3.setDescripcion("los putos amos"); gr3.setPublicante_uuid(3);

        Persona per1 = new Persona();
        per1.setNombre("guille"); per1.setEmail("pers1@unizar.es"); per1.setPublicante_uuid(pub4);
        Persona per2 = new Persona();
        per2.setNombre("recu"); per2.setEmail("pers2@unizar.es"); per2.setPublicante_uuid(pub5);

        *//*--------- RELACIONES ---------*//*

        *//*----- grupo->persona y viceversa -----*//*
		per1.addGrupo(gr1); gr1.addMiembro(per1);
		per2.addGrupo(gr1); gr2.addMiembro(per1);

        *//*----- grupo->tag -----*//*
        gr1.addTag(tag2);
        gr2.addTag(tag2); gr2.addTag(tag1); gr2.addTag(tag4);
        
        *//*----- persona->tag -----*//*
        per1.addTag(tag1); per1.addTag(tag2);
        per2.addTag(tag1); per2.addTag(tag2);

        *//*----- persona->aptitud -----*//*
        per1.addAptitud(ap1);per1.addAptitud(ap3);per1.addAptitud(ap4);
        per2.addAptitud(ap1);per2.addAptitud(ap4);

        *//*aún no hay entidades débiles*//*

        em.merge(tag1);   em.merge(tag2);   em.merge(tag3);   em.merge(tag4);
        em.merge(ap1);    em.merge(ap2);    em.merge(ap3);    em.merge(ap4);
        em.merge(pub1);   em.merge(pub2);   em.merge(pub3);   em.merge(pub4);   em.merge(pub5);
        trans.commit();
        em.merge(per1);   em.merge(per2);
        em.merge(gr1);    em.merge(gr2);    em.merge(gr3);
        trans.commit();
		*//**
		 * Inicio de las consultas 
		 *//*
		
		*//*------CONSULTA 1------*//**//*
		*//**//*sql nativo*//**//*
		String consulta1="select c,count(t) cnt from titular t JOIN cliente c WHERE cnt>1 GROUP BY t.dni";
		//Query q1_1 = em.createNativeQuery(consulta1,Cliente.class);
		//Cliente[] resul1=(Cliente[])q1_1.getResultList().toArray(new Cliente[0]);
		
		*//**//*JPQL*//**//*
		Query q1_2 = em.createQuery("SELECT t,count(t) cnt FROM titular t WHERE cnt>1 GROUP BY t.dni;");
		
		*//**//*------CONSULTA 2------*//**//*
		*//**//*sql nativo*//**//*
		String consulta2="select c,count(c) cnt2 from cliente c WHERE cnt2>1 GROUP BY c.direccionCliente";
		//Query q2_1 = em.createNativeQuery(consulta2,Cliente.class);
		//Cliente[] resul2=(Cliente[])q2_1.getResultList().toArray(new Cliente[0]);
		
		*//**//*JPQL*//**//*
		Query q2_2 = em.createQuery("SELECT c,count(c) cnt2 FROM cliente c WHERE cnt2>1 GROUP BY c.direccionCliente;");
		
		*//**//*------CONSULTA 3------*//**//*
		int min = 400; int max = 1000;
		*//**//*sql nativo*//**//*
		String consulta3="SELECT c FROM cuenta c WHERE c.saldo BETWEEN " + min +" AND " + max +";";
		//Query q3_1 = em.createNativeQuery(consulta3,Cuenta.class);
		//Cuenta[] resul3=(Cuenta[])q3_1.getResultList().toArray(new Cuenta[0]);
		
		*//**//*JPQL*//**//*
		Query q3_2 = em.createQuery("SELECT c FROM cuenta c WHERE c.saldo BETWEEN :min AND :max;")
				.setParameter("min", min).setParameter("max", max);
		
		*//**//*------CONSULTA 4------*//**//*
		*//**//*sql nativo*//**//*
		String consulta4="SELECT c FROM corriente c JOIN oficina o WHERE o.estadoOficina = 0";
		//Query q4_1 = em.createNativeQuery(consulta4,Corriente.class);
		//Corriente[] resul4=(Corriente[])q4_1.getResultList().toArray(new Corriente[0]);
		
		*//**//*JPQL*//**//*
		Query q4_2 = em.createQuery("SELECT c FROM corriente c JOIN oficina o WHERE o.estadoOficina = 0;");
		
		*//**//*------CONSULTA 5------*//**//*
		*//**//*sql nativo*//**//*
		String consulta5="SELECT cu FROM cuenta cu JOIN titular ti, cliente cl WHERE ti.dni = cl.dni " +	
					"AND cl.estadoCliente = 0;";
		//Query q5_1 = em.createNativeQuery(consulta5,Cuenta.class);
		//Cuenta[] resul5=(Cuenta[])q5_1.getResultList().toArray(new Cuenta[0]);
		
		*//**//*JPQL*//**//*
		Query q5_2 = em.createQuery("SELECT cu FROM cuenta cu JOIN titular ti, cliente cl WHERE ti.dni = cl.dni "
				+ "AND cl.estadoCliente = 0;");*//*
		
		em.close();
		entityManagerFactory.close();*/

	}
}