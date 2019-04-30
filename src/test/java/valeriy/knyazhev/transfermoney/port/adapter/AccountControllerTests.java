package valeriy.knyazhev.transfermoney.port.adapter;

import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;
import org.mockito.Mockito;
import valeriy.knyazhev.transfermoney.TransferMoneyServer;
import valeriy.knyazhev.transfermoney.application.AccountData;
import valeriy.knyazhev.transfermoney.application.AccountManager;
import valeriy.knyazhev.transfermoney.application.AccountNotFoundException;
import valeriy.knyazhev.transfermoney.port.adapter.model.AccountsModel;
import valeriy.knyazhev.transfermoney.port.adapter.model.ResponseModel;
import valeriy.knyazhev.transfermoney.port.adapter.request.MoneyActionRequest;
import valeriy.knyazhev.transfermoney.port.adapter.request.TransferMoneyRequest;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Valeriy Knyazhev
 */
public class AccountControllerTests extends JerseyTest {

    private AccountManager accountManager;

    @Override
    public Application configure() {
        this.accountManager = Mockito.mock(AccountManager.class);
        AccountController controller = new AccountController(this.accountManager);
        return TransferMoneyServer.ServerConfig.setup(controller);
    }

    @Test
    public void shouldCreateNewAccount() {
        // given
        Mockito.when(this.accountManager.createNewAccount())
            .thenReturn(new AccountData(0, 0));

        // expect
        Response response = target("/accounts")
            .request()
            .post(Entity.json(null));
        AccountData responseBody = response.readEntity(AccountData.class);
        assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
        assertThat(responseBody.id()).isEqualTo(0);
        assertThat(responseBody.amount()).isEqualTo(0);
    }

    @Test
    public void shouldFindAccountById() {
        // given
        int accountId = 1;
        long amount = 1000;
        Mockito.when(this.accountManager.findById(accountId))
            .thenReturn(new AccountData(accountId, amount));

        // expect
        Response response = target("/accounts/" + accountId)
            .request()
            .get();
        AccountData responseBody = response.readEntity(AccountData.class);
        assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
        assertThat(responseBody.id()).isEqualTo(accountId);
        assertThat(responseBody.amount()).isEqualTo(amount);
    }

    @Test
    public void shouldNotFindAccountById() {
        // given
        int accountId = 1;
        Mockito.when(this.accountManager.findById(accountId))
            .thenThrow(new AccountNotFoundException(accountId));

        // expect
        Response response = target("/accounts/" + accountId)
            .request()
            .get();
        ResponseModel responseBody = response.readEntity(ResponseModel.class);
        assertThat(response.getStatus()).isEqualTo(Response.Status.NOT_FOUND.getStatusCode());
        assertThat(responseBody.info()).isNull();
        assertThat(responseBody.error()).isEqualTo("Account with identifier " + accountId + " not found.");
    }

    @Test
    public void shouldFindAllAccounts() {
        // given
        Mockito.when(this.accountManager.findAll())
            .thenReturn(
                Collections.singletonList(
                    new AccountData(1, 1000)
                )
            );

        // expect
        Response response = target("/accounts")
            .request()
            .get();
        AccountsModel responseBody = response.readEntity(AccountsModel.class);
        assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
        assertThat(responseBody.getAccounts()).isNotNull();
        assertThat(responseBody.getAccounts()).isNotEmpty();
    }

    @Test
    public void shouldPutMoneyToAccount() {
        // given
        int accountId = 1;

        // expect
        Response response = target("/accounts/" + accountId + "/put")
            .request()
            .post(
                Entity.entity(
                    new MoneyActionRequest(1000),
                    MediaType.APPLICATION_JSON
                )
            );
        ResponseModel responseBody = response.readEntity(ResponseModel.class);
        assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
        assertThat(responseBody.info()).isEqualTo("Money successfully added to account " + accountId + ".");
        assertThat(responseBody.error()).isNull();
    }

