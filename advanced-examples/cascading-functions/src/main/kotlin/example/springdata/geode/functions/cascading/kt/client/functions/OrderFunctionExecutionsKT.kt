package example.springdata.geode.functions.cascading.kt.client.functions

import example.springdata.geode.domain.Order
import org.springframework.data.gemfire.function.annotation.FunctionId
import org.springframework.data.gemfire.function.annotation.OnServers

@OnServers
interface OrderFunctionExecutionsKT {

    @FunctionId("FindOrdersForCustomers")
    fun findOrdersForCustomers(customerIds: List<Long>): List<Order>
}