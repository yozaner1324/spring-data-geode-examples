package example.springdata.geode.server.eviction.kt

import example.springdata.geode.domain.Customer
import example.springdata.geode.domain.Order
import example.springdata.geode.domain.Product
import example.springdata.geode.server.eviction.kt.service.CustomerServiceKT
import example.springdata.geode.server.eviction.kt.service.OrderServiceKT
import example.springdata.geode.server.eviction.kt.service.ProductServiceKT
import org.apache.geode.cache.Region
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
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

    @Autowired
    lateinit var customerService: CustomerServiceKT

    @Autowired
    lateinit var productService: ProductServiceKT

    @Autowired
    lateinit var orderService: OrderServiceKT

    @Before
    fun clearMemory() {
        System.gc()
    }

    @Test
    fun customerRepositoryWasAutoConfiguredCorrectly() {
        assertThat(this.customerService.size()).isEqualTo(300)
    }

    @Test
    fun productRepositoryWasAutoConfiguredCorrectly() {
        assertThat(this.productService.size()).isEqualTo(300)
    }

    @Test
    fun orderRepositoryWasAutoConfiguredCorrectly() {
        val numOrders = this.orderService.size();
        println("There are $numOrders orders in the Orders region")
        assertThat(numOrders).isEqualTo(10)
    }

    @Test
    fun evictionIsConfiguredCorrectly() {
        assertThat(customers.attributes.evictionAttributes.action.isOverflowToDisk).isTrue()
        assertThat(products.attributes.evictionAttributes.action.isOverflowToDisk).isTrue()
        assertThat(orders.attributes.evictionAttributes.action.isLocalDestroy).isTrue()
    }
}