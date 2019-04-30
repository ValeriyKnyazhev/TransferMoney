package valeriy.knyazhev.transfermoney.application;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

/**
 * @author Valeriy Knyazhev
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class AccountData {

    private final int id;

    private final long amount;

    public AccountData(int id, long amount) {
        this.id = id;
        this.amount = amount;
    }

    public int id() {
        return this.id;
    }

    public long amount() {
        return this.amount;
    }
}