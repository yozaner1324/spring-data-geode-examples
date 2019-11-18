package example.springdata.geode.server.compression.kt

import com.github.javafaker.Faker
import example.springdata.geode.server.compression.kt.config.CompressionEnabledServerConfigKT
import example.springdata.geode.server.compression.kt.domain.Customer
import example.springdata.geode.server.compression.kt.domain.EmailAddress
import example.springdata.geode.server.compression.kt.repo.CustomerRepositoryKT
import org.slf4j.LoggerFactory
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.WebApplicationType
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.context.annotation.Bean

@SpringBootApplication(scanBasePackageClasses = [CompressionEnabledServerConfigKT::class])
class CompressionEnabledServerKT {

    private val logger = LoggerFactory.getLogger(this.javaClass)
    
    @Bean
    internal fun runner(customerRepository: CustomerRepositoryKT): ApplicationRunner {
        logger.info("Inserting 4000 Customers into compressed region")
        return ApplicationRunner {
            val faker = Faker()
            val fakerName = faker.name()
            val fakerInternet = faker.internet()
            (0 until 4000).forEachIndexed { index, _ ->
                customerRepository.save(Customer(index.toLong(), EmailAddress(fakerInternet.emailAddress()), fakerName.firstName(), fakerName.lastName()))
            }
        }
    }
}

fun main(args: Array<String>) {
    SpringApplicationBuilder(CompressionEnabledServerKT::class.java).web(WebApplicationType.NONE).build().run(*args)
}