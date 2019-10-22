package example.springdata.geode.server.eviction.kt.service

import org.apache.geode.cache.Region
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

@Component
class OrderServiceKT(@param:Qualifier("Orders") private val orderRegion: Region<*, *>) {

    fun size(): Int {
        return orderRegion.size
    }
}