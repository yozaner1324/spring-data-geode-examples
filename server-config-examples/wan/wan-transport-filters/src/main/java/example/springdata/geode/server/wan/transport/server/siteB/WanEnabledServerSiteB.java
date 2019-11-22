package example.springdata.geode.server.wan.transport.server.siteB;

import java.util.Scanner;

import example.springdata.geode.server.wan.transport.server.siteB.config.SiteBWanEnabledServerConfig;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackageClasses = SiteBWanEnabledServerConfig.class)
public class WanEnabledServerSiteB {
    public static void main(String[] args) {
        new SpringApplicationBuilder(WanEnabledServerSiteB.class)
                .web(WebApplicationType.NONE)
                .build()
                .run(args);
    }

    @Bean
    public ApplicationRunner siteBRunner() {
        return args -> new Scanner(System.in).nextLine();
    }
}
