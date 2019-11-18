package example.springdata.geode.server.offheap.kt

import com.github.javafaker.Faker
import example.springdata.geode.server.offheap.kt.config.OffHeapServerConfigKT
import example.springdata.geode.server.offheap.kt.domain.Customer
import example.springdata.geode.server.offheap.kt.domain.EmailAddress
import example.springdata.geode.server.offheap.kt.domain.Product
import example.springdata.geode.server.offheap.kt.repo.CustomerRepositoryKT
import example.springdata.geode.server.offheap.kt.repo.ProductRepositoryKT
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.WebApplicationType
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.context.annotation.Bean
import java.math.BigDecimal

@SpringBootApplication(scanBasePackageClasses = [OffHeapServerConfigKT::class])
class OffHeapServerKT {

    @Bean
    fun runner(customerRepository: CustomerRepositoryKT, productRepository: ProductRepositoryKT) =
            ApplicationRunner {
                createCustomers(customerRepository)
                createProducts(productRepository)
            }

    private fun createProducts(repository: ProductRepositoryKT) {
        val faker = Faker()
        val fakerCommerce = faker.commerce()
        (0 until 1000).forEachIndexed { index, _ ->
            repository.save(
                    Product(index.toLong(), fakerCommerce.productName(), BigDecimal(fakerCommerce.price(0.01, 100000.0))))
        }
    }

    private fun createCustomers(repository: CustomerRepositoryKT) {
        val faker = Faker()
        val fakerName = faker.name()
        val fakerInternet = faker.internet()
        (0 until 3000).forEachIndexed { index, _ ->
            repository.save(Customer(index.toLong(),
                    EmailAddress(fakerInternet.emailAddress()), fakerName.firstName(), fakerName.lastName()))
        }
    }
}

fun main(args: Array<String>) {
    SpringApplicationBuilder(OffHeapServerKT::class.java).web(WebApplicationType.NONE).build().run(*args)
}