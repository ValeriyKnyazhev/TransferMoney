package valeriy.knyazhev.transfermoney.domain.model;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * @author Valeriy Knyazhev
 */
public class AccountTests {

    @Test
    public void shouldCreateNewAccount() {
        // given
        int accountId = 1;
        Account newAccount = new Account(accountId);

        // expect
        assertThat(newAccount.id()).isEqualTo(accountId);
        assertThat(newAccount.amount()).isEqualTo(0);
    }

    @Test
    public void shouldPutMoneyToAccount() {
        // given
        int accountId = 1;
        Account account = new Account(accountId);
        long initAmount = account.amount();
        long operationAmount = 1000;

        // when
        account.putMoney(operationAmount);

        // then
        assertThat(account.amount()).isEqualTo(initAmount + operationAmount);
    }

    @Test
    public void shouldThrowExceptionIfPutOperationAmountNotValid() {
        // given
        int accountId = 1;
        Account account = new Account(accountId);
        long negativeAmount = -1000;
        long zeroAmount = 0;

        // expect
        assertThatThrownBy(() -> account.putMoney(negativeAmount))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Amount money must be positive.");
        assertThatThrownBy(() -> account.putMoney(zeroAmount))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Amount money must be positive.");

    }

    @Test
    public void shouldTakeMoneyFromAccount() {
        // given
        int accountId = 1;
        Account account = new Account(accountId);
        account.putMoney(1000);
        long initAmount = account.amount();
        long operationAmount = 500;

        // when
        account.takeMoney(operationAmount);

        // then
        assertThat(account.amount()).isEqualTo(initAmount - operationAmount);
    }

    @Test
    public void shouldThrowExceptionIfTakeOperationAmountNotValid() {
        // given
        int accountId = 1;
        Account account = new Account(accountId);
        account.putMoney(1000);
        long negativeAmount = -1000;
        long zeroAmount = 0;

        // expect
        assertThatThrownBy(() -> account.takeMoney(negativeAmount))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Amount money must be positive.");
        assertThatThrownBy(() -> account.takeMoney(zeroAmount))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Amount money must be positive.");

    }

    @Test
    public void shouldThrowExceptionIfNotEnoughMoneyForTakeOperation() {
        // given
        int accountId = 1;
        Account account = new Account(accountId);
        account.putMoney(500);
        long operationAmount = 1000;

        // expect
        assertThatThrownBy(() -> account.takeMoney(operationAmount))
            .isInstanceOf(IllegalStateException.class)
            .hasMessageContaining("has not enough money");

    }

}