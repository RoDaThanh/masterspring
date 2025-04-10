package handson.config;

import handson.dao.BookDao;
import handson.dao.BookDaoJpaImpl;
import handson.dao.StudentDaoJpaImpl;
import handson.service.BookService;
import handson.service.BookServiceImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
public class BeanConfig {

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        factoryBean.setPackagesToScan("handson.model");
        factoryBean.setDataSource(dataSource());
        factoryBean.setJpaPropertyMap(jpaProperties());
        return factoryBean;
    }

    @Bean
    public TransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory);
        return jpaTransactionManager;
    }

    private Map<String, ?> jpaProperties() {
        Map<String,String> jpaPropertiesMap = new HashMap<>();
        jpaPropertiesMap.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        jpaPropertiesMap.put("hibernate.hbm2ddl.auto", "update");
        jpaPropertiesMap.put("hibernate.show_sql", "true");
        jpaPropertiesMap.put("hibernate.format_sql", "true");
        return jpaPropertiesMap;
    }

    @Bean
    public StudentDaoJpaImpl studentDao() {
        return new StudentDaoJpaImpl();
    }

    @Bean
    public BookDao bookDao() {
        return new BookDaoJpaImpl();
    }

    @Bean
    public BookService bookService() {
        return new BookServiceImpl(bookDao());
    }


}
