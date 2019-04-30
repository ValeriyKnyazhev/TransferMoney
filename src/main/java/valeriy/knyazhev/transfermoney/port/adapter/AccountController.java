package valeriy.knyazhev.transfermoney.port.adapter;

import valeriy.knyazhev.transfermoney.application.AccountData;
import valeriy.knyazhev.transfermoney.application.AccountManager;
import valeriy.knyazhev.transfermoney.port.adapter.model.ResponseModel;
import valeriy.knyazhev.transfermoney.port.adapter.request.MoneyActionRequest;
import valeriy.knyazhev.transfermoney.port.adapter.request.MoneyTransferRequest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


/**
 * @author Valeriy Knyazhev
 */
@Path("/accounts")
public class AccountController {

    private AccountManager accountManager;

    public AccountController() {
        this.accountManager = AccountManager.getInstance();
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
        this.accountManager.putMoney(accountId, request.amount());
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
        this.accountManager.takeMoney(accountId, request.amount());
        return Response
            .status(Response.Status.OK)
            .entity(ResponseModel.info("Money successfully withdrawn from account " + accountId + "."))
            .build();
    }

    @POST
    @Path("/transfer")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response takeMoneyFromAccount(MoneyTransferRequest request) {
        this.accountManager.transferMoney(
            request.fromAccountId(), request.toAccountId(), request.amount()
        );
        return Response
            .status(Response.Status.OK)
            .entity(ResponseModel.info(
                "Money successfully transferred from account "
                    + request.fromAccountId() + " to account "
                    + request.toAccountId() + "."
                )
            )
            .build();
    }

}