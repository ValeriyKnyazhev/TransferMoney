package valeriy.knyazhev.transfermoney;

/**
 * @author Valeriy Knyazhev
 */
public class Main {

    private final static int DEFAULT_PORT = 8080;

    public static void main(String[] args) {
        Integer port = parsePortArg(args);
        TransferMoneyServer server = TransferMoneyServer.createServer(
            port != null ? port : DEFAULT_PORT,
            TransferMoneyServer.ServerConfig.setup()
        );
        try {
            server.start();
        } catch (Exception e) {
            server.stop();
        }
    }

    private static Integer parsePortArg(String[] args) {
        if (args.length == 2 && "-p".equalsIgnoreCase(args[0])) {
            return Integer.valueOf(args[1]);
        } else {
            return null;
        }
    }

}