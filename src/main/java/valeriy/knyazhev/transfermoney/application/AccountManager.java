package valeriy.knyazhev.transfermoney.application;

import valeriy.knyazhev.transfermoney.domain.model.Account;
import valeriy.knyazhev.transfermoney.domain.model.AccountRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Valeriy Knyazhev
 */
public class AccountManager {


    private final AccountRepository repository;

    public AccountManager(AccountRepository repository) {
        this.repository = repository;
    }

    private static AccountData constructData(Account account) {
        return new AccountData(account.id(), account.amount());
    }

    public AccountData createNewAccount() {
        Account newAccount = this.repository.createNewAccount();
        return constructData(newAccount);
    }

    public void putMoney(int accountId, long amount) {
        Account account = this.repository.findById(accountId);
        synchronized (account) {
            account.putMoney(amount);
        }
    }

    public void takeMoney(int accountId, long amount) {
        Account account = this.repository.findById(accountId);
        synchronized (account) {
            account.takeMoney(amount);
        }
    }

    public List<AccountData> findAll() {
        return this.repository.findAll().stream()
            .map(AccountManager::constructData)
            .collect(Collectors.toList());
    }

    public AccountData findById(int accountId) {
        Account account = this.repository.findById(accountId);
        return constructData(account);
    }

    public void transferMoney(int fromAccountId, int toAccountId, long amount) {
        if (fromAccountId == toAccountId) {
            throw new IllegalArgumentException("From and to account must be different.");
        }
        Account accountFrom = this.repository.findById(fromAccountId);
        Account accountTo = this.repository.findById(toAccountId);

        if (accountFrom.id() < accountTo.id()) {
            synchronized (accountFrom) {
                synchronized (accountTo) {
                    doTransferMoney(accountFrom, accountTo, amount);
                }
            }
        } else {
            synchronized (accountTo) {
                synchronized (accountFrom) {
                    doTransferMoney(accountFrom, accountTo, amount);
                }
            }
        }
    }

    private void doTransferMoney(Account accountFrom, Account accountTo, long amount) {
        accountFrom.takeMoney(amount);
        accountTo.putMoney(amount);
    }

}