    @Test
    public void shouldNotPutMoneyToAccountIfAmountNotValid() {
        // given
        int accountId = 1;
        long amount = -1000;
        Mockito.doThrow(new IllegalArgumentException("Wrong money amount."))
            .when(this.accountManager)
            .putMoney(accountId, amount);

        // expect
        Response response = target("/accounts/" + accountId + "/put")
            .request()
            .post(
                Entity.entity(
                    new MoneyActionRequest(amount),
                    MediaType.APPLICATION_JSON
                )
            );
        ResponseModel responseBody = response.readEntity(ResponseModel.class);
        assertThat(response.getStatus()).isEqualTo(Response.Status.BAD_REQUEST.getStatusCode());
        assertThat(responseBody.info()).isNull();
        assertThat(responseBody.error()).isNotNull();
    }

    @Test
    public void shouldTakeMoneyFromAccount() {
        // given
        int accountId = 1;

        // expect
        Response response = target("/accounts/" + accountId + "/take")
            .request()
            .post(
                Entity.entity(
                    new MoneyActionRequest(1000),
                    MediaType.APPLICATION_JSON
                )
            );
        ResponseModel responseBody = response.readEntity(ResponseModel.class);
        assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
        assertThat(responseBody.info()).isEqualTo("Money successfully withdrawn from account " + accountId + ".");
        assertThat(responseBody.error()).isNull();
    }

    @Test
    public void shouldNotTakeMoneyFromAccountIfAmountNotValid() {
        // given
        int accountId = 1;
        long amount = -1000;
        Mockito.doThrow(new IllegalArgumentException("Wrong money amount."))
            .when(this.accountManager)
            .takeMoney(accountId, amount);

        // expect
        Response response = target("/accounts/" + accountId + "/take")
            .request()
            .post(
                Entity.entity(
                    new MoneyActionRequest(amount),
                    MediaType.APPLICATION_JSON
                )
            );
        ResponseModel responseBody = response.readEntity(ResponseModel.class);
        assertThat(response.getStatus()).isEqualTo(Response.Status.BAD_REQUEST.getStatusCode());
        assertThat(responseBody.info()).isNull();
        assertThat(responseBody.error()).isNotNull();
    }

    @Test
    public void shouldNotTakeMoneyFromAccountIfNotEnoughMoney() {
        // given
        int accountId = 1;
        long amount = 1000;
        Mockito.doThrow(new IllegalStateException("Not enough money."))
            .when(this.accountManager)
            .takeMoney(accountId, amount);

        // expect
        Response response = target("/accounts/" + accountId + "/take")
            .request()
            .post(
                Entity.entity(
                    new MoneyActionRequest(amount),
                    MediaType.APPLICATION_JSON
                )
            );
        ResponseModel responseBody = response.readEntity(ResponseModel.class);
        assertThat(response.getStatus()).isEqualTo(Response.Status.BAD_REQUEST.getStatusCode());
        assertThat(responseBody.info()).isNull();
        assertThat(responseBody.error()).isNotNull();
    }

    @Test
    public void shouldTransferMoneyBetweenAccounts() {
        // given
        int fromAccountId = 1;
        int toAccountId = 2;

        // expect
        Response response = target("/accounts/transfer")
            .request()
            .post(
                Entity.entity(
                    new TransferMoneyRequest(fromAccountId, toAccountId, 1000),
                    MediaType.APPLICATION_JSON
                )
            );
        ResponseModel responseBody = response.readEntity(ResponseModel.class);
        assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
        assertThat(responseBody.info()).isEqualTo(
            "Money successfully transferred from account " + fromAccountId
                + " to account " + toAccountId + "."
        );
        assertThat(responseBody.error()).isNull();
    }

    @Test
    public void shouldNotTransferMoneyBetweenAccountsIfAccountsAreNotDifferent() {
        // given
        int fromAccountId = 1;
        int toAccountId = fromAccountId;
        long amount = 1000;
        Mockito.doThrow(new IllegalArgumentException("Accounts must be different."))
            .when(this.accountManager)
            .transferMoney(fromAccountId, toAccountId, amount);


        // expect
        Response response = target("/accounts/transfer")
            .request()
            .post(
                Entity.entity(
                    new TransferMoneyRequest(fromAccountId, toAccountId, amount),
                    MediaType.APPLICATION_JSON
                )
            );
        ResponseModel responseBody = response.readEntity(ResponseModel.class);
        assertThat(response.getStatus()).isEqualTo(Response.Status.BAD_REQUEST.getStatusCode());
        assertThat(responseBody.info()).isNull();
        assertThat(responseBody.error()).isNotNull();
    }

}