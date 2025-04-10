package handson.dao;

import handson.model.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;

import java.util.List;

public class StudentDaoJpaImpl {

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    public void saveStudent(Student student) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(student);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public List<Student> getStudents() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Student> students = entityManager.createQuery("SELECT s FROM Student s", Student.class).getResultList();
        students.forEach(s -> s.getBooks().forEach(System.out::println));
        entityManager.close();
        return students;
    }
}
