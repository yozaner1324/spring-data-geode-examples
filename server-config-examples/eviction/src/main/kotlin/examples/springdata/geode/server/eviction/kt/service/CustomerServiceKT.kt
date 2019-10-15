package examples.springdata.geode.server.eviction.kt.service

import org.apache.geode.cache.Region
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

@Component
class CustomerServiceKT(@param:Qualifier("Customers") private val customerRegion: Region<*, *>) {

    fun size(): Int {
        return customerRegion.size
    }
}
