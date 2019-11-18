package example.springdata.geode.client.transactions.server.config;

import example.springdata.geode.client.transactions.domain.Customer;
import example.springdata.geode.client.transactions.server.utils.CustomerTransactionListener;
import example.springdata.geode.client.transactions.server.utils.CustomerTransactionWriter;
import org.apache.geode.cache.DataPolicy;
import org.apache.geode.cache.GemFireCache;
import org.apache.geode.cache.Scope;
import org.apache.geode.cache.TransactionListener;
import org.apache.geode.cache.TransactionWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.data.gemfire.ReplicatedRegionFactoryBean;
import org.springframework.data.gemfire.config.annotation.CacheServerApplication;
import org.springframework.data.gemfire.config.annotation.EnableClusterConfiguration;
import org.springframework.data.gemfire.config.annotation.EnableHttpService;
import org.springframework.data.gemfire.config.annotation.EnableLocator;
import org.springframework.data.gemfire.config.annotation.EnableManager;
import org.springframework.data.gemfire.config.annotation.PeerCacheConfigurer;
import org.springframework.data.gemfire.transaction.config.EnableGemfireCacheTransactions;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Collections;

@EnableLocator
@EnableTransactionManagement
@EnableGemfireCacheTransactions
@EnableClusterConfiguration(useHttp = true)
@EnableManager(start = true)
@EnableHttpService
@CacheServerApplication
public class TransactionalServerConfig {

    @Bean
    TransactionWriter customerTransactionWriter() {
        return new CustomerTransactionWriter();
    }

    @Bean
    TransactionListener customerTransactionListener() {
        return new CustomerTransactionListener();
    }

    @Bean
    PeerCacheConfigurer transactionListenerRegistrationConfigurer(TransactionWriter customerTransactionWriter, TransactionListener customerTransactionListener) {
        return (beanName, peerCacheFactoryBean) ->
        {
            peerCacheFactoryBean.setTransactionListeners(Collections.singletonList(customerTransactionListener));
            peerCacheFactoryBean.setTransactionWriter(customerTransactionWriter);
        };
    }

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
