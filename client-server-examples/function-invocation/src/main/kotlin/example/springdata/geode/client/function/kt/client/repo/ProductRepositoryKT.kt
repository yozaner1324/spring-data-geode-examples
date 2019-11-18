package example.springdata.geode.client.function.kt.client.repo

import example.springdata.geode.client.function.kt.domain.Product
import org.springframework.data.repository.CrudRepository

interface ProductRepositoryKT : CrudRepository<Product, Long>