package example.springdata.geode.client.oql.server;

import example.springdata.geode.client.oql.server.config.ServerApplicationConfig;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication(scanBasePackageClasses = ServerApplicationConfig.class)
public class Server {
    public static void main(String[] args) {
        new SpringApplicationBuilder(Server.class).web(WebApplicationType.NONE).build().run(args);
    }
}