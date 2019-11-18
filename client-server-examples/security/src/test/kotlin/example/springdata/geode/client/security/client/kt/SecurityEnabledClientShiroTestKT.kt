package example.springdata.geode.client.security.client.kt

import example.springdata.geode.client.security.kt.client.config.SecurityEnabledClientConfigurationKT
import example.springdata.geode.client.security.kt.client.repo.CustomerRepositoryKT
import example.springdata.geode.client.security.kt.domain.Customer
import example.springdata.geode.client.security.kt.domain.EmailAddress
import example.springdata.geode.client.security.kt.server.SecurityEnabledServerKT
import org.apache.geode.cache.Region
import org.assertj.core.api.Assertions
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
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = [SecurityEnabledClientConfigurationKT::class])
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class SecurityEnabledClientShiroTestKT : ForkingClientServerIntegrationTestsSupport() {
    @Autowired
    lateinit var customerRepo: CustomerRepositoryKT

    @Resource(name = "Customers")
    lateinit var customers: Region<Long, Customer>

    private val logger = LoggerFactory.getLogger(this.javaClass)

    companion object {
        @BeforeClass
        @JvmStatic
        @Throws(IOException::class)
        fun setup() {
            startGemFireServer(SecurityEnabledServerKT::class.java, "-Dspring.profiles.active=shiro-ini-configuration")
        }
    }

    @Test
    fun customersRegionWasConfiguredCorrectly() {

        Assertions.assertThat(this.customers).isNotNull
        Assertions.assertThat(this.customers.name).isEqualTo("Customers")
        Assertions.assertThat(this.customers.fullPath).isEqualTo(RegionUtils.toRegionPath("Customers"))
        Assertions.assertThat(this.customers).isEmpty()
    }

    @Test
    fun customerRepositoryWasConfiguredCorrectly() {

        Assertions.assertThat(this.customerRepo).isNotNull
    }

    @Test
    fun securityWasAutoConfiguredCorrectly() {
        logger.info("Inserting 3 entries for keys: 1, 2, 3")
        val john = Customer(1L, EmailAddress("2@2.com"), "John", "Smith")
        val frank = Customer(2L, EmailAddress("3@3.com"), "Frank", "Lamport")
        val jude = Customer(3L, EmailAddress("5@5.com"), "Jude", "Simmons")
        customerRepo.save(john)
        customerRepo.save(frank)
        customerRepo.save(jude)
        Assertions.assertThat(customers.keySetOnServer().size).isEqualTo(3)
        logger.info("Customers saved on server:")
        val customerList = customerRepo.findAll()
        Assertions.assertThat(customerList.count()).isEqualTo(3)
        Assertions.assertThat(customerList.contains(john)).isTrue()
        Assertions.assertThat(customerList.contains(frank)).isTrue()
        Assertions.assertThat(customerList.contains(jude)).isTrue()
        customerList.forEach { customer -> logger.info("\t Entry: \n \t\t $customer") }
    }
}