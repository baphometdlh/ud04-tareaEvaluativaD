package ejercicios;


import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import entidades.*;


public class Ud04TareaEvaluativaD {

	public static void main(String[] args) {
		// crea sessionFactory y session
		StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
			    .configure( "hibernate.cfg.xml" )
			    .build();

		Metadata metadata = new MetadataSources( standardRegistry )
			    .addAnnotatedClass( Student.class )
			    .addAnnotatedClass( Tuition.class )
			    .addAnnotatedClass( University.class )
			    .addAnnotatedClass( Course.class )
			    .getMetadataBuilder()
			    .build();

		SessionFactory sessionFactory = metadata.getSessionFactoryBuilder()
			    .build();    
		
		Session session = sessionFactory.openSession();
		
		try {
			session.beginTransaction();
			
			Query<Student> query = session.createQuery("select s from Student s", Student.class);
			
			List<Student> students = query.list();			
			for(Student student : students) {
				System.out.println("Nombre y apellido: " + student.getFirstName() + " " + student.getLastName() + "; Universidad:  " + student.getUniversity().getName());
			}
			
			session.getTransaction().commit();			
			
		}
		catch(Exception e) {
			//hacemos rollback
			System.out.println("Realizando Rollback");
			session.getTransaction().rollback();
			e.getMessage();
		}
		finally {
			session.close();
			sessionFactory.close();
		}
		

	}

}
