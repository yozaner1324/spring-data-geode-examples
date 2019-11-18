package example.springdata.geode.server.eventhandlers.kt.repo

import example.springdata.geode.server.eventhandlers.kt.domain.Customer
import org.springframework.data.gemfire.mapping.annotation.Region
import org.springframework.data.repository.CrudRepository

@Region("Customers")
interface CustomerRepositoryKT : CrudRepository<Customer, Long>
