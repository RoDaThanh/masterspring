package handson;

import handson.model.Student;
import handson.service.StudentService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

import java.util.List;

public class ObjectQueryLanguage {
    public static void main(String[] args) {
        try (EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("test-jpa");) {
            EntityManager entityManager = entityManagerFactory.createEntityManager();

            // Initialize StudentService
            StudentService studentService = new StudentService();

            // Persist students
            studentService.insertStudentWithBooks(entityManager, "Student 1", "Book 1", "Book 2");
            studentService.insertStudentWithoutBooks(entityManager, "Student 2", "Student 3", "Student 4");

            // Query students whose names start with "Student"
            Query query = entityManager.createQuery("""
                    select s from Student s where s.name like :namePattern
                    """);
            query.setParameter("namePattern", "Student%");
            List<Student> students = query.getResultList();
            students.forEach(System.out::println);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
