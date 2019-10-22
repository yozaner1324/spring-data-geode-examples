package example.springdata.geode.functions.cascading.kt.client.services

import example.springdata.geode.domain.Order
import example.springdata.geode.functions.cascading.kt.client.functions.OrderFunctionExecutionsKT
import example.springdata.geode.functions.cascading.kt.client.repo.OrderRepositoryKT
import org.springframework.stereotype.Service

@Service
class OrderServiceKT(private val orderRepositoryKT: OrderRepositoryKT,
                     private val orderFunctionExecutionsKT: OrderFunctionExecutionsKT) {

    fun save(order: Order) = orderRepositoryKT.save(order)
    fun findOrdersForCustomers(customerIds: List<Long>): List<Order> =
            orderFunctionExecutionsKT.findOrdersForCustomers(customerIds)
}