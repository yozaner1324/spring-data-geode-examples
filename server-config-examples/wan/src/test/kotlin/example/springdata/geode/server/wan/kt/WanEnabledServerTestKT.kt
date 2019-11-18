package example.springdata.geode.server.wan.kt

import example.springdata.geode.server.wan.kt.client.config.WanClientConfigKT
import example.springdata.geode.server.wan.kt.domain.Customer
import example.springdata.geode.server.wan.kt.server.siteA.WanEnabledServerSiteAKT
import example.springdata.geode.server.wan.kt.server.siteB.WanEnabledServerSiteBKT
import org.apache.geode.cache.Region
import org.assertj.core.api.Assertions
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
import java.util.concurrent.TimeUnit
import javax.annotation.Resource

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = [WanClientConfigKT::class])
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class WanEnabledServerTestKT : ForkingClientServerIntegrationTestsSupport() {

    @Resource(name = "Customers")
    lateinit var customers: Region<Long, Customer>

    private val logger = LoggerFactory.getLogger(this.javaClass)

    @Ignore
    @Test
    fun wanReplicationOccurs() {
        Awaitility.await().atMost(10, TimeUnit.SECONDS).until { customers.keySetOnServer().size == 301 }
        Assertions.assertThat(customers.keySetOnServer().size).isEqualTo(301)
        logger.info(customers.keySetOnServer().size.toString() + " entries replicated to siteB")
    }

    companion object {
        @BeforeClass
        @JvmStatic
        fun setup() {
            startGemFireServer(WanEnabledServerSiteBKT::class.java)
            startGemFireServer(WanEnabledServerSiteAKT::class.java)
            System.getProperties().remove("spring.data.gemfire.pool.servers")
        }
    }
}