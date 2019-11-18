package example.springdata.geode.server.asyncqueues.kt.repo

import example.springdata.geode.server.asyncqueues.kt.domain.Product
import org.springframework.data.repository.CrudRepository

interface ProductRepositoryKT : CrudRepository<Product, Long>
