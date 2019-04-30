package valeriy.knyazhev.transfermoney;

/**
 * @author Valeriy Knyazhev
 */
public class Main {

    public static void main(String[] args) throws Exception {
        TransferMoneyServer server = new TransferMoneyServer();
        try {
            server.startServer(8080);
        } catch (Exception e) {
            server.stopServer();
        }
    }

}