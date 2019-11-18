package example.springdata.geode.client.function.client.kt

import example.springdata.geode.client.function.kt.client.config.FunctionInvocationClientApplicationConfigKT
import example.springdata.geode.client.function.kt.client.functions.CustomerFunctionExecutionsKT
import example.springdata.geode.client.function.kt.client.functions.OrderFunctionExecutionsKT
import example.springdata.geode.client.function.kt.client.functions.ProductFunctionExecutionsKT
import example.springdata.geode.client.function.kt.client.repo.CustomerRepositoryKT
import example.springdata.geode.client.function.kt.client.repo.OrderRepositoryKT
import example.springdata.geode.client.function.kt.client.repo.ProductRepositoryKT
import example.springdata.geode.client.function.kt.domain.*
import example.springdata.geode.client.function.kt.server.FunctionServerKT
import org.apache.geode.cache.Region
import org.assertj.core.api.Assertions.assertThat
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.gemfire.tests.integration.ForkingClientServerIntegrationTestsSupport
import org.springframework.data.gemfire.util.RegionUtils
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.junit4.SpringRunner
import java.io.IOException
import java.math.BigDecimal
import java.util.*
import java.util.stream.IntStream
import java.util.stream.LongStream
import javax.annotation.Resource

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = [FunctionInvocationClientApplicationConfigKT::class])
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class FunctionInvocationClientTestKT : ForkingClientServerIntegrationTestsSupport() {
    @Autowired
    lateinit var customerRepository: CustomerRepositoryKT

    @Autowired
    lateinit var orderRepository: OrderRepositoryKT

    @Autowired
    lateinit var productRepository: ProductRepositoryKT

    @Resource(name = "Customers")
    lateinit var customers: Region<Long, Customer>

    @Resource(name = "Orders")
    lateinit var orders: Region<Long, Order>

    @Resource(name = "Products")
    lateinit var products: Region<Long, Product>

    @Autowired
    lateinit var customerFunctionExecutions: CustomerFunctionExecutionsKT

    @Autowired
    lateinit var orderFunctionExecutions: OrderFunctionExecutionsKT

    @Autowired
    lateinit var productFunctionExecutions: ProductFunctionExecutionsKT

    private val logger = LoggerFactory.getLogger(this.javaClass)

    companion object {
        @BeforeClass
        @JvmStatic
        @Throws(IOException::class)
        fun setup() {
            startGemFireServer(FunctionServerKT::class.java)
        }
    }

    @Test
    fun customerRepositoryWasConfiguredCorrectly() {

        assertThat(this.customerRepository).isNotNull
    }

    @Test
    fun customersRegionWasConfiguredCorrectly() {

        assertThat(this.customers).isNotNull
        assertThat(this.customers.name).isEqualTo("Customers")
        assertThat(this.customers.fullPath).isEqualTo(RegionUtils.toRegionPath("Customers"))
        assertThat(this.customers).isEmpty()
    }

    @Test
    fun ordersRegionWasConfiguredCorrectly() {

        assertThat(this.orders).isNotNull
        assertThat(this.orders.name).isEqualTo("Orders")
        assertThat(this.orders.fullPath).isEqualTo(RegionUtils.toRegionPath("Orders"))
        assertThat(this.orders).isEmpty()
    }

    @Test
    fun productsRegionWasConfiguredCorrectly() {

        assertThat(this.products).isNotNull
        assertThat(this.products.name).isEqualTo("Products")
        assertThat(this.products.fullPath).isEqualTo(RegionUtils.toRegionPath("Products"))
        assertThat(this.products).isEmpty()
    }

    @Test
    fun orderRepositoryWasConfiguredCorrectly() {

        assertThat(this.orderRepository).isNotNull
    }

    @Test
    fun productRepositoryWasConfiguredCorrectly() {

        assertThat(this.productRepository).isNotNull
    }

    @Test
    fun functionsExecuteCorrectly() {
        createCustomerData()

        val cust = customerFunctionExecutions.listAllCustomersForEmailAddress("2@2.com", "3@3.com")[0]
        assertThat(cust.size).isEqualTo(2)
        logger.info("All customers for emailAddresses:3@3.com,2@2.com using function invocation: \n\t $cust")

        createProducts()
        var sum = productFunctionExecutions.sumPricesForAllProducts()[0]
        assertThat(sum).isEqualTo(BigDecimal.valueOf(1499.97))
        logger.info("Running function to sum up all product prices: \n\t$sum")

        createOrders()

        sum = orderFunctionExecutions.sumPricesForAllProductsForOrder(1L)[0]
        assertThat(sum).isGreaterThanOrEqualTo(BigDecimal.valueOf(99.99))
        logger.info("Running function to sum up all order lineItems prices for order 1: \n\t$sum")
        val order = orderRepository.findById(1L)
        logger.info("For order: \n\t $order")
    }

    fun createCustomerData() {

        logger.info("Inserting 3 entries for keys: 1, 2, 3")
        customerRepository.save(Customer(1L, EmailAddress("2@2.com"), "John", "Smith"))
        customerRepository.save(Customer(2L, EmailAddress("3@3.com"), "Frank", "Lamport"))
        customerRepository.save(Customer(3L, EmailAddress("5@5.com"), "Jude", "Simmons"))
        assertThat(customers.keySetOnServer().size).isEqualTo(3)
    }

    fun createProducts() {
        productRepository.save(Product(1L, "Apple iPod", BigDecimal("99.99"),
                "An Apple portable music player"))
        productRepository.save(Product(2L, "Apple iPad", BigDecimal("499.99"),
                "An Apple tablet device"))
        val macbook = Product(3L, "Apple macBook", BigDecimal("899.99"),
                "An Apple notebook computer")
        macbook.addAttribute("warranty", "included")
        productRepository.save(macbook)
        assertThat(products.keySetOnServer().size).isEqualTo(3)
    }

    fun createOrders() {
        val random = Random()
        val address = Address("it", "doesn't", "matter")
        LongStream.rangeClosed(1, 100).forEach { orderId ->
            LongStream.rangeClosed(1, 3).forEach { customerId ->
                val order = Order(orderId, customerId, address)
                IntStream.rangeClosed(0, random.nextInt(3) + 1).forEach { lineItemCount ->
                    val quantity = random.nextInt(3) + 1
                    val productId = (random.nextInt(3) + 1).toLong()
                    order.add(LineItem(productRepository.findById(productId).get(), quantity))
                }
                orderRepository.save(order)
            }
        }
        assertThat(orders.keySetOnServer().size).isEqualTo(100)
    }
}