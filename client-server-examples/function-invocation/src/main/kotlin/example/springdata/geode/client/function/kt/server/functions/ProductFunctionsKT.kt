package example.springdata.geode.client.function.kt.server.functions

import example.springdata.geode.client.function.kt.domain.Product
import org.springframework.data.gemfire.function.annotation.GemfireFunction
import org.springframework.data.gemfire.function.annotation.RegionData
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
class ProductFunctionsKT {
    @GemfireFunction(id = "sumPricesForAllProductsFnc", HA = true, optimizeForWrite = false, hasResult = true)
    fun sumPricesForAllProductsFnc(@RegionData productData: Map<Long, Product>) =
        BigDecimal.valueOf(productData.map { it.value }.sumByDouble { it.price.toDouble() })
}