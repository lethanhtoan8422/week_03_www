package vn.edu.iuh.fit.services;

import vn.edu.iuh.fit.entities.Login;
import vn.edu.iuh.fit.models.Account;
import vn.edu.iuh.fit.repositories.AccountRepository;

import java.util.List;

public class AccountService {
    private final AccountRepository accountRepository;

    public AccountService() {
        accountRepository = new AccountRepository();
    }

    public boolean createAccount(Account account) {
        return accountRepository.createAccount(account);
    }

    public boolean updateAccount(Account account) {
        return accountRepository.updateAccount(account);
    }

    public boolean deleteAccount(long accountId) {
        return accountRepository.deleteAccount(accountId);
    }

    public Account getAccountById(long accountId) {
        return accountRepository.getAccountById(accountId);
    }

    public List<Account> getAllAccounts() {
        return accountRepository.getAllAccounts();
    }

    public long getAccountId(){
        return accountRepository.getAccountId();
    }

    public boolean checkAccountExist(String email) {
        return accountRepository.checkAccountExist(email);
    }

    public Account login(Login login) {
        return accountRepository.login(login);
    }

    public List<Account> getAccountByEmail(String email) {
        return accountRepository.getAccountByEmail(email);
    }
    public long getQuantityAccount() {
        return accountRepository.getQuantityAccount();
    }
}