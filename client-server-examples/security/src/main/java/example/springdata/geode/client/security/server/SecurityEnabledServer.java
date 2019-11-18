package example.springdata.geode.client.security.server;

import example.springdata.geode.client.security.server.config.SecurityEnabledServerConfiguration;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication(scanBasePackageClasses = SecurityEnabledServerConfiguration.class)
public class SecurityEnabledServer {
    public static void main(String[] args) {
        new SpringApplicationBuilder(SecurityEnabledServer.class)
                .web(WebApplicationType.NONE)
                .build()
                .run(args);
    }
}