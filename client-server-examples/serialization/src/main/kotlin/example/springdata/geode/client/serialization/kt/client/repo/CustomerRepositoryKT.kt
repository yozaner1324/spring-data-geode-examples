package example.springdata.geode.client.serialization.kt.client.repo

import example.springdata.geode.client.serialization.domain.Customer
import org.springframework.data.gemfire.mapping.annotation.ClientRegion
import org.springframework.data.repository.CrudRepository


@ClientRegion("Customers")
interface CustomerRepositoryKT : CrudRepository<Customer, Long> {
    /**
     * Returns all [Customer]s.
     *
     * @return
     */
    override fun findAll(): List<Customer>
}