package example.springdata.geode.functions.cascading.client.config;

import example.springdata.geode.functions.cascading.kt.domain.Customer;
import example.springdata.geode.functions.cascading.kt.domain.Order;
import example.springdata.geode.functions.cascading.kt.domain.Product;
import example.springdata.geode.functions.cascading.client.functions.CustomerFunctionExecutions;
import example.springdata.geode.functions.cascading.client.repo.CustomerRepository;
import org.apache.geode.cache.GemFireCache;
import org.apache.geode.cache.client.ClientRegionShortcut;
import org.springframework.context.annotation.Bean;
import org.springframework.data.gemfire.client.ClientRegionFactoryBean;
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication;
import org.springframework.data.gemfire.function.config.EnableGemfireFunctionExecutions;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;
import org.springframework.data.gemfire.transaction.config.EnableGemfireCacheTransactions;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@ClientCacheApplication(name = "CascadingFunctionClientCache", logLevel = "error", pingInterval = 5000L, readTimeout = 15000, retryAttempts = 1)
@EnableGemfireFunctionExecutions(basePackageClasses = CustomerFunctionExecutions.class)
@EnableGemfireRepositories(basePackageClasses = CustomerRepository.class)
@EnableGemfireCacheTransactions
@EnableTransactionManagement
public class CascadingFunctionClientConfig {
    @Bean("Customers")
    protected ClientRegionFactoryBean<Long, Customer> customerRegion(GemFireCache gemfireCache) {
        ClientRegionFactoryBean<Long, Customer> regionFactoryBean = new ClientRegionFactoryBean<>();
        regionFactoryBean.setCache(gemfireCache);
        regionFactoryBean.setRegionName("Customers");
        regionFactoryBean.setShortcut(ClientRegionShortcut.PROXY);
        return regionFactoryBean;
    }

    @Bean("Orders")
    protected ClientRegionFactoryBean<Long, Order> orderRegion(GemFireCache gemfireCache) {
        ClientRegionFactoryBean<Long, Order> regionFactoryBean = new ClientRegionFactoryBean<>();
        regionFactoryBean.setCache(gemfireCache);
        regionFactoryBean.setRegionName("Orders");
        regionFactoryBean.setShortcut(ClientRegionShortcut.PROXY);
        return regionFactoryBean;
    }

    @Bean("Products")
    protected ClientRegionFactoryBean<Long, Product> productsRegion(GemFireCache gemfireCache) {
        ClientRegionFactoryBean<Long, Product> regionFactoryBean = new ClientRegionFactoryBean<>();
        regionFactoryBean.setCache(gemfireCache);
        regionFactoryBean.setRegionName("Products");
        regionFactoryBean.setShortcut(ClientRegionShortcut.PROXY);
        return regionFactoryBean;
    }
}