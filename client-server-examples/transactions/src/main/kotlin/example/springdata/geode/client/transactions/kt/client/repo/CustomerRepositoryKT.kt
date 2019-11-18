package example.springdata.geode.client.transactions.kt.client.repo

import example.springdata.geode.client.transactions.domain.Customer
import org.springframework.data.gemfire.mapping.annotation.ClientRegion
import org.springframework.data.repository.CrudRepository

@ClientRegion("Customers")
interface CustomerRepositoryKT : CrudRepository<Customer, Long>
