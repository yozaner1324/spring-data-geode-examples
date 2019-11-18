package example.springdata.geode.server.wan.event.kt

import com.github.javafaker.Faker
import example.springdata.geode.server.wan.event.kt.config.WanEventFiltersConfigKT
import example.springdata.geode.server.wan.kt.domain.Customer
import example.springdata.geode.server.wan.kt.domain.EmailAddress
import example.springdata.geode.server.wan.kt.repo.CustomerRepositoryKT
import org.slf4j.LoggerFactory
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.WebApplicationType
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Profile
import java.util.stream.LongStream

@SpringBootApplication(scanBasePackageClasses = [WanEventFiltersConfigKT::class])
class WanEventFilteringServerKT {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    @Bean
    @Profile("default", "SiteA")
    fun siteARunner() = ApplicationRunner {
        readLine()
    }

    @Bean
    @Profile("SiteB")
    fun siteBRunner(customerRepository: CustomerRepositoryKT) = ApplicationRunner {
        logger.info("Inserting 300 customers")
        createCustomers(customerRepository)
    }

    private fun createCustomers(repository: CustomerRepositoryKT) {
        val faker = Faker()
        val fakerName = faker.name()
        val fakerInternet = faker.internet()
        LongStream.range(0, 300).forEach { index ->
            repository.save(Customer(index, EmailAddress(fakerInternet.emailAddress()), fakerName.firstName(), fakerName.lastName()))
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplicationBuilder(WanEventFilteringServerKT::class.java)
                    .web(WebApplicationType.NONE)
                    .build()
                    .run(*args)
        }
    }
}