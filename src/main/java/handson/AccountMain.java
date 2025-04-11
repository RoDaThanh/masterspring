package handson;

import handson.config.DBconnection;
import handson.dao.AccountDaoJbdcImpl;
import handson.model.Account;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class AccountMain {
    public static void main(String[] args) throws SQLException {
        Connection connection = DBconnection.getDbConnection();

        AccountDaoJbdcImpl accountDao = new AccountDaoJbdcImpl(connection);

        //add 2 account with connection
        try {
            String createTableSql = """
            CREATE TABLE account (
                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                name VARCHAR(255),
                balance DOUBLE
                );
            """;
            connection.createStatement().executeUpdate(createTableSql);
            connection.createStatement().executeUpdate("insert into account (name, balance) values ('account1', 1000)");
            connection.createStatement().executeUpdate("insert into account (name, balance) values ('account2', 2000)");
            accountDao.save(new Account(null, "thanhdeptrai", 3000));
            List<Account> accountFounds = accountDao.findAll();
            accountFounds.forEach(s -> System.out.println(s.toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection.close();
    }
}
