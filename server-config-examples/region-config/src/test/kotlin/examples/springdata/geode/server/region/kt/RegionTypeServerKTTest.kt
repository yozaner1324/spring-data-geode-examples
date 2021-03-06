package examples.springdata.geode.server.region.kt

import examples.springdata.geode.domain.Customer
import examples.springdata.geode.domain.Order
import examples.springdata.geode.domain.Product
import examples.springdata.geode.server.region.kt.repo.CustomerRepositoryKT
import examples.springdata.geode.server.region.kt.repo.OrderRepositoryKT
import examples.springdata.geode.server.region.kt.repo.ProductRepositoryKT
import org.apache.geode.cache.Region
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.gemfire.util.RegionUtils
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import javax.annotation.Resource

@ActiveProfiles("dataPopulationPeer")
@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = [RegionTypeServerKT::class])
class RegionTypeServerKTTest {
    @Resource(name = "Customers")
    private val customers: Region<Long, Customer>? = null

    @Resource(name = "Orders")
    private val orders: Region<Long, Order>? = null

    @Resource(name = "Products")
    private val products: Region<Long, Product>? = null

    @Autowired
    private val customerRepository: CustomerRepositoryKT? = null

    @Autowired
    private val orderRepository: OrderRepositoryKT? = null

    @Autowired
    private val productRepository: ProductRepositoryKT? = null

    @Test
    fun customersRegionWasConfiguredCorrectly() {

        assertThat(this.customers).isNotNull
        assertThat(this.customers!!.name).isEqualTo("Customers")
        assertThat(this.customers.fullPath).isEqualTo(RegionUtils.toRegionPath("Customers"))
        assertThat(this.customers).isNotEmpty
        assertThat(this.customers.attributes.partitionAttributes.totalNumBuckets).isEqualTo(13)
        assertThat(this.customers.attributes.partitionAttributes.redundantCopies).isEqualTo(1)
    }

    @Test
    fun ordersRegionWasConfiguredCorrectly() {

        assertThat(this.orders).isNotNull
        assertThat(this.orders!!.name).isEqualTo("Orders")
        assertThat(this.orders.fullPath).isEqualTo(RegionUtils.toRegionPath("Orders"))
        assertThat(this.orders).isNotEmpty
    }

    @Test
    fun productsRegionWasConfiguredCorrectly() {

        assertThat(this.products).isNotNull
        assertThat(this.products!!.name).isEqualTo("Products")
        assertThat(this.products.fullPath).isEqualTo(RegionUtils.toRegionPath("Products"))
        assertThat(this.products).isNotEmpty
    }

    @Test
    fun customerRepositoryWasAutoConfiguredCorrectly() {

        assertThat(this.customerRepository!!.count()).isEqualTo(3001)
    }

    @Test
    fun productRepositoryWasAutoConfiguredCorrectly() {

        assertThat(this.productRepository!!.count()).isEqualTo(3)
    }

    @Test
    fun orderRepositoryWasAutoConfiguredCorrectly() {

        assertThat(this.orderRepository!!.count()).isEqualTo(100)
    }
}