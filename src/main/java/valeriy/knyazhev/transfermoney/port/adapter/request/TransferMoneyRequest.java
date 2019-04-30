package valeriy.knyazhev.transfermoney.port.adapter.request;

/**
 * @author Valeriy Knyazhev
 */
public class TransferMoneyRequest {

    private long amount;

    private int fromAccountId;

    private int toAccountId;

    public TransferMoneyRequest(int fromAccountId, int toAccountId, long amount) {
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.amount = amount;
    }

    protected TransferMoneyRequest() {
        // empty
    }

    public long getAmount() {
        return this.amount;
    }

    public int getFromAccountId() {
        return this.fromAccountId;
    }

    public int getToAccountId() {
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