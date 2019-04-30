package valeriy.knyazhev.transfermoney.application;

import valeriy.knyazhev.transfermoney.domain.model.Account;
import valeriy.knyazhev.transfermoney.domain.model.AccountRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Valeriy Knyazhev
 */
public class InMemoryAccountRepository implements AccountRepository {

    private final List<Account> accounts = new ArrayList<>();

    @Override
    public Account createNewAccount() {
        synchronized (this.accounts) {
            Account account = new Account(accounts.size());
            this.accounts.add(account);
            return account;
        }
    }

    @Override
    public List<Account> findAll() {
        return Collections.unmodifiableList(this.accounts);
    }

    @Override
    public Account findById(int accountId) {
        if (accountId < 0 || accountId >= this.accounts.size()) {
            throw new AccountNotFoundException(accountId);
        }
        return this.accounts.get(accountId);
    }

}
