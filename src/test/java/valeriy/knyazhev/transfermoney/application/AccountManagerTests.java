package valeriy.knyazhev.transfermoney.application;

import org.junit.Before;
import org.junit.Test;
import valeriy.knyazhev.transfermoney.domain.model.Account;
import valeriy.knyazhev.transfermoney.domain.model.AccountRepository;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Valeriy Knyazhev
 */
public class AccountManagerTests {

    private AccountManager accountManager;

    private AccountRepository accountRepository;

    @Before
    public void init() {
        this.accountRepository = new InMemoryAccountRepository();
        this.accountManager = new AccountManager(this.accountRepository);
    }

    @Test
    public void shouldCreateNewAccount() {
        // when
        AccountData newAccount = this.accountManager.createNewAccount();

        // then
        assertThat(newAccount).isNotNull();
        assertThat(newAccount.id()).isEqualTo(0);
    }

    @Test
    public void shouldFindAccountById() {
        // given
        Account account = this.accountRepository.createNewAccount();

        // when
        AccountData foundAccount = this.accountManager.findById(account.id());

        // then
        assertThat(foundAccount).isNotNull();
        assertThat(foundAccount.id()).isEqualTo(account.id());
        assertThat(foundAccount.amount()).isEqualTo(account.amount());
    }

    @Test
    public void shouldPutMoneyToAccount() {
        // given
        Account account = this.accountRepository.createNewAccount();
        long initAmount = account.amount();
        long operationAmount = 1000;


        // when
        this.accountManager.putMoney(account.id(), operationAmount);

        // then
        Account foundAccount = this.accountRepository.findById(account.id());
        assertThat(foundAccount).isNotNull();
        assertThat(foundAccount.id()).isEqualTo(account.id());
        assertThat(foundAccount.amount()).isEqualTo(initAmount + operationAmount);
    }

    @Test
    public void shouldTakeMoneyFromAccount() {
        // given
        Account account = this.accountRepository.createNewAccount();
        this.accountManager.putMoney(account.id(), 1000);
        long initAmount = account.amount();
        long operationAmount = 500;

        // when
        this.accountManager.takeMoney(account.id(), operationAmount);

        // then
        Account foundAccount = this.accountRepository.findById(account.id());
        assertThat(foundAccount).isNotNull();
        assertThat(foundAccount.id()).isEqualTo(account.id());
        assertThat(foundAccount.amount()).isEqualTo(initAmount - operationAmount);
    }

    @Test
    public void shouldTransferMoneyBetweenAccounts() {
        // given
        Account fromAccount = this.accountRepository.createNewAccount();
        Account toAccount = this.accountRepository.createNewAccount();
        this.accountManager.putMoney(fromAccount.id(), 1000);
        long initFromAmount = fromAccount.amount();
        long initToAmount = toAccount.amount();
        long operationAmount = 200;

        // when
        this.accountManager.transferMoney(fromAccount.id(), toAccount.id(), operationAmount);

        // then
        Account foundFromAccount = this.accountRepository.findById(fromAccount.id());
        Account foundToAccount = this.accountRepository.findById(toAccount.id());
        assertThat(foundFromAccount).isNotNull();
        assertThat(foundFromAccount.id()).isEqualTo(fromAccount.id());
        assertThat(foundFromAccount.amount()).isEqualTo(initFromAmount - operationAmount);
        assertThat(foundToAccount).isNotNull();
        assertThat(foundToAccount.id()).isEqualTo(toAccount.id());
        assertThat(foundToAccount.amount()).isEqualTo(initToAmount + operationAmount);
    }

}