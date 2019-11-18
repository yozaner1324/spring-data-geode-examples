package example.springdata.geode.client.basic.server;

import example.springdata.geode.client.basic.server.config.ServerApplicationConfig;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication(scanBasePackageClasses = ServerApplicationConfig.class)
public class Server {
    public static void main(String[] args) {
        new SpringApplicationBuilder(Server.class).web(WebApplicationType.NONE).build().run(args);
    }
}