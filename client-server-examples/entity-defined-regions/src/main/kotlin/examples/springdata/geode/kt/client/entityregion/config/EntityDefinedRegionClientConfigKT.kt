package examples.springdata.geode.kt.client.entityregion.config

import examples.springdata.geode.domain.Customer
import examples.springdata.geode.kt.client.entityregion.repo.CustomerRepositoryKT
import examples.springdata.geode.kt.client.entityregion.service.CustomerServiceKT
import org.apache.geode.cache.RegionShortcut
import org.apache.geode.cache.client.ClientRegionShortcut
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.gemfire.config.annotation.*
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories
import org.springframework.data.gemfire.support.ConnectionEndpoint

@Configuration
@ClientCacheApplication
@EnableEntityDefinedRegions(basePackageClasses = [Customer::class],
        clientRegionShortcut = ClientRegionShortcut.PROXY,
        serverRegionShortcut = RegionShortcut.PARTITION)
@EnableGemfireRepositories(basePackageClasses = [CustomerRepositoryKT::class])
@ComponentScan(basePackageClasses = [CustomerServiceKT::class])
@EnableIndexing
@EnableClusterConfiguration
class EntityDefinedRegionClientConfigKT {
    @Bean
    internal fun clientCacheServerConfigurer(
            @Value("\${spring.data.geode.locator.host:localhost}") hostname: String,
            @Value("\${spring.data.geode.locator.port:10334}") port: Int) =
            ClientCacheConfigurer { _, clientCacheFactoryBean ->
                clientCacheFactoryBean.setLocators(listOf(ConnectionEndpoint(hostname, port)))
            }
}