package handson;

import handson.model.Book;
import handson.model.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.net.URL;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("test-jpa");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        //create 2 books and 1 student entity
        Book book1 = new Book();
        book1.setTitle("Book 1");
        Book book2 = new Book();
        book2.setTitle("Book 2");
        Student student = new Student();
        student.setName("Student 1");

        student.getBooks().add(book1);
        student.getBooks().add(book2);

        entityManager.persist(student);
        entityTransaction.commit();
        entityManager.close();
        entityManagerFactory.close();
    }

}
