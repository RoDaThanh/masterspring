package handson.service;

import handson.model.Book;
import handson.model.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StudentService {

    public void insertStudentWithBooks(EntityManager entityManager, String studentName, String... bookTitles) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();

            Student student = new Student(studentName);
            Arrays.stream(bookTitles).forEach(title -> {
                Book book = new Book(title);
                student.getBooks().add(book);
            });

            entityManager.persist(student);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public void insertStudentWithoutBooks(EntityManager entityManager, String... studentNames) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();

            List<Student> students = Arrays.stream(studentNames).map(Student::new).toList();

            students.forEach(entityManager::persist);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }
}
