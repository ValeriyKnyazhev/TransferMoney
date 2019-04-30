package valeriy.knyazhev.transfermoney.port.adapter.request;

/**
 * @author Valeriy Knyazhev
 */
public class TransferMoneyRequest {

    private long amount;

    private int fromAccountId;

    private int toAccountId;

    public long amount() {
        return this.amount;
    }

    public int fromAccountId() {
        return this.fromAccountId;
    }

    public int toAccountId() {
        return this.toAccountId;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public void setFromAccountId(int fromAccountId) {
        this.fromAccountId = fromAccountId;
    }

    public void setToAccountId(int toAccountId) {
        this.toAccountId = toAccountId;
    }

}