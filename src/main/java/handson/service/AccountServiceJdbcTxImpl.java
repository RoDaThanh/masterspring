package handson.service;

import handson.dao.AccountDao;

public class AccountServiceJdbcTxImpl implements AccountService{
    private final AccountDao accountDao;

    public AccountServiceJdbcTxImpl(AccountDao accountDao) {
        this.accountDao = accountDao;
    }


    @Override
    public void transferMoney(Long accountId1, Long accountId2, double amount) {
        accountDao.transferMoney(accountId1, accountId2, amount);
    }
}
