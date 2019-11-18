package example.springdata.geode.server.eviction.kt.repo

import example.springdata.geode.server.eviction.kt.domain.Customer
import example.springdata.geode.server.eviction.kt.domain.Order
import example.springdata.geode.server.eviction.kt.domain.Product
import org.springframework.data.gemfire.mapping.annotation.Region
import org.springframework.data.repository.CrudRepository

@Region("Customers")
interface CustomerRepositoryKT : CrudRepository<Customer, Long>

@Region("Orders")
interface OrderRepositoryKT : CrudRepository<Order, Long>

@Region("Products")
interface ProductRepositoryKT : CrudRepository<Product, Long>