package example.springdata.geode.client.transactions.kt.server.config

import example.springdata.geode.client.transactions.domain.Customer
import example.springdata.geode.client.transactions.kt.server.utils.CustomerTransactionListener
import example.springdata.geode.client.transactions.kt.server.utils.CustomerTransactionWriter
import org.apache.geode.cache.DataPolicy
import org.apache.geode.cache.GemFireCache
import org.apache.geode.cache.Scope
import org.apache.geode.cache.TransactionListener
import org.springframework.context.annotation.Bean
import org.springframework.data.gemfire.ReplicatedRegionFactoryBean
import org.springframework.data.gemfire.config.annotation.*
import org.springframework.data.gemfire.transaction.config.EnableGemfireCacheTransactions
import org.springframework.transaction.annotation.EnableTransactionManagement

@EnableLocator(host = "localhost", port = 10334)
@EnableTransactionManagement
@EnableGemfireCacheTransactions()
@EnableClusterConfiguration(useHttp = true)
@EnableManager(start = true)
@EnableHttpService
@CacheServerApplication(port = 0, autoStartup = true, logLevel = "error")
class TransactionalServerConfigKT {

    @Bean
    internal fun customerTransactionListener() = CustomerTransactionListener()

    @Bean
    internal fun customerTransactionWriter() = CustomerTransactionWriter()

    @Bean
    fun transactionListenerRegistrationConfigurer(customerTransactionWriter: CustomerTransactionWriter,
                                                  customerTransactionListener: TransactionListener): PeerCacheConfigurer =
            PeerCacheConfigurer { _, peerCacheFactoryBean ->
                peerCacheFactoryBean.transactionListeners = listOf(customerTransactionListener)
                peerCacheFactoryBean.transactionWriter = customerTransactionWriter
            }

    @Bean
    fun createCustomerRegion(gemfireCache: GemFireCache): ReplicatedRegionFactoryBean<Long, Customer> =
            ReplicatedRegionFactoryBean<Long, Customer>().apply {
                cache = gemfireCache
                setRegionName("Customers")
                dataPolicy = DataPolicy.REPLICATE
                scope = Scope.DISTRIBUTED_ACK
            }
}
