package example.springdata.geode.client.cq.kt.client.repo

import example.springdata.geode.client.cq.kt.domain.Customer
import org.springframework.data.gemfire.mapping.annotation.ClientRegion
import org.springframework.data.repository.CrudRepository


@ClientRegion(name = "Customers")
interface CustomerRepositoryKT : CrudRepository<Customer, Long>