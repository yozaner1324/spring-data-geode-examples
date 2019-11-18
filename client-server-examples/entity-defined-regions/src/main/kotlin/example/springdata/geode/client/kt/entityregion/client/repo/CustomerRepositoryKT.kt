package example.springdata.geode.client.kt.entityregion.client.repo

import example.springdata.geode.client.kt.entityregion.domain.Customer
import org.springframework.data.gemfire.mapping.annotation.ClientRegion
import org.springframework.data.repository.CrudRepository

@ClientRegion("Customers")
interface CustomerRepositoryKT : CrudRepository<Customer, Long> {
    override fun findAll() : List<Customer>
}