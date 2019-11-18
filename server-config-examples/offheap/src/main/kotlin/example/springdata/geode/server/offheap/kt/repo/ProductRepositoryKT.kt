package example.springdata.geode.server.offheap.kt.repo

import example.springdata.geode.server.offheap.kt.domain.Product
import org.springframework.data.repository.CrudRepository

interface ProductRepositoryKT : CrudRepository<Product, Long>
