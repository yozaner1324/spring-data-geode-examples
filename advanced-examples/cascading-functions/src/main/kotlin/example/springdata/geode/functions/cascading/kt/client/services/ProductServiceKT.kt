package example.springdata.geode.functions.cascading.kt.client.services

import example.springdata.geode.functions.cascading.kt.client.repo.ProductRepositoryKT
import example.springdata.geode.domain.Product
import org.springframework.stereotype.Service

@Service
class ProductServiceKT(private val productRepositoryKT: ProductRepositoryKT) {

    fun save(product: Product) = productRepositoryKT.save(product)

    fun findById(productId: Long): Product = productRepositoryKT.findById(productId).get()
}