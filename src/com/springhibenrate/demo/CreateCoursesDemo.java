package com.springhibenrate.demo;

import com.springhibenrate.entity.Course;
import com.springhibenrate.entity.Instructor;
import com.springhibenrate.entity.InstructorDetail;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class CreateCoursesDemo {

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

            // create some courses
            Course course1 = new Course("Air Guitar - The ultimate guide");
            Course course2 = new Course("The pinball masterclass");

            // add courses to instructor
            instructor.add(course1);
            instructor.add(course2);

            // save the courses
            session.save(course1);
            session.save(course2);

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
