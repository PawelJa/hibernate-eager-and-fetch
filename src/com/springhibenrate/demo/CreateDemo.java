package com.springhibenrate.demo;

import com.springhibenrate.entity.Instructor;
import com.springhibenrate.entity.InstructorDetail;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class CreateDemo {

    public static void main(String[] args) {

        // create session factory
        SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Instructor.class)
                .addAnnotatedClass(InstructorDetail.class)
                .buildSessionFactory();

        // create session
        Session session = sessionFactory.getCurrentSession();

        try {
            // start a transaction
            session.beginTransaction();

            // create intructor object
            Instructor instructor =
                    new Instructor("Bob", "Star", "bob@google.com");
            instructor.setInstructorDetail(new InstructorDetail("http://youtube.com/bobb", "swimming"));

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
