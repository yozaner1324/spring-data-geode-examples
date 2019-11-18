package example.springdata.geode.client.oql.server.config;

import example.springdata.geode.client.oql.domain.Customer;
import org.apache.geode.cache.DataPolicy;
import org.apache.geode.cache.GemFireCache;
import org.apache.geode.cache.Scope;
import org.springframework.context.annotation.Bean;
import org.springframework.data.gemfire.ReplicatedRegionFactoryBean;
import org.springframework.data.gemfire.config.annotation.CacheServerApplication;
import org.springframework.data.gemfire.config.annotation.EnableIndexing;
import org.springframework.data.gemfire.config.annotation.EnableLocator;
import org.springframework.data.gemfire.config.annotation.EnableManager;

@EnableLocator
@EnableIndexing
@EnableManager
@CacheServerApplication(port = 0, logLevel = "error")
public class ServerApplicationConfig {

    @Bean
    ReplicatedRegionFactoryBean<Long, Customer> createCustomerRegion(GemFireCache gemfireCache) {
        ReplicatedRegionFactoryBean replicatedRegionFactoryBean = new ReplicatedRegionFactoryBean();
        replicatedRegionFactoryBean.setCache(gemfireCache);
        replicatedRegionFactoryBean.setRegionName("Customers");
        replicatedRegionFactoryBean.setDataPolicy(DataPolicy.REPLICATE);
        replicatedRegionFactoryBean.setScope(Scope.DISTRIBUTED_ACK);
        return replicatedRegionFactoryBean;
    }
}