package example.springdata.geode.server.wan.event.config;

import java.io.File;
import java.util.Arrays;

import com.github.javafaker.Faker;
import example.springdata.geode.server.wan.event.domain.Customer;
import example.springdata.geode.server.wan.event.repo.CustomerRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.DiskStoreFactoryBean;
import org.springframework.data.gemfire.PartitionAttributesFactoryBean;
import org.springframework.data.gemfire.PartitionedRegionFactoryBean;
import org.springframework.data.gemfire.RegionAttributesFactoryBean;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;

import org.apache.geode.cache.DataPolicy;
import org.apache.geode.cache.GemFireCache;
import org.apache.geode.cache.PartitionAttributes;
import org.apache.geode.cache.RegionAttributes;
import org.apache.geode.cache.wan.GatewaySender;

@Configuration
@EnableGemfireRepositories(basePackageClasses = CustomerRepository.class)
public class WanEnabledServerCommonConfig {
    @Bean
    Faker faker() {
        return new Faker();
    }

    @Bean(name = "DiskStore")
    DiskStoreFactoryBean diskStore(GemFireCache gemFireCache, Faker faker) {
        final DiskStoreFactoryBean diskStoreFactoryBean = new DiskStoreFactoryBean();
        final boolean completed = new File("/tmp/" + faker.name().firstName()).mkdirs();
        final DiskStoreFactoryBean.DiskDir[] diskDirs = {new DiskStoreFactoryBean.DiskDir("/tmp/" + faker.name().firstName())};
        diskStoreFactoryBean.setDiskDirs(Arrays.asList(diskDirs));
        diskStoreFactoryBean.setCache(gemFireCache);
        return diskStoreFactoryBean;
    }

    @Bean
    RegionAttributesFactoryBean regionAttributes(PartitionAttributes partitionAttributes) {
        final RegionAttributesFactoryBean regionAttributesFactoryBean = new RegionAttributesFactoryBean();
        regionAttributesFactoryBean.setPartitionAttributes(partitionAttributes);
        return regionAttributesFactoryBean;
    }

    @Bean
    PartitionAttributesFactoryBean partitionAttributes(GemFireCache gemFireCache) {
        final PartitionAttributesFactoryBean<Long, Customer> partitionAttributesFactoryBean = new PartitionAttributesFactoryBean<>();
        partitionAttributesFactoryBean.setTotalNumBuckets(13);
        partitionAttributesFactoryBean.setRedundantCopies(0);
        return partitionAttributesFactoryBean;
    }

    @Bean("Customers")
    PartitionedRegionFactoryBean createCustomerRegion(GemFireCache gemFireCache, RegionAttributes regionAttributes, GatewaySender gatewaySender) {
        final PartitionedRegionFactoryBean<Long, Customer> partitionedRegionFactoryBean = new PartitionedRegionFactoryBean<>();
        partitionedRegionFactoryBean.setCache(gemFireCache);
        partitionedRegionFactoryBean.setRegionName("Customers");
        partitionedRegionFactoryBean.setDataPolicy(DataPolicy.PARTITION);
        partitionedRegionFactoryBean.setAttributes(regionAttributes);
        partitionedRegionFactoryBean.setGatewaySenders(new GatewaySender[]{gatewaySender});
        return partitionedRegionFactoryBean;
    }
}
