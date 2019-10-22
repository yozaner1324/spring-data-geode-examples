package example.springdata.geode.server.asyncqueues.kt.repo

import example.springdata.geode.domain.OrderProductSummary
import example.springdata.geode.domain.OrderProductSummaryKey
import org.springframework.data.gemfire.mapping.annotation.Region
import org.springframework.data.gemfire.repository.Query
import org.springframework.data.gemfire.repository.query.annotation.Hint
import org.springframework.data.repository.CrudRepository

@Region("OrderProductSummary")
interface OrderProductSummaryRepositoryKT : CrudRepository<OrderProductSummary, OrderProductSummaryKey> {
    @Hint("emailAddressIndex")
    @Query("select orderSummary.value from /OrderProductSummary.entrySet orderSummary where orderSummary.key.productId = $1")
    fun findAllForProductID(l: Long): List<OrderProductSummary>
}
