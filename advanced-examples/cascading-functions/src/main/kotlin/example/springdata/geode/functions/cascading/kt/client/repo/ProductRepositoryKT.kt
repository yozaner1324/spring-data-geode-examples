package example.springdata.geode.functions.cascading.kt.client.repo

import example.springdata.geode.domain.Product
import org.springframework.context.annotation.DependsOn
import org.springframework.data.gemfire.mapping.annotation.ClientRegion
import org.springframework.data.repository.CrudRepository

interface ProductRepositoryKT : CrudRepository<Product, Long>