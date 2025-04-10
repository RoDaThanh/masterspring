package handson;

import handson.config.BeanConfig;
import handson.model.Student;
import handson.service.StudentService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class JPASupportMain {
    public static void main(String[] args) {
        ApplicationContext app = new AnnotationConfigApplicationContext(BeanConfig.class);
        EntityManagerFactory entityManagerFactory = app.getBean(EntityManagerFactory.class);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
//            StudentService studentService = new StudentService();
//          studentService.insertStudentWithBooks(entityManager, "Student 1", "Book 1", "Book 2");
            transaction.begin();
            Student student = new Student();
            student.setName("hello Name");
            entityManager.persist(student);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
    }
}
