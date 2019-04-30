package valeriy.knyazhev.transfermoney.application;

/**
 * @author Valeriy Knyazhev
 */
public class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException(long id) {
        super("Account with identifier " + id + " not found.");
    }
}
