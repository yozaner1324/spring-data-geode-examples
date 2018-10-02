package examples.springdata.geode.kt.entityregion.entityregion.client.service

import examples.springdata.geode.domain.Product
import examples.springdata.geode.kt.entityregion.entityregion.client.repo.ProductRepositoryKT
import org.springframework.stereotype.Service
import java.util.*

@Service
class ProductServiceKT(private val productRepository: ProductRepositoryKT) {

    fun save(product: Product) = productRepository.save(product)

    fun findAll(): List<Product> = productRepository.findAll()

    fun findById(id: Long): Optional<Product> = productRepository.findById(id)
}