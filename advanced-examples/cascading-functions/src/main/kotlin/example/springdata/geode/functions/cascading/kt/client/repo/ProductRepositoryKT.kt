package example.springdata.geode.functions.cascading.kt.client.repo

import example.springdata.geode.functions.cascading.kt.domain.Product
import org.springframework.data.repository.CrudRepository

interface ProductRepositoryKT : CrudRepository<Product, Long>