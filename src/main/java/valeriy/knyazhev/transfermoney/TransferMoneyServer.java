package valeriy.knyazhev.transfermoney;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import valeriy.knyazhev.transfermoney.port.adapter.AccountController;

import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;

/**
 * @author Valeriy Knyazhev
 */
public class TransferMoneyServer {

    private HttpServer server;

    public void startServer(int port) throws IOException {
        ResourceConfig resourceConfig = new ResourceConfig()
            .registerClasses(AccountController.class);
        URI uri = UriBuilder.fromPath("http://localhost:" + port).build();
        this.server = GrizzlyHttpServerFactory.createHttpServer(uri, resourceConfig, false);
        this.server.start();
    }

    public void stopServer() {
        this.server.shutdownNow();
    }

}