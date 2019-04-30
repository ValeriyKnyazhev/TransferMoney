package valeriy.knyazhev.transfermoney.application;

import org.junit.Before;
import org.junit.Test;
import valeriy.knyazhev.transfermoney.domain.model.Account;
import valeriy.knyazhev.transfermoney.domain.model.AccountRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * @author Valeriy Knyazhev
 */
public class InMemoryAccountRepositoryTests {

    private AccountRepository accountRepository;

    @Before
    public void init() {
        accountRepository = new InMemoryAccountRepository();
    }

    @Test
    public void shouldCreateNewAccount() {
        // when
        Account newAccount = this.accountRepository.createNewAccount();

        // then
        assertThat(newAccount).isNotNull();
        assertThat(newAccount.id()).isEqualTo(0);
    }

    @Test
    public void shouldFindAccountById() {
        // given
        Account account = this.accountRepository.createNewAccount();

        // when
        Account foundAccount = this.accountRepository.findById(account.id());

        // then
        assertThat(foundAccount).isNotNull();
        assertThat(foundAccount.id()).isEqualTo(account.id());
        assertThat(foundAccount.amount()).isEqualTo(account.amount());
    }

    @Test
    public void shouldThrowExceptionIfAccountNotFound() {
        // given
        int accountId = 1;

        // expect
        assertThatThrownBy(() -> this.accountRepository.findById(accountId))
            .isInstanceOf(AccountNotFoundException.class);
    }

}