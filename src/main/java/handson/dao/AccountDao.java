package handson.dao;

import handson.model.Account;

import java.util.List;

public interface AccountDao {
    void transferMoney(Long accountId1, Long accountId2, double amount);

    void save(Account account);

    void save(List<Account> accounts);

    Account findById(Long id);

    List<Account> findAll();

}
