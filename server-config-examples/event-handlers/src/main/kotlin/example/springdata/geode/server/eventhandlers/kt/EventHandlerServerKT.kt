package example.springdata.geode.server.eventhandlers.kt

import example.springdata.geode.server.eventhandlers.kt.config.EventHandlerServerConfigurationKT
import example.springdata.geode.server.eventhandlers.kt.domain.Customer
import example.springdata.geode.server.eventhandlers.kt.domain.EmailAddress
import example.springdata.geode.server.eventhandlers.kt.domain.Product
import example.springdata.geode.server.eventhandlers.kt.repo.CustomerRepositoryKT
import example.springdata.geode.server.eventhandlers.kt.repo.ProductRepositoryKT
import org.slf4j.LoggerFactory
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.WebApplicationType
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.context.annotation.Bean
import java.math.BigDecimal
import java.util.stream.LongStream

@SpringBootApplication(scanBasePackageClasses = [EventHandlerServerConfigurationKT::class])
class EventHandlerServerKT {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    @Bean
    internal fun runner(customerRepository: CustomerRepositoryKT, productRepository: ProductRepositoryKT) = ApplicationRunner {
        createCustomerData(customerRepository)
        createProducts(productRepository)

        val product = productRepository.findById(5L)
        logger.info("product = " + product.get())
    }

    private fun createProducts(productRepository: ProductRepositoryKT) {
        productRepository.save(Product(1L, "Apple iPod", BigDecimal("99.99"),
                "An Apple portable music player"))
        productRepository.save(Product(2L, "Apple iPad", BigDecimal("499.99"),
                "An Apple tablet device"))
        val macbook = Product(3L, "Apple macBook", BigDecimal("899.99"),
                "An Apple notebook computer")
        macbook.addAttribute("warranty", "included")
        productRepository.save(macbook)
    }

    private fun createCustomerData(customerRepository: CustomerRepositoryKT) {
        logger.info("Inserting 3 entries for keys: 1, 2, 3")
        LongStream.rangeClosed(1, 3)
            .forEach { customerId ->
                customerRepository.save(Customer(customerId,
                        EmailAddress("$customerId@2.com"), "John$customerId", "Smith$customerId"))
            }
    }
}

fun main(args: Array<String>) {
    SpringApplicationBuilder(EventHandlerServerKT::class.java)
        .web(WebApplicationType.NONE)
        .build()
        .run(*args)
}