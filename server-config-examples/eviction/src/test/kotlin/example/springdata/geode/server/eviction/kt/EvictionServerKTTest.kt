package example.springdata.geode.server.eviction.kt

import example.springdata.geode.server.eviction.kt.domain.Customer
import example.springdata.geode.server.eviction.kt.domain.Order
import example.springdata.geode.server.eviction.kt.domain.Product
import org.apache.geode.cache.Region
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.slf4j.LoggerFactory
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.junit4.SpringRunner
import javax.annotation.Resource

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = [EvictionServerKT::class])
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class EvictionServerKTTest {

    @Resource(name = "Customers")
    lateinit var customers: Region<Long, Customer>

    @Resource(name = "Products")
    lateinit var products: Region<Long, Product>

    @Resource(name = "Orders")
    lateinit var orders: Region<Long, Order>

    private val logger = LoggerFactory.getLogger(this.javaClass)

    @Before
    fun clearMemory() {
        System.gc()
    }

    @Test
    fun customerRepositoryWasAutoConfiguredCorrectly() {
        assertThat(this.customers.size).isEqualTo(300)
    }

    @Test
    fun productRepositoryWasAutoConfiguredCorrectly() {
        assertThat(this.products.size).isEqualTo(300)
    }

    @Test
    fun orderRepositoryWasAutoConfiguredCorrectly() {
        val numOrders = this.orders.size
        logger.info("There are $numOrders orders in the Orders region")
        assertThat(numOrders).isEqualTo(10)
    }

    @Test
    fun evictionIsConfiguredCorrectly() {
        assertThat(customers.attributes.evictionAttributes.action.isOverflowToDisk).isTrue()
        assertThat(products.attributes.evictionAttributes.action.isOverflowToDisk).isTrue()
        assertThat(orders.attributes.evictionAttributes.action.isLocalDestroy).isTrue()
    }
}