package valeriy.knyazhev.transfermoney.domain.model;

import java.util.List;

/**
 * @author Valeriy Knyazhev
 */
public interface AccountRepository {

    public Account createNewAccount();

    public List<Account> findAll();

    public Account findById(int accountId);

}
