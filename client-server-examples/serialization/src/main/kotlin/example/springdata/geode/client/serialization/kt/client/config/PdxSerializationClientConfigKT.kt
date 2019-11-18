package example.springdata.geode.client.serialization.kt.client.config

import example.springdata.geode.client.serialization.domain.Customer
import example.springdata.geode.client.serialization.kt.client.repo.CustomerRepositoryKT
import org.apache.geode.cache.GemFireCache
import org.apache.geode.cache.client.ClientRegionShortcut
import org.springframework.context.annotation.Bean
import org.springframework.data.gemfire.client.ClientRegionFactoryBean
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication
import org.springframework.data.gemfire.config.annotation.EnablePdx
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories

@EnablePdx
@ClientCacheApplication(name = "PDXSerializedClient", logLevel = "error", pingInterval = 5000L, readTimeout = 15000, retryAttempts = 1)
@EnableGemfireRepositories(basePackageClasses = [CustomerRepositoryKT::class])
class PdxSerializationClientConfigKT {

    @Bean("Customers")
    protected fun configureProxyClientCustomerRegion(gemFireCache: GemFireCache) = ClientRegionFactoryBean<Long, Customer>()
        .apply {
            cache = gemFireCache
            setName("Customers")
            setShortcut(ClientRegionShortcut.PROXY)
        }
}
