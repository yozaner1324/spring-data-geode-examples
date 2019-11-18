package example.springdata.geode.client.kt.entityregion.server

import example.springdata.geode.client.kt.entityregion.server.config.EntityDefinedRegionServerConfigKT
import org.springframework.boot.WebApplicationType
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder

@SpringBootApplication(scanBasePackageClasses = [EntityDefinedRegionServerConfigKT::class])
class EntityDefinedRegionServerKT {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplicationBuilder(EntityDefinedRegionServerKT::class.java)
                    .web(WebApplicationType.NONE)
                    .build()
                    .run(*args)
        }
    }
}