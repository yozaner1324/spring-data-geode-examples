package example.springdata.geode.client.entityregion.server;

import example.springdata.geode.client.entityregion.server.config.EntityDefinedRegionServerConfig;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication(scanBasePackageClasses = EntityDefinedRegionServerConfig.class)
public class EntityDefinedRegionServer {
    public static void main(String[] args) {
        new SpringApplicationBuilder(EntityDefinedRegionServer.class)
            .web(WebApplicationType.NONE)
            .build()
            .run(args);
    }
}