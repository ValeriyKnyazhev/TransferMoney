package valeriy.knyazhev.transfermoney;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import valeriy.knyazhev.transfermoney.application.AccountManager;
import valeriy.knyazhev.transfermoney.application.InMemoryAccountRepository;
import valeriy.knyazhev.transfermoney.domain.model.AccountRepository;
import valeriy.knyazhev.transfermoney.port.adapter.AccountController;
import valeriy.knyazhev.transfermoney.port.adapter.ExceptionHandler;

import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;

/**
 * @author Valeriy Knyazhev
 */
public class TransferMoneyServer {

    private final HttpServer server;

    private TransferMoneyServer(HttpServer server) {
        this.server = server;
    }

    public static TransferMoneyServer createServer(int port, ServerConfig config) {
        URI uri = UriBuilder.fromPath("http://localhost:" + port).build();
        return new TransferMoneyServer(GrizzlyHttpServerFactory.createHttpServer(uri, config, false));
    }

    public void start() throws IOException {
        this.server.start();
    }

    public void stop() {
        this.server.shutdownNow();
    }

    public static class ServerConfig extends ResourceConfig {

        private ServerConfig(AccountController controller) {
            register(controller);
            registerClasses(ExceptionHandler.class);
        }

        public static ServerConfig setup() {
            AccountRepository repository = new InMemoryAccountRepository();
            AccountManager manager = new AccountManager(repository);
            AccountController controller = new AccountController(manager);
            return new ServerConfig(controller);
        }

        public static ServerConfig setup(AccountController controller) {
            return new ServerConfig(controller);
        }
    }

}