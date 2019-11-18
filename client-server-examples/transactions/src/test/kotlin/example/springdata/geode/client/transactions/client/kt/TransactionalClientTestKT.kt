package example.springdata.geode.client.transactions.client.kt

import example.springdata.geode.client.transactions.domain.Customer
import example.springdata.geode.client.transactions.domain.EmailAddress
import example.springdata.geode.client.transactions.kt.client.config.TransactionalClientConfigKT
import example.springdata.geode.client.transactions.kt.client.service.CustomerServiceKT
import example.springdata.geode.client.transactions.kt.server.TransactionalServerKT
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
import javax.annotation.Resource

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = [TransactionalClientConfigKT::class])
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class TransactionalClientTestKT : ForkingClientServerIntegrationTestsSupport() {
    @Autowired
    lateinit var customerService: CustomerServiceKT

    @Resource(name = "Customers")
    lateinit var customers: Region<Long, Customer>

    private val logger = LoggerFactory.getLogger(this.javaClass)

    companion object {
        @BeforeClass
        @JvmStatic
        @Throws(IOException::class)
        fun setup() {
            startGemFireServer(TransactionalServerKT::class.java)
        }
    }

    @Test
    fun customerServiceWasConfiguredCorrectly() {

        assertThat(this.customerService).isNotNull
    }

    @Test
    fun customersRegionWasConfiguredCorrectly() {

        assertThat(this.customers).isNotNull
        assertThat(this.customers.name).isEqualTo("Customers")
        assertThat(this.customers.fullPath).isEqualTo(RegionUtils.toRegionPath("Customers"))
        assertThat(this.customers).isEmpty()
    }

    @Test
    fun repositoryWasAutoConfiguredCorrectly() {

        logger.info("Number of Entries stored before = " + customerService.numberEntriesStoredOnServer())
        customerService.createFiveCustomers()
        assertThat(customerService.numberEntriesStoredOnServer()).isEqualTo(5)
        logger.info("Number of Entries stored after = " + customerService.numberEntriesStoredOnServer())
        logger.info("Customer for ID before (transaction commit success) = " + customerService.findById(2L).get())
        customerService.updateCustomersSuccess()
        assertThat(customerService.numberEntriesStoredOnServer()).isEqualTo(5)
        var customer = customerService.findById(2L).get()
        assertThat(customer.firstName).isEqualTo("Humpty")
        logger.info("Customer for ID after (transaction commit success) = $customer")
        try {
            customerService.updateCustomersFailure()
        } catch (exception: IllegalArgumentException) {
        }

        customer = customerService.findById(2L).get()
        assertThat(customer.firstName).isEqualTo("Humpty")
        logger.info("Customer for ID after (transaction commit failure) = " + customerService.findById(2L).get())

        val numpty = Customer(2L, EmailAddress("2@2.com"), "Numpty", "Hamilton")
        val frumpy = Customer(2L, EmailAddress("2@2.com"), "Frumpy", "Hamilton")
        customerService.updateCustomersWithDelay(1000, numpty)
        customerService.updateCustomersWithDelay(10, frumpy)
        customer = customerService.findById(2L).get()
        assertThat(customer).isEqualTo(frumpy)
        logger.info("Customer for ID after 2 updates with delay = $customer")
    }
}