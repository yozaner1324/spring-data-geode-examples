package example.springdata.geode.client.serialization.kt.server.config

import org.apache.geode.cache.DataPolicy
import org.apache.geode.cache.GemFireCache
import org.apache.geode.cache.Scope
import org.apache.geode.pdx.PdxInstance
import org.springframework.context.annotation.Bean
import org.springframework.data.gemfire.ReplicatedRegionFactoryBean
import org.springframework.data.gemfire.config.annotation.CacheServerApplication
import org.springframework.data.gemfire.config.annotation.EnableLocator
import org.springframework.data.gemfire.config.annotation.EnablePdx

/**
 * The server application configuration file. This configuration file creates: LoggingCacheListener, CustomerRegion,
 * CacheServer and starts a Locator on the default host:localhost and port 10334. Which the server will use to join
 * the cluster and the client to connect to the locator to receive a connection to a registered server.
 */

@EnableLocator
@EnablePdx
@CacheServerApplication(port = 0, logLevel = "error")
class ServerApplicationConfigKT {

    @Bean("Customers")
    protected fun customerRegion(gemfireCache: GemFireCache) =
            ReplicatedRegionFactoryBean<Long, PdxInstance>().apply {
                cache = gemfireCache
                setRegionName("Customers")
                scope = Scope.DISTRIBUTED_ACK
                dataPolicy = DataPolicy.REPLICATE
            }
}