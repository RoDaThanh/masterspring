package handson.dao;

import handson.model.Book;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;


public class BookDaoJpaImpl implements BookDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(Book book) {
        entityManager.persist(book);
    }
}
