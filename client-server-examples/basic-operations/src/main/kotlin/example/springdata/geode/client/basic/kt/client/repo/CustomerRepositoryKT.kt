package example.springdata.geode.client.basic.kt.client.repo

import example.springdata.geode.client.basic.kt.domain.Customer
import org.springframework.data.gemfire.mapping.annotation.Region
import org.springframework.data.repository.CrudRepository

@Region("Customers")
interface CustomerRepositoryKT : CrudRepository<Customer, Long>