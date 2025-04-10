package handson;

import handson.config.BeanConfig;
import handson.dao.StudentDaoJpaImpl;
import handson.model.Student;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class StudentDaoMain {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(BeanConfig.class);
        StudentDaoJpaImpl studentDao = applicationContext.getBean(StudentDaoJpaImpl.class);
        Student student = new Student("thanh dep trai");
        studentDao.saveStudent(student);
    }
}
