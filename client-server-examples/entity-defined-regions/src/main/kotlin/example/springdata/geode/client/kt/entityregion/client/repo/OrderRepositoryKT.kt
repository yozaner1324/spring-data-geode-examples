package example.springdata.geode.client.kt.entityregion.client.repo

import example.springdata.geode.domain.Order
import org.springframework.data.gemfire.mapping.annotation.ClientRegion
import org.springframework.data.repository.CrudRepository

@ClientRegion("Orders")
interface OrderRepositoryKT : CrudRepository<Order, Long> {
    override fun findAll(): List<Order>
}
