package com.springhibenrate.demo;

import com.springhibenrate.entity.Course;
import com.springhibenrate.entity.Instructor;
import com.springhibenrate.entity.InstructorDetail;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class CreateInstructorDemo {

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
            // create intructor object
            Instructor instructor =
                    new Instructor("Susan", "Public", "susan@google.com");

            instructor.setInstructorDetail(new InstructorDetail("http://youtube.com/susannn", "video games"));

            // start a transaction
            session.beginTransaction();

            // print the instructor object
            System.out.println("Created instructor: " + instructor);

            // save the object into db
            System.out.println("Saving...");
            session.save(instructor);

            // commit the transaction
            session.getTransaction().commit();
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
