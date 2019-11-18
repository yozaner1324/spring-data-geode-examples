package example.springdata.geode.server.eviction.kt

import com.github.javafaker.Faker
import example.springdata.geode.server.eviction.kt.config.EvictionServerConfigKT
import example.springdata.geode.server.eviction.kt.domain.*
import example.springdata.geode.server.eviction.kt.repo.CustomerRepositoryKT
import example.springdata.geode.server.eviction.kt.repo.OrderRepositoryKT
import example.springdata.geode.server.eviction.kt.repo.ProductRepositoryKT
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.WebApplicationType
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.context.annotation.Bean
import org.springframework.data.repository.CrudRepository
import java.math.BigDecimal
import java.util.*

@SpringBootApplication(scanBasePackageClasses = [EvictionServerConfigKT::class])
class EvictionServerKT {
    
    @Bean
    fun runner(customerRepositoryKT: CustomerRepositoryKT,
               orderRepositoryKT: OrderRepositoryKT,
               productRepositoryKT: ProductRepositoryKT) = ApplicationRunner {
        createCustomers(customerRepositoryKT)
        createProducts(productRepositoryKT)
        createOrders(orderRepositoryKT, productRepositoryKT)
    }

    private fun createProducts(repository: CrudRepository<Product, Long>) {
        val faker = Faker()
        val fakerCommerce = faker.commerce()
        (0 until 300).forEachIndexed { index, _ ->
            repository.save(
                    Product(index.toLong(), fakerCommerce.productName(), BigDecimal(fakerCommerce.price(0.01, 100000.0))))
        }
    }

    private fun createCustomers(repository: CustomerRepositoryKT) {
        val faker = Faker()
        val fakerName = faker.name()
        val fakerInternet = faker.internet()
        (0 until 300).forEachIndexed { index, _ ->
            repository.save(Customer(index.toLong(),
                    EmailAddress(fakerInternet.emailAddress()), fakerName.firstName(), fakerName.lastName()))
        }
    }

    private fun createOrders(orderRepository: OrderRepositoryKT, productRepository: ProductRepositoryKT) {
        val random = Random(System.nanoTime())

        val faker = Faker()
        val fakerAddress = faker.address()
        val address = Address(fakerAddress.streetAddress(), fakerAddress.city(), fakerAddress.country())

        (0L..300.toLong()).forEach { customerId ->
            (0L..100.toLong()).forEach { orderId ->
                val order = Order(orderId, customerId, address)
                (0..random.nextInt(5)).forEach {
                    val quantity = random.nextInt(10) + 1
                    val productId = (random.nextInt(300)).toLong()
                    order.add(LineItem(productRepository.findById(productId).get(), quantity))
                }
                orderRepository.save(order)
            }
        }
    }
}

fun main(args: Array<String>) {
    SpringApplicationBuilder(EvictionServerKT::class.java).web(WebApplicationType.NONE).build().run(*args)
}