package example.springdata.geode.functions.cascading.kt.server.functions

import example.springdata.geode.functions.cascading.kt.domain.Customer
import example.springdata.geode.functions.cascading.kt.domain.Order
import org.apache.geode.cache.Region
import org.slf4j.LoggerFactory
import org.springframework.data.gemfire.function.annotation.GemfireFunction
import org.springframework.data.gemfire.function.annotation.RegionData
import org.springframework.stereotype.Component
import javax.annotation.Resource

@Component
class CascadingFunctionsKT {

    @Resource(name = "Orders")
    private lateinit var orderData: Region<Long, Order>

    private val logger = LoggerFactory.getLogger(this.javaClass)

    @GemfireFunction(id = "ListAllCustomers", HA = true, optimizeForWrite = false, batchSize = 0, hasResult = true)
    fun listAllCustomers(@RegionData customerData: Map<Long, Customer>): List<Long> {
        logger.info("I'm executing function: \"listAllCustomers\" size= ${customerData.size}")
        return customerData.map { it.value }.mapNotNull { customer -> customer.id }.toList()
    }

    @GemfireFunction(id = "FindOrdersForCustomers", HA = true, optimizeForWrite = true, batchSize = 0, hasResult = true)
    fun findOrdersForCustomers(customerIds: List<Long>): List<Order> {
        logger.info("I'm executing function: \"findOrdersForCustomer\" size= ${orderData.size}")
        val returnValue = orderData.map { it.value }.filter { order -> customerIds.contains(order.customerId) }.toList()
        logger.info("Return Value: $returnValue")
        return returnValue
    }
}