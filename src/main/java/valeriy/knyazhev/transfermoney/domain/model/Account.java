package valeriy.knyazhev.transfermoney.domain.model;

/**
 * @author Valeriy Knyazhev
 */
public class Account {

    private final int id;

    private long amount;

    public Account(int id) {
        this.id = id;
        this.amount = 0;
    }

    public int id() {
        return this.id;
    }

    public long amount() {
        return this.amount;
    }
}