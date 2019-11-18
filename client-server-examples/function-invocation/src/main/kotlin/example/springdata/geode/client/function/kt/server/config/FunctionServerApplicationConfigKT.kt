package example.springdata.geode.client.function.kt.server.config

import example.springdata.geode.client.function.kt.domain.Customer
import example.springdata.geode.client.function.kt.domain.Order
import example.springdata.geode.client.function.kt.domain.Product
import example.springdata.geode.client.function.kt.server.functions.CustomerFunctionsKT
import org.apache.geode.cache.DataPolicy
import org.apache.geode.cache.GemFireCache
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.gemfire.ReplicatedRegionFactoryBean
import org.springframework.data.gemfire.config.annotation.CacheServerApplication
import org.springframework.data.gemfire.function.config.EnableGemfireFunctions

@Configuration
@EnableGemfireFunctions
@ComponentScan(basePackageClasses = [CustomerFunctionsKT::class])
@CacheServerApplication(name = "FunctionServer", logLevel = "error")
class FunctionServerApplicationConfigKT {

    @Bean("Customers")
    protected fun configureProxyClientCustomerRegion(gemFireCache: GemFireCache) =
            ReplicatedRegionFactoryBean<Long, Customer>()
            .apply {
                cache = gemFireCache
                setName("Customers")
                dataPolicy = DataPolicy.REPLICATE
            }

    @Bean("Products")
    protected fun productRegion(gemfireCache: GemFireCache) =
            ReplicatedRegionFactoryBean<Long, Product>().apply {
                cache = gemfireCache
                setRegionName("Products")
                dataPolicy = DataPolicy.REPLICATE
            }

    @Bean("Orders")
    protected fun orderRegion(gemfireCache: GemFireCache) =
            ReplicatedRegionFactoryBean<Long, Order>().apply {
                cache = gemfireCache
                setRegionName("Orders")
                dataPolicy = DataPolicy.REPLICATE
            }
}