package valeriy.knyazhev.transfermoney.port.adapter;

import valeriy.knyazhev.transfermoney.application.AccountManager;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
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

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response test1() {
        return Response
            .status(Response.Status.OK)
            .entity("{\"info\": \"Hello!\"}")
            .build();
    }

    @GET
    @Path("/{accountId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response test2(@PathParam("accountId") int accountId) {
        return Response
            .status(Response.Status.OK)
            .entity("{\"info\": \"Hello, " + accountId + "!\"}")
            .build();
    }

}