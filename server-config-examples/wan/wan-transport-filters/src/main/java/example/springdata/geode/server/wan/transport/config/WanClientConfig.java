package example.springdata.geode.server.wan.transport.config;

import java.util.Collections;

import example.springdata.geode.server.wan.transport.domain.Customer;
import example.springdata.geode.server.wan.transport.repo.CustomerRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.client.ClientRegionFactoryBean;
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication;
import org.springframework.data.gemfire.config.annotation.ClientCacheConfigurer;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;
import org.springframework.data.gemfire.support.ConnectionEndpoint;

import org.apache.geode.cache.GemFireCache;
import org.apache.geode.cache.client.ClientRegionShortcut;

/**
 * Spring JavaConfig configuration class to setup a Spring container and infrastructure components.
 *
 * @author Udo Kohlmeyer
* @author Patrick Johnson
 */
@Configuration
@EnableGemfireRepositories(basePackageClasses = CustomerRepository.class)
@ClientCacheApplication(name = "WanClient", logLevel = "error", pingInterval = 5000L, readTimeout = 15000, retryAttempts = 1)
public class WanClientConfig {
	@Bean("Customers")
	protected ClientRegionFactoryBean<Long, Customer> configureProxyClientCustomerRegion(GemFireCache gemFireCache) {
		ClientRegionFactoryBean<Long,Customer> clientRegionFactoryBean = new ClientRegionFactoryBean<>();
		clientRegionFactoryBean.setCache(gemFireCache);
		clientRegionFactoryBean.setName("Customers");
		clientRegionFactoryBean.setShortcut(ClientRegionShortcut.PROXY);
		return clientRegionFactoryBean;
	}

	@Bean
	ClientCacheConfigurer clientCacheServerConfigurer(
			@Value("${spring.data.geode.locator.host:localhost}") String hostname,
			@Value("${spring.data.geode.locator.port:10334}") int port) {

		return (beanName, clientCacheFactoryBean) -> clientCacheFactoryBean.setLocators(Collections.singletonList(
				new ConnectionEndpoint(hostname, port)));
	}
}
