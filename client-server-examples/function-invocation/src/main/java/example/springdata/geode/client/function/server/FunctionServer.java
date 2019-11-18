package example.springdata.geode.client.function.server;

import example.springdata.geode.client.function.server.config.FunctionServerApplicationConfig;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication(scanBasePackageClasses = FunctionServerApplicationConfig.class)
public class FunctionServer {

    public static void main(String[] args) {
        new SpringApplicationBuilder(FunctionServer.class).web(WebApplicationType.NONE).build().run(args);
    }
}
