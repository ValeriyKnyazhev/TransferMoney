package valeriy.knyazhev.transfermoney;

/**
 * @author Valeriy Knyazhev
 */
public class Main {

    public static void main(String[] args) throws Exception {
        TransferMoneyServer server = TransferMoneyServer.createServer(
            8080,
            TransferMoneyServer.ServerConfig.setup()
        );
        try {
            server.start();
        } catch (Exception e) {
            server.stop();
        }
    }

}