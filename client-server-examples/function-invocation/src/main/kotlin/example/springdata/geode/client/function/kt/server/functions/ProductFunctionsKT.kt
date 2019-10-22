package example.springdata.geode.client.function.kt.server.functions

import example.springdata.geode.domain.Product
import example.springdata.geode.util.sumBy
import org.springframework.data.gemfire.function.annotation.GemfireFunction
import org.springframework.data.gemfire.function.annotation.RegionData
import org.springframework.stereotype.Component

@Component
class ProductFunctionsKT {
    @GemfireFunction(id = "sumPricesForAllProductsFnc", HA = true, optimizeForWrite = false, hasResult = true)
    fun sumPricesForAllProductsFnc(@RegionData productData: Map<Long, Product>) =
            productData.asSequence().sumBy { it.value.price }
}