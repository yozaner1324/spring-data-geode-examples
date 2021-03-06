package examples.springdata.geode.client.serialization.kt.client.repo

import examples.springdata.geode.domain.Customer
import examples.springdata.geode.domain.EmailAddress
import org.springframework.context.annotation.Profile
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

    /**
     * Finds all [Customer]s with the given lastname.
     *
     * @param lastname
     * @return
     */
    fun findByLastName(lastName: String): List<Customer>

    /**
     * Finds the Customer with the given [EmailAddress].
     *
     * @param emailAddress
     * @return
     */
    fun findByEmailAddress(emailAddress: EmailAddress): Customer
}