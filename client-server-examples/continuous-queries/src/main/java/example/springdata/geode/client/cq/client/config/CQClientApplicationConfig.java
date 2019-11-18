package example.springdata.geode.client.cq.client.config;

import example.springdata.geode.client.cq.domain.Customer;
import example.springdata.geode.client.cq.client.repo.CustomerRepository;
import org.apache.geode.cache.GemFireCache;
import org.apache.geode.cache.client.ClientRegionShortcut;
import org.springframework.context.annotation.Bean;
import org.springframework.data.gemfire.client.ClientRegionFactoryBean;
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication;
import org.springframework.data.gemfire.config.annotation.EnableContinuousQueries;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;

@EnableGemfireRepositories(basePackageClasses = CustomerRepository.class)
@ClientCacheApplication(name = "CQClientCache", logLevel = "error", pingInterval = 5000L, readTimeout = 15000, subscriptionEnabled = true, readyForEvents = true)
@EnableContinuousQueries
public class CQClientApplicationConfig {
    @Bean("Customers")
    protected ClientRegionFactoryBean<Long, Customer> configureProxyClientCustomerRegion(GemFireCache gemFireCache) {
        ClientRegionFactoryBean<Long,Customer> clientRegionFactoryBean = new ClientRegionFactoryBean<>();
        clientRegionFactoryBean.setCache(gemFireCache);
        clientRegionFactoryBean.setName("Customers");
        clientRegionFactoryBean.setShortcut(ClientRegionShortcut.PROXY);
        return clientRegionFactoryBean;
    }
}