package valeriy.knyazhev.transfermoney.port.adapter.request;

/**
 * @author Valeriy Knyazhev
 */
public class MoneyActionRequest {

    private long amount;

    public MoneyActionRequest(long amount) {
        this.amount = amount;
    }

    protected MoneyActionRequest() {
        // empty
    }

    public long getAmount() {
        return this.amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

}