package ejercicios;

import java.time.LocalDate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import entidades.Address;
import entidades.Student;
import entidades.Tuition;
import entidades.University;

public class Ud4TareaAprendizaje4bEjercicio1 {

	/**
	 * 1. OneToMany bidireccional entre entidades Student y University
	 */
	public static void main(String[] args) {

		// crea sessionFactory y session
		StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
			    .configure( "hibernate.cfg.xml" )
			    .build();

		Metadata metadata = new MetadataSources( standardRegistry )
			    .addAnnotatedClass( Student.class )
			    .addAnnotatedClass( Tuition.class )
			    .addAnnotatedClass( University.class)
			    .getMetadataBuilder()
			    .build();

		SessionFactory sessionFactory = metadata.getSessionFactoryBuilder()
			    .build();    
		
		Session session = sessionFactory.openSession();
		
		
		try {			
			// crea un objeto Student y University
			System.out.println("Creando un nuevo objeto Student y una Universidad");
		
			Student student = createStudent();
			University university = createUniversity();
					
			university.getStudents().add(student);
			student.setUniversity(university); //bidireccional
					
			// comienza la transacci n
			session.beginTransaction();
			
			// guarda el objeto University
			System.out.println("Guardando la universidad y en cascada el estudiante");
			session.persist(university);
			
			// hace commit de la transaccion
			session.getTransaction().commit();
			
			// prueba para acceder a la entidad source desde de la entidad target
			session.beginTransaction();
			Student dbStudent = (Student)session.get(Student.class, university.getStudents().get(0).getId());
			System.out.println(dbStudent.getUniversity().getName());
						
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
	private static Student createStudent() {
		Student tempStudent = new Student();
		Address tempAddress = new Address();
		
		tempStudent.setFirstName("Iñaki");
		tempStudent.setLastName("Laspiur");
		tempStudent.setEmail("ilaspiur@birt.eus");
		tempStudent.getPhones().add("687123456");
		tempStudent.getPhones().add("699212345");
		tempStudent.setBirthdate(LocalDate.parse("1985-04-04"));
		tempAddress.setAddressLine1("Burdin kale 8");
		tempAddress.setAddressLine2("1A");
		tempAddress.setCity("Zarautz");
		tempAddress.setZipCode("20080");
		tempStudent.setAddress(tempAddress);
		return tempStudent;		
	}
	
	private static University createUniversity() {
		University tempUniversity = new University();
		Address uniAddress = new Address();
		
		tempUniversity.setName("EHU");
		uniAddress.setAddressLine1("Araba Kalea");
		uniAddress.setAddressLine2("5");
		uniAddress.setCity("Gasteiz");
		uniAddress.setZipCode("01155");
		tempUniversity.setAddress(uniAddress);
		return tempUniversity;		
	}
}










