package example.springdata.geode.client.kt.entityregion.client.config

import example.springdata.geode.client.kt.entityregion.client.repo.CustomerRepositoryKT
import example.springdata.geode.client.kt.entityregion.domain.Customer
import org.apache.geode.cache.RegionShortcut
import org.apache.geode.cache.client.ClientRegionShortcut
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication
import org.springframework.data.gemfire.config.annotation.EnableClusterConfiguration
import org.springframework.data.gemfire.config.annotation.EnableEntityDefinedRegions
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories

@ClientCacheApplication(logLevel = "error")
@EnableEntityDefinedRegions(basePackageClasses = [Customer::class],
        clientRegionShortcut = ClientRegionShortcut.PROXY,
        serverRegionShortcut = RegionShortcut.PARTITION)
@EnableGemfireRepositories(basePackageClasses = [CustomerRepositoryKT::class])
@EnableClusterConfiguration
class EntityDefinedRegionClientConfigKT {
}