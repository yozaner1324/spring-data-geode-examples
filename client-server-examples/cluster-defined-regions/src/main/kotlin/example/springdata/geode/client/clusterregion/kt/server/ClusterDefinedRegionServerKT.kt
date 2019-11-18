package example.springdata.geode.client.clusterregion.kt.server

import example.springdata.geode.client.clusterregion.kt.server.config.ClusterDefinedRegionServerConfigKT
import org.springframework.boot.WebApplicationType
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder


@SpringBootApplication(scanBasePackageClasses = [ClusterDefinedRegionServerConfigKT::class])
class ClusterDefinedRegionServerKT {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplicationBuilder(ClusterDefinedRegionServerKT::class.java)
                    .web(WebApplicationType.NONE)
                    .build()
                    .run(*args)
        }
    }
}