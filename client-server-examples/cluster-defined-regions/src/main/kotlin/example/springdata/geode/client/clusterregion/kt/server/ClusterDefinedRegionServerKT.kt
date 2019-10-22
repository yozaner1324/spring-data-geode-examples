package example.springdata.geode.client.clusterregion.kt.server

import example.springdata.geode.client.clusterregion.kt.server.config.ClusterDefinedRegionServerConfigKT
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.WebApplicationType
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.context.annotation.Bean
import java.util.*


@SpringBootApplication(scanBasePackageClasses = [ClusterDefinedRegionServerConfigKT::class])
class ClusterDefinedRegionServerKT {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(ClusterDefinedRegionServerKT::class.java, *args)

            SpringApplicationBuilder(ClusterDefinedRegionServerKT::class.java)
                    .web(WebApplicationType.NONE)
                    .build()
                    .run(*args)
        }
    }

    @Bean
    fun runner() = ApplicationRunner {
        println("Press <ENTER> to exit")
        Scanner(System.`in`).nextLine()
    }
}