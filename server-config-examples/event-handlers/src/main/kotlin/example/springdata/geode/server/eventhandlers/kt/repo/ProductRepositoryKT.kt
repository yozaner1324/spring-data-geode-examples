package example.springdata.geode.server.eventhandlers.kt.repo

import example.springdata.geode.domain.Product
import org.springframework.data.gemfire.mapping.annotation.Region
import org.springframework.data.repository.CrudRepository

@Region("Products")
interface ProductRepositoryKT : CrudRepository<Product, Long>
