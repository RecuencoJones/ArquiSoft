package myusick.model.test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import myusick.model.entities.*;
/**
 * Bases de datos 2 2014. Practica 4.
 * @author Equipo 2
 * Clase que implementa la insercion de datos en las tablas
 * y las consultas correspondientes
 */
public class Test {
	
	public static void main(String[] args) {
		EntityManagerFactory entityManagerFactory = Persistence
				.createEntityManagerFactory("PersistenciaTest");

		EntityManager em = entityManagerFactory.createEntityManager();
		EntityTransaction trans = em.getTransaction();

		trans.begin();

		/* Creamos efectivo */

		/*Efectivo efectivo = new Efectivo();
		efectivo.setIdOperacion(1234);
		//Aqui es una relaci�n. Asocias una cuenta, no un numero.
		efectivo.setNumCuenta("12345123451234512345");*/

		/**
		 * Llenamos Hash
		 */
		//cliente.getHash().add(cuenta); etc etc
		
		/**
		 * Les damos persistencia
		 */
		/*em.persist(dir); 		em.persist(dir2); 			em.persist(dir3);
		em.persist(dir5); 		em.persist(dir6);			em.persist(dir7);
		em.persist(dir8);		em.persist(dir9);			em.persist(dir10);
		em.persist(dir11);		em.persist(dir12);
		em.persist(oficina); 	em.persist(oficina2); 		em.persist(oficina3);
		em.persist(oficina4);	em.persist(oficina5);		em.persist(oficina6);
		em.persist(cliente); 	em.persist(cliente2); 		em.persist(cliente3); 
		em.persist(cliente4); 	em.persist(cliente5); 		em.persist(cliente6); 
		em.persist(cuenta); 	em.persist(cuentaOrigen); 	em.persist(cuenta3); 
		em.persist(cuenta4);	em.persist(cuenta5);		em.persist(cuenta6);
		em.persist(cuenta7);
		em.persist(efectivo); 	em.persist(efectivo2);		em.persist(efectivo3);
		em.persist(ahorro);		em.persist(ahorro2);		em.persist(ahorro3);
		em.persist(ahorro4);
		em.persist(operacion);	em.persist(operacion2);		em.persist(operacion3);
		em.persist(corriente); 	em.persist(corriente2); 	em.persist(corriente3);
		em.persist(corriente4);
		em.persist(transferencia); em.persist(transferencia2); em.persist(transferencia3);
		trans.commit();*/
		
		/**
		 * Inicio de las consultas 
		 */
		
		/*------CONSULTA 1------*/
		/*sql nativo*/
		String consulta1="select c,count(t) cnt from titular t JOIN cliente c WHERE cnt>1 GROUP BY t.dni";
		//Query q1_1 = em.createNativeQuery(consulta1,Cliente.class);
		//Cliente[] resul1=(Cliente[])q1_1.getResultList().toArray(new Cliente[0]);
		
		/*JPQL*/
		Query q1_2 = em.createQuery("SELECT t,count(t) cnt FROM titular t WHERE cnt>1 GROUP BY t.dni;");
		
		/*------CONSULTA 2------*/
		/*sql nativo*/
		String consulta2="select c,count(c) cnt2 from cliente c WHERE cnt2>1 GROUP BY c.direccionCliente";
		//Query q2_1 = em.createNativeQuery(consulta2,Cliente.class);
		//Cliente[] resul2=(Cliente[])q2_1.getResultList().toArray(new Cliente[0]);
		
		/*JPQL*/
		Query q2_2 = em.createQuery("SELECT c,count(c) cnt2 FROM cliente c WHERE cnt2>1 GROUP BY c.direccionCliente;");
		
		/*------CONSULTA 3------*/
		int min = 400; int max = 1000;
		/*sql nativo*/
		String consulta3="SELECT c FROM cuenta c WHERE c.saldo BETWEEN " + min +" AND " + max +";";
		//Query q3_1 = em.createNativeQuery(consulta3,Cuenta.class);
		//Cuenta[] resul3=(Cuenta[])q3_1.getResultList().toArray(new Cuenta[0]);
		
		/*JPQL*/
		Query q3_2 = em.createQuery("SELECT c FROM cuenta c WHERE c.saldo BETWEEN :min AND :max;")
				.setParameter("min", min).setParameter("max", max);
		
		/*------CONSULTA 4------*/
		/*sql nativo*/
		String consulta4="SELECT c FROM corriente c JOIN oficina o WHERE o.estadoOficina = 0";
		//Query q4_1 = em.createNativeQuery(consulta4,Corriente.class);
		//Corriente[] resul4=(Corriente[])q4_1.getResultList().toArray(new Corriente[0]);
		
		/*JPQL*/
		Query q4_2 = em.createQuery("SELECT c FROM corriente c JOIN oficina o WHERE o.estadoOficina = 0;");
		
		/*------CONSULTA 5------*/
		/*sql nativo*/
		String consulta5="SELECT cu FROM cuenta cu JOIN titular ti, cliente cl WHERE ti.dni = cl.dni " +	
					"AND cl.estadoCliente = 0;";
		//Query q5_1 = em.createNativeQuery(consulta5,Cuenta.class);
		//Cuenta[] resul5=(Cuenta[])q5_1.getResultList().toArray(new Cuenta[0]);
		
		/*JPQL*/
		Query q5_2 = em.createQuery("SELECT cu FROM cuenta cu JOIN titular ti, cliente cl WHERE ti.dni = cl.dni "
				+ "AND cl.estadoCliente = 0;");
		
		em.close();
		entityManagerFactory.close();

	}
}