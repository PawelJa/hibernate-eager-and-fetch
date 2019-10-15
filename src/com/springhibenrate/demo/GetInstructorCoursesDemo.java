package com.springhibenrate.demo;

import com.springhibenrate.entity.Course;
import com.springhibenrate.entity.Instructor;
import com.springhibenrate.entity.InstructorDetail;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class GetInstructorCoursesDemo {

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

            System.out.println("Instructor: " + instructor);

            // get courses for the instructor
            System.out.println("Courses: " + instructor.getCourses());

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
