package example.springdata.geode.functions.cascading.server.config;

import example.springdata.geode.functions.cascading.kt.domain.Customer;
import example.springdata.geode.functions.cascading.kt.domain.Order;
import example.springdata.geode.functions.cascading.kt.domain.Product;
import example.springdata.geode.functions.cascading.server.functions.CascadingFunctions;
import org.apache.geode.cache.DataPolicy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.gemfire.PartitionedRegionFactoryBean;
import org.apache.geode.cache.GemFireCache;
import org.springframework.data.gemfire.config.annotation.CacheServerApplication;
import org.springframework.data.gemfire.config.annotation.EnableLocator;
import org.springframework.data.gemfire.function.config.EnableGemfireFunctions;

@CacheServerApplication(copyOnRead = true, port = 0, locators = "localhost[10334]", logLevel = "error")
@EnableGemfireFunctions
@EnableLocator()
@Import(CascadingFunctions.class)
public class CascadingFunctionServerConfig {

    @Bean("Customers")
    protected PartitionedRegionFactoryBean<Long, Customer> customerRegion(GemFireCache gemfireCache) {
        PartitionedRegionFactoryBean<Long, Customer> regionFactoryBean = new PartitionedRegionFactoryBean<>();
        regionFactoryBean.setCache(gemfireCache);
        regionFactoryBean.setRegionName("Customers");
        regionFactoryBean.setDataPolicy(DataPolicy.PARTITION);
        return regionFactoryBean;
    }

    @Bean("Orders")
    protected PartitionedRegionFactoryBean<Long, Order> orderRegion(GemFireCache gemfireCache) {
        PartitionedRegionFactoryBean<Long, Order> regionFactoryBean = new PartitionedRegionFactoryBean<>();
        regionFactoryBean.setCache(gemfireCache);
        regionFactoryBean.setRegionName("Orders");
        regionFactoryBean.setDataPolicy(DataPolicy.PARTITION);
        return regionFactoryBean;
    }

    @Bean("Products")
    protected PartitionedRegionFactoryBean<Long, Product> productsRegion(GemFireCache gemfireCache) {
        PartitionedRegionFactoryBean<Long, Product> regionFactoryBean = new PartitionedRegionFactoryBean<>();
        regionFactoryBean.setCache(gemfireCache);
        regionFactoryBean.setRegionName("Products");
        regionFactoryBean.setDataPolicy(DataPolicy.PARTITION);
        return regionFactoryBean;
    }
}