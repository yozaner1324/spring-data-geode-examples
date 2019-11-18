package example.springdata.geode.client.transactions.server;

import example.springdata.geode.client.transactions.server.config.TransactionalServerConfig;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication(scanBasePackageClasses = TransactionalServerConfig.class)
public class TransactionalServer {
    public static void main(String[] args) {
        new SpringApplicationBuilder(TransactionalServer.class)
            .web(WebApplicationType.NONE)
            .build()
            .run(args);
    }
}