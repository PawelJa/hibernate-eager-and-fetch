package com.springhibenrate.demo;

import com.springhibenrate.entity.Course;
import com.springhibenrate.entity.Instructor;
import com.springhibenrate.entity.InstructorDetail;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class EagerLazyDemo {

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

            // get the instructor from db
            int id = 1;
            Instructor instructor = session.get(Instructor.class, id);

            System.out.println("\n\nInstructor: " + instructor);
            System.out.println("\n\nCourses: " + instructor.getCourses());

            // commit the transaction
            session.getTransaction().commit();

            // close the session
            session.close();
            System.out.println("\nSession is now closed !\n");

            // since courses are lazy loaded... this should fail

            // resolve #1: copy call getter method while session is open

            // get courses for the instructor
            System.out.println("\n\nCourses: " + instructor.getCourses());

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
