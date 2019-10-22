package example.springdata.geode.client.function.kt.server.config

import example.springdata.geode.client.common.kt.server.config.ServerApplicationConfigKT
import example.springdata.geode.client.function.kt.server.functions.CustomerFunctionsKT
import example.springdata.geode.domain.Order
import example.springdata.geode.domain.Product
import org.apache.geode.cache.DataPolicy
import org.apache.geode.cache.GemFireCache
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.data.gemfire.ReplicatedRegionFactoryBean
import org.springframework.data.gemfire.function.config.EnableGemfireFunctions

@Configuration
@EnableGemfireFunctions
@ComponentScan(basePackageClasses = [CustomerFunctionsKT::class])
@Import(ServerApplicationConfigKT::class)
class FunctionServerApplicationConfigKT {

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