package handson;

import handson.config.DBconnection;
import handson.dao.AccountDaoJbdcImpl;
import handson.model.Account;
import handson.service.AccountService;
import handson.service.AccountServiceJdbcTxImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class AccountMain {
    public static void main(String[] args) throws SQLException {
        Connection connection = DBconnection.getDbConnection();

        AccountDaoJbdcImpl accountDao = new AccountDaoJbdcImpl(connection);
        AccountService accountService = new AccountServiceJdbcTxImpl(accountDao);

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
            List<Account> accountFounds = accountDao.findAll();
            accountFounds.forEach(s -> System.out.println(s.toString()));
            System.out.println("---------------------AFTER--------------------");
            accountService.transferMoney(accountFounds.get(0).getId(), accountFounds.get(1).getId(), 1000);
            accountFounds = accountDao.findAll();
            accountFounds.forEach(s -> System.out.println(s.toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection.close();
    }
}
