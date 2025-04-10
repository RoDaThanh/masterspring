package handson.service;

import handson.dao.BookDao;
import handson.model.Book;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class BookServiceImpl implements BookService {
    private final BookDao bookDao;

    public BookServiceImpl(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Override
    public void save(Book book) {
        bookDao.save(book);
    }

}
