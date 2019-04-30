package valeriy.knyazhev.transfermoney.port.adapter;

import valeriy.knyazhev.transfermoney.application.AccountData;
import valeriy.knyazhev.transfermoney.application.AccountManager;
import valeriy.knyazhev.transfermoney.port.adapter.model.AccountsModel;
import valeriy.knyazhev.transfermoney.port.adapter.model.ResponseModel;
import valeriy.knyazhev.transfermoney.port.adapter.request.MoneyActionRequest;
import valeriy.knyazhev.transfermoney.port.adapter.request.TransferMoneyRequest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


/**
 * @author Valeriy Knyazhev
 */
@Path("/accounts")
public class AccountController {

    private final AccountManager accountManager;

    public AccountController(AccountManager accountManager) {
        this.accountManager = accountManager;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response createAccount() {
        AccountData newAccount = this.accountManager.createNewAccount();
        return Response
            .status(Response.Status.OK)
            .entity(newAccount)
            .build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response fetchAllAccounts() {
        List<AccountData> accounts = this.accountManager.findAll();
        return Response
            .status(Response.Status.OK)
            .entity(new AccountsModel(accounts))
            .build();
    }

    @Path("/{accountId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response fetchAccount(@PathParam("accountId") int accountId) {
        AccountData account = this.accountManager.findById(accountId);
        return Response
            .status(Response.Status.OK)
            .entity(account)
            .build();
    }

    @POST
    @Path("/{accountId}/put")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response putMoneyToAccount(MoneyActionRequest request,
                                      @PathParam("accountId") int accountId) {
        this.accountManager.putMoney(accountId, request.getAmount());
        return Response
            .status(Response.Status.OK)
            .entity(ResponseModel.info("Money successfully added to account " + accountId + "."))
            .build();
    }

    @POST
    @Path("/{accountId}/take")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response takeMoneyFromAccount(MoneyActionRequest request,
                                         @PathParam("accountId") int accountId) {
        this.accountManager.takeMoney(accountId, request.getAmount());
        return Response
            .status(Response.Status.OK)
            .entity(ResponseModel.info("Money successfully withdrawn from account " + accountId + "."))
            .build();
    }

    @POST
    @Path("/transfer")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response takeMoneyFromAccount(TransferMoneyRequest request) {
        this.accountManager.transferMoney(
            request.getFromAccountId(), request.getToAccountId(), request.getAmount()
        );
        return Response
            .status(Response.Status.OK)
            .entity(ResponseModel.info(
                "Money successfully transferred from account "
                    + request.getFromAccountId() + " to account "
                    + request.getToAccountId() + "."
                )
            )
            .build();
    }

}