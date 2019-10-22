package example.springdata.geode.server.eviction.kt.service

import org.apache.geode.cache.Region
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

@Component
class ProductServiceKT(@param:Qualifier("Products") private val productRegion: Region<*, *>) {

    fun size(): Int {
        return productRegion.size
    }
}