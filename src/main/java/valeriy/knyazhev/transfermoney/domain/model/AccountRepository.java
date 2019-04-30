package valeriy.knyazhev.transfermoney.domain.model;

/**
 * @author Valeriy Knyazhev
 */
public interface AccountRepository {

    public Account createNewAccount();

    public Account findById(int accountId);

}
