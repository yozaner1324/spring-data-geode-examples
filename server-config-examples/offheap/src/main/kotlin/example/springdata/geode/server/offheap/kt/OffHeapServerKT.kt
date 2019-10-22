package example.springdata.geode.server.offheap.kt

import example.springdata.geode.server.offheap.kt.config.OffHeapServerConfigKT
import example.springdata.geode.server.offheap.kt.repo.CustomerRepositoryKT
import example.springdata.geode.server.offheap.kt.repo.ProductRepositoryKT
import example.springdata.geode.util.createCustomers
import example.springdata.geode.util.createProducts
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.WebApplicationType
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.context.annotation.Bean

@SpringBootApplication(scanBasePackageClasses = [OffHeapServerConfigKT::class])
class OffHeapServerKT {

    @Bean
    fun runner(customerRepository: CustomerRepositoryKT, productRepository: ProductRepositoryKT) =
            ApplicationRunner {
                createCustomers(3000, customerRepository)
                createProducts(1000, productRepository)
            }
}

fun main(args: Array<String>) {
    SpringApplicationBuilder(OffHeapServerKT::class.java).web(WebApplicationType.NONE).build().run(*args)
}