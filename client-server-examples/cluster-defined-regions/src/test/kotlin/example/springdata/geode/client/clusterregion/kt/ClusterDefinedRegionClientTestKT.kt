package example.springdata.geode.client.clusterregion.kt

import example.springdata.geode.client.clusterregion.kt.client.config.ClusterDefinedRegionClientConfigKT
import example.springdata.geode.client.clusterregion.kt.client.repo.CustomerRepositoryKT
import example.springdata.geode.client.clusterregion.kt.domain.Customer
import example.springdata.geode.client.clusterregion.kt.domain.EmailAddress
import example.springdata.geode.client.clusterregion.kt.server.ClusterDefinedRegionServerKT
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
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = [ClusterDefinedRegionClientConfigKT::class])
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class ClusterDefinedRegionClientTestKT : ForkingClientServerIntegrationTestsSupport() {

    @Autowired
    lateinit var customerRepository: CustomerRepositoryKT

    @Resource(name = "Customers")
    lateinit var customers: Region<Long, Customer>

    private val logger = LoggerFactory.getLogger(ClusterDefinedRegionClientTestKT::class.java)


    companion object {

        @BeforeClass
        @Throws(IOException::class)
        @JvmStatic
        fun setup() {
            startGemFireServer(ClusterDefinedRegionServerKT::class.java)
        }
    }

    @Test
    fun customerRepositoryWasConfiguredCorrectly() {

        assertThat(this.customerRepository).isNotNull
    }

    @Test
    fun customersRegionWasConfiguredCorrectly() {

        assertThat<Long, Customer>(this.customers).isNotNull
        assertThat(this.customers.name).isEqualTo("Customers")
        assertThat(this.customers.fullPath).isEqualTo(RegionUtils.toRegionPath("Customers"))
        assertThat<Long, Customer>(this.customers).isEmpty()
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

        val localEntries = customers.keys.size
        assertThat(localEntries).isEqualTo(0)
        logger.info("Entries on Client: $localEntries")
        val serverEntries = customers.keySetOnServer().size
        assertThat(serverEntries).isEqualTo(3)
        logger.info("Entries on Server: $serverEntries")
        var all = customerRepository.findAll()
        assertThat(all.size).isEqualTo(3)
        all.forEach { customer -> logger.info("\t Entry: \n \t\t $customer") }

        logger.info("Updating entry for key: 2")
        var customer = customerRepository.findById(2L).get()
        assertThat(customer).isEqualTo(frank)
        logger.info("Entry Before: $customer")
        val sam = Customer(2L, EmailAddress("4@4.com"), "Sam", "Spacey")
        customerRepository.save(sam)
        customer = customerRepository.findById(2L).get()
        assertThat(customer).isEqualTo(sam)
        logger.info("Entry After: $customer")

        logger.info("Removing entry for key: 3")
        customerRepository.deleteById(3L)
        assertThat<Customer>(customerRepository.findById(3L)).isEmpty

        logger.info("Entries:")
        all = customerRepository.findAll()
        assertThat(all.size).isEqualTo(2)
        all.forEach { c -> logger.info("\t Entry: \n \t\t $c") }
    }
}