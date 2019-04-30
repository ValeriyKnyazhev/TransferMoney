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

    private static void checkAmountCorrectness(long amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount money must be positive.");
        }
    }

    public void putMoney(long amount) {
        checkAmountCorrectness(amount);
        this.amount += amount;
    }

    public void takeMoney(long amount) {
        checkAmountCorrectness(amount);
        if (amount > this.amount) {
            throw new IllegalStateException("Account " + this.id + " has not enough money for operation.");
        }
        this.amount -= amount;
    }

    public int id() {
        return this.id;
    }

    public long amount() {
        return this.amount;
    }
}