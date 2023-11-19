package vn.edu.iuh.fit.resoures;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import vn.edu.iuh.fit.entities.Login;
import vn.edu.iuh.fit.models.Account;
import vn.edu.iuh.fit.models.Order;
import vn.edu.iuh.fit.services.AccountService;

import java.util.List;
import java.util.Optional;

@Path("/accounts")
public class AccountResource {
    private final AccountService accountService;

    public AccountResource() {
        accountService = new AccountService();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createAccount(Account account) {
        boolean isSuccess = accountService.createAccount(account);
        if (isSuccess) {
            return Response.status(Response.Status.CREATED).entity(isSuccess).build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(Login login) {
        Account account = accountService.login(login);
        return Response.ok(account).build();
    }

    @PUT
    @Path("/{accountId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateAccount(@PathParam("accountId") long accountId, Account account) {
        account.setAccountId(accountId);
        boolean isSuccess = accountService.updateAccount(account);
        if (isSuccess) {
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DELETE
    @Path("/{accountId}")
    public Response deleteAccount(@PathParam("accountId") long accountId) {
        boolean isSuccess = accountService.deleteAccount(accountId);
        if (isSuccess) {
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("/{accountId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAccountById(@PathParam("accountId") long accountId) {
        Account account = accountService.getAccountById(accountId);
        if (account != null) {
            return Response.status(Response.Status.OK).entity(account).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllAccounts() {
        List<Account> accounts = accountService.getAllAccounts();
        return Response.status(Response.Status.OK).entity(accounts).build();
    }

    @GET
    @Path("/getID")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAccountId() {
        long id = accountService.getAccountId();
        if (id != 0) {
            return Response.ok(id).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/checkExist/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkAccountExist(@PathParam("email") String email) {
        boolean isSuccess = accountService.checkAccountExist(email);
        return Response.ok(isSuccess).build();
    }

    @GET
    @Path("/account-by-email/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAccountsByEmail(@PathParam("email") String email) {
        List<Account> accounts = accountService.getAccountsByEmail(email);
        return Response.ok(accounts).build();
    }

    @GET
    @Path("/get-quantity-accounts")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getQuantityAccount() {
        long quantity = accountService.getQuantityAccount();
        return Response.ok(quantity).build();
    }

}