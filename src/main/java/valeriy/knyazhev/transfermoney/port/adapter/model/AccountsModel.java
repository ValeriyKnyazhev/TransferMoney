package valeriy.knyazhev.transfermoney.port.adapter.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import valeriy.knyazhev.transfermoney.application.AccountData;

import java.util.List;

/**
 * @author Valeriy Knyazhev
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class AccountsModel {

    public final List<AccountData> accounts;

    public AccountsModel(List<AccountData> accounts) {
        this.accounts = accounts;
    }

}
