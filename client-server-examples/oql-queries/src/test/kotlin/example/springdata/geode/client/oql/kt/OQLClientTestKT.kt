package example.springdata.geode.client.oql.kt

import example.springdata.geode.client.oql.kt.client.config.OQLClientApplicationConfigKT
import example.springdata.geode.client.oql.kt.client.repo.CustomerRepositoryKT
import example.springdata.geode.client.oql.kt.domain.Customer
import example.springdata.geode.client.oql.kt.domain.EmailAddress
import example.springdata.geode.client.oql.kt.server.ServerKT
import org.apache.geode.cache.Region
import org.assertj.core.api.Assertions.assertThat
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.gemfire.GemfireTemplate
import org.springframework.data.gemfire.tests.integration.ForkingClientServerIntegrationTestsSupport
import org.springframework.data.gemfire.util.RegionUtils
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.junit4.SpringRunner
import java.io.IOException
import javax.annotation.Resource

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = [OQLClientApplicationConfigKT::class])
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class OQLClientTestKT : ForkingClientServerIntegrationTestsSupport() {
    @Autowired
    lateinit var customerRepository: CustomerRepositoryKT

    @Resource(name = "Customers")
    lateinit var customers: Region<Long, Customer>

    @Autowired
    lateinit var customerTemplate: GemfireTemplate

    private val logger = LoggerFactory.getLogger(this.javaClass)

    companion object {
        @BeforeClass
        @JvmStatic
        @Throws(IOException::class)
        fun setup() {
            startGemFireServer(ServerKT::class.java)
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
    fun customerRepositoryWasAutoConfiguredCorrectly() {

        logger.info("Inserting 3 entries for keys: 1, 2, 3")
        val john = Customer(1L, EmailAddress("2@2.com"), "John", "Smith")
        val frank = Customer(2L, EmailAddress("3@3.com"), "Frank", "Lamport")
        val jude = Customer(3L, EmailAddress("5@5.com"), "Jude", "Simmons")
        customerRepository.save(john)
        customerRepository.save(frank)
        customerRepository.save(jude)
        assertThat(customers.keySetOnServer().size).isEqualTo(3)

        var customer = customerRepository.findById(2L).get()
        assertThat(customer).isEqualTo(frank)
        logger.info("Find customer with key=2 using GemFireRepository: $customer")
        var customerList: List<*> = customerTemplate.find<Customer>("select * from /Customers where id=$1", 2L).asList()
        assertThat(customerList.size).isEqualTo(1)
        assertThat(customerList.contains(frank)).isTrue()
        logger.info("Find customer with key=2 using GemFireTemplate: $customerList")

        customer = Customer(1L, EmailAddress("3@3.com"), "Jude", "Smith")
        customerRepository.save(customer)
        assertThat(customers.keySetOnServer().size).isEqualTo(3)

        customerList = customerRepository.findByEmailAddressUsingIndex<Customer>("3@3.com")
        assertThat(customerList.size).isEqualTo(2)
        assertThat(customerList.contains(frank)).isTrue()
        assertThat(customerList.contains(customer)).isTrue()
        logger.info("Find customers with emailAddress=3@3.com: $customerList")

        customerList = customerRepository.findByFirstNameUsingIndex<Customer>("Frank")
        assertThat(customerList[0]).isEqualTo(frank)
        logger.info("Find customers with firstName=Frank: $customerList")
        customerList = customerRepository.findByFirstNameUsingIndex<Customer>("Jude")
        assertThat(customerList.size).isEqualTo(2)
        assertThat(customerList.contains(jude)).isTrue()
        assertThat(customerList.contains(customer)).isTrue()
        logger.info("Find customers with firstName=Jude: $customerList")
    }
}