package example.springdata.geode.server.region.kt.repo

import example.springdata.geode.domain.Customer
import example.springdata.geode.domain.Order
import example.springdata.geode.domain.Product
import org.springframework.data.gemfire.mapping.annotation.Region
import org.springframework.data.repository.CrudRepository

@Region("Customers")
interface CustomerRepositoryKT : CrudRepository<Customer, Long>

@Region("Orders")
interface OrderRepositoryKT : CrudRepository<Order, Long>

@Region("Products")
interface ProductRepositoryKT : CrudRepository<Product, Long>