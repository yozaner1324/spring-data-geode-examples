package example.springdata.geode.server.expiration.cache.kt

import com.github.javafaker.Faker
import com.jayway.awaitility.Awaitility
import example.springdata.geode.server.expiration.cache.kt.domain.Address
import example.springdata.geode.server.expiration.cache.kt.domain.Customer
import example.springdata.geode.server.expiration.cache.kt.domain.EmailAddress
import example.springdata.geode.server.expiration.cache.kt.repo.CustomerRepositoryKT
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.junit4.SpringRunner
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = [CacheDefinedExpirationServerKT::class])
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class CacheDefinedExpirationServerKTTest {

    @Autowired
    lateinit var customerRepository: CustomerRepositoryKT

    @Autowired
    lateinit var faker: Faker

    private val logger = LoggerFactory.getLogger(this.javaClass)

    @Test
    fun evictionIsConfiguredCorrectly() {
        customerRepository.save(Customer(1L, EmailAddress(faker.internet().emailAddress()),
                faker.name().firstName(), faker.name().lastName(),
                Address(faker.address().streetAddress(), faker.address().city(), faker.address().country())))

        assertThat(customerRepository.count()).isEqualTo(1)

        val conditionEvaluator = { !customerRepository.findById(1L).isPresent }

        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy hh:mm:ss:SSS")
        logger.info("Starting TTL wait period: ${simpleDateFormat.format(Date())}")
        //Due to the constant "getting" of the entry, the idle expiry timeout will not be met and the time-to-live
        // will be used.
        Awaitility.await()
                .pollInterval(1, TimeUnit.SECONDS)
                .atMost(10, TimeUnit.SECONDS)
                .until<Any>(conditionEvaluator)

        assertThat(customerRepository.count()).isEqualTo(0)

        logger.info("Ending TTL wait period: ${simpleDateFormat.format(Date())}")

        customerRepository.save(Customer(1L, EmailAddress(faker.internet().emailAddress()),
                faker.name().firstName(), faker.name().lastName(),
                Address(faker.address().streetAddress(), faker.address().city(), faker.address().country())))

        assertThat(customerRepository.count()).isEqualTo(1)

        logger.info("Starting Idle wait period: ${simpleDateFormat.format(Date())}")

        //Due to the delay in "getting" the entry, the idle timeout of 2s should delete the entry.
        Awaitility.await()
                .pollDelay(2, TimeUnit.SECONDS)
                .pollInterval(100, TimeUnit.MILLISECONDS)
                .atMost(10, TimeUnit.SECONDS)
                .until<Any>(conditionEvaluator)

        assertThat(customerRepository.count()).isEqualTo(0)

        logger.info("Ending Idle wait period:${simpleDateFormat.format(Date())}")
    }
}