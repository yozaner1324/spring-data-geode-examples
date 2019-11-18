package example.springdata.geode.client.entityregion.kt.client

import example.springdata.geode.client.kt.entityregion.client.config.EntityDefinedRegionClientConfigKT
import example.springdata.geode.client.kt.entityregion.client.repo.CustomerRepositoryKT
import example.springdata.geode.client.kt.entityregion.server.EntityDefinedRegionServerKT
import example.springdata.geode.client.kt.entityregion.domain.Customer
import example.springdata.geode.client.kt.entityregion.domain.EmailAddress
import org.apache.geode.cache.Region
import org.assertj.core.api.Assertions.assertThat
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.gemfire.tests.integration.ForkingClientServerIntegrationTestsSupport
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.junit4.SpringRunner
import javax.annotation.Resource

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = [EntityDefinedRegionClientConfigKT::class])
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class EntityDefinedRegionClientTestKT : ForkingClientServerIntegrationTestsSupport() {
    @Autowired
    lateinit var customerRepository: CustomerRepositoryKT

    @Resource(name = "Customers")
    lateinit var customers: Region<Long, Customer>

    private val logger = LoggerFactory.getLogger(this.javaClass)

    companion object {
        @BeforeClass
        @JvmStatic
        fun setup() {
            startGemFireServer(EntityDefinedRegionServerKT::class.java)
        }
    }

    @Test
    fun customerRepositoryWasConfiguredCorrectly() {

        assertThat(this.customerRepository).isNotNull
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