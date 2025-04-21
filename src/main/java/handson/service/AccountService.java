package handson.service;

public interface AccountService {
    void transferMoney(Long accountId1, Long accountId2, double amount);
}
