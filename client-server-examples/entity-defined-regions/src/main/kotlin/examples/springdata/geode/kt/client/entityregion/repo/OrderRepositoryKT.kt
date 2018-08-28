package examples.springdata.geode.kt.client.entityregion.repo

import examples.springdata.geode.domain.Order
import org.springframework.data.gemfire.mapping.annotation.ClientRegion
import org.springframework.data.repository.CrudRepository

@ClientRegion("Orders")
interface OrderRepositoryKT : CrudRepository<Order, Long> {
    override fun findAll(): List<Order>
}
