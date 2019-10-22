package example.springdata.geode.client.function.kt.client.functions

import example.springdata.geode.domain.Customer
import org.springframework.data.gemfire.function.annotation.FunctionId
import org.springframework.data.gemfire.function.annotation.OnRegion

@OnRegion(region = "Customers")
interface CustomerFunctionExecutionsKT {
    @FunctionId("listConsumersForEmailAddressesFnc")
    fun listAllCustomersForEmailAddress(vararg emailAddresses: String): List<List<Customer>>
}