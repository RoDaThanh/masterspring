package handson;

import handson.model.Book;
import handson.model.Student;
import handson.service.StudentService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("test-jpa");

        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            EntityTransaction entityTransaction = entityManager.getTransaction();

            StudentService studentService = new StudentService();
            studentService.insertStudentWithBooks(entityManager, "Student 1", "Book 1", "Book 2");

            //  Find, Update, and Delete Entities
            try {
                entityTransaction.begin();
                Student studentDb = entityManager.find(Student.class, 1L);
                if (studentDb != null) {
                    Book bookDb = entityManager.find(Book.class, 2L);
                    if (bookDb != null) {
                        System.out.println(studentDb);
                        System.out.println(bookDb);

                        // Modify the student and remove the book
                        studentDb.setName("Thanh dep trai");
                        studentDb.getBooks().remove(bookDb);
                        entityManager.remove(bookDb);
                    } else {
                        System.out.println("Book not found");
                    }
                } else {
                    System.out.println("Student not found");
                }

                entityTransaction.commit();
            } catch (RuntimeException e) {
                if (entityTransaction.isActive()) {
                    entityTransaction.rollback();
                }
                throw e;
            }

            // Fetch the updated student to verify changes
            try {
                entityTransaction.begin();
                Student studentDb = entityManager.find(Student.class, 1L);
                if (studentDb != null) {
                    System.out.println(studentDb);
                } else {
                    System.out.println("Student not found");
                }
                List<Book> books = entityManager.createQuery("SELECT b FROM Book b", Book.class).getResultList();
                // Print all books
                System.out.println("All Books in DB:");
                for (Book book : books) {
                    System.out.println(book);
                }

                entityTransaction.commit();
            } catch (RuntimeException e) {
                if (entityTransaction.isActive()) {
                    entityTransaction.rollback();
                }
                throw e;
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            entityManagerFactory.close();
        }
    }

}
