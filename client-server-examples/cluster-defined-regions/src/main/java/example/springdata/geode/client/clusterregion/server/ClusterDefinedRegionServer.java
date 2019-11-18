package example.springdata.geode.client.clusterregion.server;

import example.springdata.geode.client.clusterregion.server.config.ClusterDefinedRegionServerConfig;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication(scanBasePackageClasses = ClusterDefinedRegionServerConfig.class)
public class ClusterDefinedRegionServer {

	public static void main(String[] args) {
		new SpringApplicationBuilder(ClusterDefinedRegionServer.class)
			.web(WebApplicationType.NONE)
			.build()
			.run(args);
	}
}
