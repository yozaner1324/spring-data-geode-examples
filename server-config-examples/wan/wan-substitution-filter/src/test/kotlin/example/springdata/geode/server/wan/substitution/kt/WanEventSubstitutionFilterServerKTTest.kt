package example.springdata.geode.server.wan.substitution.kt

import example.springdata.geode.server.wan.client.config.WanClientConfig
import example.springdata.geode.server.wan.kt.domain.Customer
import org.apache.geode.cache.Region
import org.assertj.core.api.Assertions.assertThat
import org.awaitility.Awaitility
import org.junit.BeforeClass
import org.junit.Ignore
import org.junit.Test
import org.junit.jupiter.api.Disabled
import org.junit.runner.RunWith
import org.slf4j.LoggerFactory
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.gemfire.tests.integration.ForkingClientServerIntegrationTestsSupport
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.junit4.SpringRunner
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.annotation.Resource

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = [WanClientConfig::class])
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class WanEventSubstitutionFilterServerKTTest : ForkingClientServerIntegrationTestsSupport() {

    @Resource(name = "Customers")
    lateinit var customers: Region<Long, Customer>

    private val logger = LoggerFactory.getLogger(this.javaClass)

    @Ignore
    @Test
    fun wanReplicationOccursCorrectly() {
        Awaitility.await().atMost(10, TimeUnit.SECONDS).until { customers.keySetOnServer().size == 300 }
        assertThat(customers.keySetOnServer().size).isEqualTo(300)
        logger.info(customers.keySetOnServer().size.toString() + " entries replicated to siteA")

        customers.getAll(customers.keySetOnServer()).forEach { (key, value) -> assertThat(value.lastName.length).isEqualTo(1) }
        logger.info("All customers' last names changed to last initial on siteA")
    }

    companion object {

        @BeforeClass
        @JvmStatic
        @Throws(IOException::class)
        fun setup() {
            startGemFireServer(WanEventSubstitutionFilterServerKT::class.java, "-Dspring.profiles.active=SiteB")
            startGemFireServer(WanEventSubstitutionFilterServerKT::class.java, "-Dspring.profiles.active=SiteA")
            System.getProperties().remove("spring.data.gemfire.pool.servers")
        }
    }
}