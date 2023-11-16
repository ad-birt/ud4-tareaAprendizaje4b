package ejercicios;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import entidades.Student;
import entidades.Tuition;
import entidades.University;

public class Ud4TareaAprendizaje4bEjercicio4 {

	/**
	 * 4. OneToMany bidireccional entre entidades Student y University
	 * Borra una Universidad y sus estudiantes.
	 */
	public static void main(String[] args) {

		// crea sessionFactory y session
		StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
			    .configure( "hibernate.cfg.xml" )
			    .build();

		Metadata metadata = new MetadataSources( standardRegistry )
			    .addAnnotatedClass( Student.class )
			    .addAnnotatedClass( Tuition.class )
			    .addAnnotatedClass( University.class )
			    .getMetadataBuilder()
			    .build();

		SessionFactory sessionFactory = metadata.getSessionFactoryBuilder()
			    .build();    
		
		Session session = sessionFactory.openSession();
		
		try {			
			// crea un objeto Student
			System.out.println("Borrando una universidad sin borrar sus estudiantes");
			
			int university_id = 2;
			
			University tempUniversity = session.get(University.class, university_id);
			// comienza la transacción
			session.beginTransaction();
		
			// borra la universidad pero no el estudiante. "on delete set null" en BD
			session.remove(tempUniversity);
			
			// hace commit de la transaccion
			session.getTransaction().commit();
					
			System.out.println("Hecho!");
		}
		catch ( Exception e ) {
			// rollback ante alguna excepci n
			System.out.println("Realizando Rollback");
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		finally {
			session.close();
			sessionFactory.close();
		}
	}
	
}




