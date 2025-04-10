package handson;

import handson.config.BeanConfig;
import handson.model.Book;
import handson.service.BookService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class BookMain {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(BeanConfig.class);
        BookService bookService = context.getBean(BookService.class);
        Book book = new Book("new Book");
        bookService.save(book);
    }
}
