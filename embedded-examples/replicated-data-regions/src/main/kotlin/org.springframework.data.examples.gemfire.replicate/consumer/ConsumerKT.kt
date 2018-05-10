package org.springframework.data.examples.gemfire.replicate.consumer

import org.apache.geode.cache.CacheListener
import org.apache.geode.cache.DataPolicy
import org.apache.geode.cache.GemFireCache
import org.apache.geode.cache.Region
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.data.gemfire.ReplicatedRegionFactoryBean
import org.springframework.data.gemfire.config.annotation.EnableLocator
import org.springframework.data.gemfire.config.annotation.PeerCacheApplication
import org.springframework.data.gemfire.examples.domain.Customer
import org.springframework.data.gemfire.examples.util.LoggingCacheListener
import javax.annotation.Resource

@SpringBootApplication
@PeerCacheApplication(name = "ConsumerPeer")
@EnableLocator(port = 10334, host = "localhost")
class ConsumerKT {

    @Resource
    private lateinit var customerRegion: Region<String, Customer>

    @Bean
    internal fun loggingCacheListener() = LoggingCacheListener<String, Customer>() as CacheListener<String, Customer>

    @Bean("customerRegion")
    fun customerRegion(gemfireCache: GemFireCache) =
        ReplicatedRegionFactoryBean<String, Customer>().apply {
            cache = gemfireCache
            setRegionName("customerRegion")
            setCacheListeners(arrayOf(loggingCacheListener()))
            dataPolicy = DataPolicy.REPLICATE
        }
}


fun main(args: Array<String>) {
    SpringApplication.run(ConsumerKT::class.java, *args)
    System.`in`.read()
}