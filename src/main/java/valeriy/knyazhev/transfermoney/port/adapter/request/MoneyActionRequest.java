package valeriy.knyazhev.transfermoney.port.adapter.request;

/**
 * @author Valeriy Knyazhev
 */
public class MoneyActionRequest {

    private long amount;

    public long amount() {
        return this.amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

}