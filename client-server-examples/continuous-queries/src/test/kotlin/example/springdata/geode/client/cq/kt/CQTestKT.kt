package example.springdata.geode.client.cq.kt

import example.springdata.geode.client.cq.kt.server.ServerKT
import example.springdata.geode.client.cq.kt.client.config.CQClientApplicationConfigKT
import example.springdata.geode.client.cq.kt.client.repo.CustomerRepositoryKT
import example.springdata.geode.client.cq.kt.domain.Customer
import example.springdata.geode.client.cq.kt.domain.EmailAddress
import org.apache.geode.cache.Region
import org.apache.geode.cache.query.CqEvent
import org.assertj.core.api.Assertions.assertThat
import org.awaitility.Awaitility
import org.junit.After
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.gemfire.listener.ContinuousQueryListenerContainer
import org.springframework.data.gemfire.listener.annotation.ContinuousQuery
import org.springframework.data.gemfire.tests.integration.ForkingClientServerIntegrationTestsSupport
import org.springframework.data.gemfire.util.RegionUtils
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.junit4.SpringRunner
import java.io.IOException
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger
import javax.annotation.Resource

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = [CQClientApplicationConfigKT::class])
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class CQTestKT : ForkingClientServerIntegrationTestsSupport() {

    private var counter = AtomicInteger(0)

    @Autowired
    lateinit var container: ContinuousQueryListenerContainer

    @Autowired
    lateinit var customerRepository: CustomerRepositoryKT

    @Resource(name = "Customers")
    lateinit var customers: Region<Long, Customer>

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
    }

    @Test
    fun continuousQueryWorkingCorrectly() {
        assertThat(this.customers).isEmpty()
        logger.info("Inserting 3 entries for keys: 1, 2, 3")
        customerRepository.save(Customer(1L, EmailAddress("2@2.com"), "John", "Smith"))
        customerRepository.save(Customer(2L, EmailAddress("3@3.com"), "Frank", "Lamport"))
        customerRepository.save(Customer(3L, EmailAddress("5@5.com"), "Jude", "Simmons"))
        assertThat(customers.keySetOnServer().size).isEqualTo(3)

        Awaitility.await().atMost(30, TimeUnit.SECONDS).until { this.counter.get() == 3 }
    }

    @ContinuousQuery(name = "CustomerCQ", query = "SELECT * FROM /Customers")
    fun handleEvent(event: CqEvent) {
        logger.info("Received message for CQ 'CustomerCQ'$event")
        counter.incrementAndGet()
    }

    @After
    fun tearDown() {
        container.queryService.closeCqs()
    }
}