package example.springdata.geode.server.wan.event.kt

import example.springdata.geode.server.wan.event.kt.config.WanEventFiltersConfigKT
import example.springdata.geode.server.wan.kt.repo.CustomerRepositoryKT
import example.springdata.geode.util.createCustomers
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.WebApplicationType
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Profile

@SpringBootApplication(scanBasePackageClasses = [WanEventFiltersConfigKT::class])
class WanEventFilteringServerKT {

    @Bean
    @Profile("default", "SiteA")
    fun siteARunner() = ApplicationRunner {
        readLine()
    }

    @Bean
    @Profile("SiteB")
    fun siteBRunner(customerRepository: CustomerRepositoryKT) = ApplicationRunner {
        println("Inserting 300 customers")
        createCustomers(300, customerRepository)
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplicationBuilder(WanEventFilteringServerKT::class.java)
                    .web(WebApplicationType.NONE)
                    .build()
                    .run(*args)
        }
    }
}