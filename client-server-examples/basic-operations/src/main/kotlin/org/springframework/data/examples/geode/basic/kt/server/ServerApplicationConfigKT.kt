package org.springframework.data.examples.geode.basic.kt.server

import org.apache.geode.cache.CacheListener
import org.apache.geode.cache.DataPolicy
import org.apache.geode.cache.GemFireCache
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.examples.geode.model.Customer
import org.springframework.data.examples.geode.util.LoggingCacheListener
import org.springframework.data.gemfire.ReplicatedRegionFactoryBean
import org.springframework.data.gemfire.config.annotation.CacheServerApplication
import org.springframework.data.gemfire.config.annotation.EnableLocator

/**
 * The server application configuration file. This configuration file creates: LoggingCacheListener, CustomerRegion,
 * CacheServer and starts a Locator on the default host:localhost and port 10334. Which the server will use to join
 * the cluster and the client to connect to the locator to receive a connection to a registered server.
 */

@Configuration
@EnableLocator
@CacheServerApplication(port = 0)
class ServerApplicationConfigKT {
    @Bean
    internal fun loggingCacheListener() = LoggingCacheListener<String, Customer>() as CacheListener<String, Customer>

    @Bean
    internal fun customerRegion(gemfireCache: GemFireCache) = ReplicatedRegionFactoryBean<String, Customer>().apply {
        cache = gemfireCache
        setRegionName("Customers")
        dataPolicy = DataPolicy.REPLICATE
        setCacheListeners(arrayOf(loggingCacheListener()))
    }
}