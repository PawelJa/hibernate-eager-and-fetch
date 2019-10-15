package com.springhibenrate.demo;

import com.springhibenrate.entity.Course;
import com.springhibenrate.entity.Instructor;
import com.springhibenrate.entity.InstructorDetail;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

public class FetchJoinDemo {

    public static void main(String[] args) {

        // create session factory
        SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Instructor.class)
                .addAnnotatedClass(InstructorDetail.class)
                .addAnnotatedClass(Course.class)
                .buildSessionFactory();

        // create session
        Session session = sessionFactory.getCurrentSession();

        try {
            // start a transaction
            session.beginTransaction();

            // resolve #2: Hibernate query with HQL

            // get the instructor from db
            int id = 1;
            Query<Instructor> query =
                    session.createQuery("SELECT i FROM Instructor i "
                            + "JOIN FETCH i.courses "
                            + "WHERE i.id=:instructorId", Instructor.class);

            // set parameter on query
            query.setParameter("instructorId", id);

            // execute query and get instructor
            Instructor instructor = query.getSingleResult();

            System.out.println("\n\nInstructor: " + instructor);

            // commit the transaction
            session.getTransaction().commit();

            // close the session
            session.close();
            System.out.println("\nSession is now closed !\n");

            // get courses for the instructor
            System.out.println("\nCourses: " + instructor.getCourses());
            System.out.println("Done !");

        } catch (Exception exc) {
            exc.printStackTrace();
        } finally {
            // handle connection leak issue
            session.close();
            sessionFactory.close();
        }
    }
}
