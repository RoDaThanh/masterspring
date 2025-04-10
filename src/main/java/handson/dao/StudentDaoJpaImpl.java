package handson.dao;

import handson.model.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;

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
}
