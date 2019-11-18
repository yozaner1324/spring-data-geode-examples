package example.springdata.geode.server.asyncqueues.config;

import example.springdata.geode.server.asyncqueues.domain.Customer;
import example.springdata.geode.server.asyncqueues.domain.Order;
import example.springdata.geode.server.asyncqueues.domain.Product;
import example.springdata.geode.server.asyncqueues.listener.OrderAsyncQueueListener;
import example.springdata.geode.server.asyncqueues.repo.CustomerRepository;
import org.apache.geode.cache.Cache;
import org.apache.geode.cache.DataPolicy;
import org.apache.geode.cache.GemFireCache;
import org.apache.geode.cache.PartitionAttributes;
import org.apache.geode.cache.Region;
import org.apache.geode.cache.RegionAttributes;
import org.apache.geode.cache.asyncqueue.AsyncEventListener;
import org.apache.geode.cache.asyncqueue.AsyncEventQueue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.data.gemfire.PartitionAttributesFactoryBean;
import org.springframework.data.gemfire.PartitionedRegionFactoryBean;
import org.springframework.data.gemfire.RegionAttributesFactoryBean;
import org.springframework.data.gemfire.ReplicatedRegionFactoryBean;
import org.springframework.data.gemfire.config.annotation.PeerCacheApplication;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;
import org.springframework.data.gemfire.wan.AsyncEventQueueFactoryBean;

@PeerCacheApplication(logLevel = "error")
@EnableGemfireRepositories(basePackageClasses = CustomerRepository.class)
public class AsyncQueueServerConfig {

    @Bean
    AsyncEventListener orderAsyncEventListener(@Qualifier("OrderProductSummary") Region orderProductSummary) {
        return new OrderAsyncQueueListener(orderProductSummary);
    }

    @Bean
    AsyncEventQueueFactoryBean orderAsyncEventQueue(GemFireCache gemFireCache, AsyncEventListener orderAsyncEventListener) {
        final AsyncEventQueueFactoryBean asyncEventQueueFactoryBean = new AsyncEventQueueFactoryBean((Cache) gemFireCache);
        asyncEventQueueFactoryBean.setBatchTimeInterval(1000);
        asyncEventQueueFactoryBean.setBatchSize(5);
        asyncEventQueueFactoryBean.setAsyncEventListener(orderAsyncEventListener);
        return asyncEventQueueFactoryBean;
    }

    @Bean
    RegionAttributesFactoryBean regionAttributes(PartitionAttributes partitionAttributes) {
        final RegionAttributesFactoryBean regionAttributesFactoryBean = new RegionAttributesFactoryBean();
        regionAttributesFactoryBean.setPartitionAttributes(partitionAttributes);
        return regionAttributesFactoryBean;
    }

    @Bean
    PartitionAttributesFactoryBean partitionAttributes(GemFireCache gemFireCache) {
        final PartitionAttributesFactoryBean<Long, Order> partitionAttributesFactoryBean = new PartitionAttributesFactoryBean<>();
        partitionAttributesFactoryBean.setTotalNumBuckets(13);
        partitionAttributesFactoryBean.setRedundantCopies(0);
        return partitionAttributesFactoryBean;
    }

    @Bean(name = "OrderProductSummary")
    PartitionedRegionFactoryBean createOrderProductSummaryRegion(GemFireCache gemFireCache, RegionAttributes regionAttributes) {
        final PartitionedRegionFactoryBean<Long, Order> partitionedRegionFactoryBean = new PartitionedRegionFactoryBean<>();
        partitionedRegionFactoryBean.setCache(gemFireCache);
        partitionedRegionFactoryBean.setRegionName("OrderProductSummary");
        partitionedRegionFactoryBean.setDataPolicy(DataPolicy.PARTITION);
        partitionedRegionFactoryBean.setAttributes(regionAttributes);
        return partitionedRegionFactoryBean;
    }

    @Bean("Orders")
    PartitionedRegionFactoryBean createOrderRegion(GemFireCache gemFireCache, RegionAttributes regionAttributes, AsyncEventQueue orderAsyncEventQueue) {
        final PartitionedRegionFactoryBean<Long, Order> partitionedRegionFactoryBean = new PartitionedRegionFactoryBean<>();
        partitionedRegionFactoryBean.setCache(gemFireCache);
        partitionedRegionFactoryBean.setRegionName("Orders");
        partitionedRegionFactoryBean.setDataPolicy(DataPolicy.PARTITION);
        partitionedRegionFactoryBean.setAttributes(regionAttributes);
        partitionedRegionFactoryBean.setAsyncEventQueues(new AsyncEventQueue[]{orderAsyncEventQueue});
        return partitionedRegionFactoryBean;
    }

    @Bean("Products")
    ReplicatedRegionFactoryBean createProductRegion(GemFireCache gemFireCache) {
        final ReplicatedRegionFactoryBean<Long, Product> replicatedRegionFactoryBean = new ReplicatedRegionFactoryBean<>();
        replicatedRegionFactoryBean.setCache(gemFireCache);
        replicatedRegionFactoryBean.setRegionName("Products");
        replicatedRegionFactoryBean.setDataPolicy(DataPolicy.REPLICATE);
        return replicatedRegionFactoryBean;
    }

    @Bean("Customers")
    ReplicatedRegionFactoryBean createCustomerRegion(GemFireCache gemFireCache, RegionAttributes<Long, Customer> regionAttributes) {
        final ReplicatedRegionFactoryBean<Long, Customer> replicatedRegionFactoryBean = new ReplicatedRegionFactoryBean<Long, Customer>();
        replicatedRegionFactoryBean.setCache(gemFireCache);
        replicatedRegionFactoryBean.setRegionName("Customers");
        replicatedRegionFactoryBean.setDataPolicy(DataPolicy.REPLICATE);
        return replicatedRegionFactoryBean;
    }
}