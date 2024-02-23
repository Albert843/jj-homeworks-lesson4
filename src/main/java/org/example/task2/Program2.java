package org.example.task2;

import org.example.models.Course;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Program2 {
    public static void main(String[] args) {
        SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Course.class)
                .buildSessionFactory();

        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();

            //  Create
            Course course = Course.create();
            session.save(course);
            System.out.println("Object course saved");

            //  Read
            Course retrievedCourse = session.get(Course.class, course.getId());
            System.out.println("Object course retrieved");
            System.out.println("Retrieved course object: " + retrievedCourse);

            //  Update
            retrievedCourse.updateTitle();
            retrievedCourse.updateDuration();
            session.update(retrievedCourse);
            System.out.println("Object course updated");

            //  Delete
            session.delete(retrievedCourse);
            System.out.println("Object course deleted");

            session.getTransaction().commit();
        } finally {
            sessionFactory.close();
        }
    }
}
