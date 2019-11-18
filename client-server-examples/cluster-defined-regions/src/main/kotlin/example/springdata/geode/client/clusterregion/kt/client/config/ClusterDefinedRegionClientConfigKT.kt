package example.springdata.geode.client.clusterregion.kt.client.config

import example.springdata.geode.client.clusterregion.kt.client.repo.CustomerRepositoryKT
import org.apache.geode.cache.client.ClientRegionShortcut
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication
import org.springframework.data.gemfire.config.annotation.EnableClusterConfiguration
import org.springframework.data.gemfire.config.annotation.EnableClusterDefinedRegions
import org.springframework.data.gemfire.config.annotation.EnableIndexing
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories

@ClientCacheApplication(logLevel = "error")
@EnableClusterDefinedRegions(clientRegionShortcut = ClientRegionShortcut.PROXY)
@EnableGemfireRepositories(basePackageClasses = [CustomerRepositoryKT::class])
@EnableIndexing
@EnableClusterConfiguration
class ClusterDefinedRegionClientConfigKT