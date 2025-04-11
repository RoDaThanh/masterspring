package handson.dao;

import handson.config.DBconnection;
import handson.model.Account;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDaoJbdcImpl implements AccountDao {

    private final Connection connection;

    public AccountDaoJbdcImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void transferMoney(Long accountId1, Long accountId2, double amount) {
        System.out.println("Transferring money from account " + accountId1 + " to account " + accountId2 + " amount " + amount);
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("update account set balance = balance - " + amount + " where id = " + accountId1);
            statement.executeUpdate("update account set balance = balance + " + amount + " where id = " + accountId2);
            connection.commit();
            statement.close();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(e);
            }
            throw new RuntimeException(e);
        } finally {
            try {
                connection.close();
            } catch (SQLException ignored) {
            }
        }
    }

    @Override
    public void save(Account account) {
        String sql = "INSERT INTO account (name, balance) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, account.getName());
            stmt.setDouble(2, account.getBalance());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(List<Account> accounts) {
        String sql = "INSERT INTO account (name, balance) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            for (Account account : accounts) {
                stmt.setString(1, account.getName());
                stmt.setDouble(2, account.getBalance());
                stmt.addBatch();
            }
            stmt.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Account findById(Long id) {
        String sql = "SELECT * FROM account WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Account(
                            rs.getLong("id"),
                            rs.getString("name"),
                            rs.getDouble("balance")
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null; // not found
    }

    @Override
    public List<Account> findAll() {
        String sql = "SELECT * FROM account limit 10";
        List<Account> accounts = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    accounts.add(new Account(
                            rs.getLong("id"),
                            rs.getString("name"),
                            rs.getDouble("balance")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return accounts;
    }


}
